package com.casic.accessControl.user.web;

import com.casic.accessControl.user.domain.Company;
import com.casic.accessControl.user.domain.Role;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.user.dto.CompanyDto;
import com.casic.accessControl.user.dto.RoleDto;
import com.casic.accessControl.user.dto.UserDto;
import com.casic.accessControl.user.manager.CompanyManager;
import com.casic.accessControl.user.manager.RoleManager;
import com.casic.accessControl.user.manager.UserManager;
import com.casic.accessControl.util.DataTable;
import com.casic.accessControl.util.StringUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserManager userManager;

    @Resource
    private RoleManager roleManager;

    @Resource
    private CompanyManager companyManager;

    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(@ModelAttribute User userInfo, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = null;
        try{
            user = userManager.login(userInfo);
//            user = userManager.tryLogin(userInfo);
        }  catch(Exception e){
            result.put("success", false);
            return result;
        }

        if (user == null) {//登录失败
            logger.warn("登录失败，请检查用户名或密码是否正确,当前用户名：{},密码：{}", userInfo.getUserName(), userInfo.getPassword());
            result.put("success", false);
            result.put("message", "登录失败，请检查用户名或密码是否正确");
            return result;
        }
        result.put("success", true);
        result.put("message", "登录成功");
        result.put("data",UserDto.Convert(user));
        logger.info("用户{},在{}登录成功", user.getUserName(), System.currentTimeMillis());
        if(StringUtils.isNotBlank(user.getRole().getFunctions())){
            session.setAttribute(StringUtils.SYS_USER, user);
        }
        return result;
    }

    @RequestMapping("user-info-list")
    public void list(String jsonParam, HttpServletResponse response, HttpSession session) {
        try {
            User user = (User) session.getAttribute(StringUtils.SYS_USER);
            DataTable<UserDto> userList = userManager.pageUser(jsonParam, user);
            Gson gson = new Gson();
            String json = gson.toJson(userList);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("user-info-edit")
    public String edit(@RequestParam(value = "id", required = false) Long id, Model model) {
        UserDto userDto = new UserDto();
        if (id != null) {

            userDto = userManager.getUserDtoById(id);
        }
        List<Role> roles = roleManager.getRoles();
        List<RoleDto> roleDtos = RoleDto.Converts(roles);
        List<Company> companies = companyManager.getCompanies();
        List<CompanyDto> companyDtos = CompanyDto.Converts(companies);
        model.addAttribute("model", userDto);
        model.addAttribute("roles",roleDtos);
        model.addAttribute("companies",companyDtos);
        return "user/user-info-edit";
    }

    @RequestMapping("user-info-save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute UserDto userDto, HttpSession session) {
        //分为新增用户和编辑用户两种，区分标识为userInfo的id是否为空
        Map<String, Object> map = new HashMap<String, Object>();
        if (userDto!=null)
        {
            if (userDto.getId() == null) {
                if (userManager.getUserByName(userDto.getUserName()) != null) {//说明数据库中存在相同用户名的，不能新增该用户
                    map.put("success", false);
                    map.put("message", "数据库中存在相同用户名的人员，请更改用户名");
                    return map;
                }
            }
            Company company = companyManager.get(userDto.getCompanyId());
            Role role = roleManager.get(userDto.getRoleId());
            User user = new User();
            user.setId(userDto.getId());
            user.setUserName(userDto.getUserName());
            user.setPassword(userDto.getPassword());
            user.setSex(userDto.getSex());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setCompany(company);
            user.setRole(role);
            user.setIsValid(1);
            userManager.saveUser(user, session);
            if (userDto.getId() == null ) {//新增用户
                map.put("success", true);
                map.put("message", "新增用户成功");
            } else {
                map.put("success", true);
                map.put("message", "编辑用户成功");
            }
        }
        else
        {
            map.put("success", false);
            map.put("message", "用户信息为空");
        }
        return map;
    }

    @RequestMapping("user-info-delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (id == null) {
            map.put("success", false);
            map.put("message", "删除用户需要用户ID信息");
            return map;
        }
        boolean temp = userManager.deleteUserById(id);
        if (temp)
        {
            map.put("success", true);
            map.put("message", "用户删除成功");
        }
        else
        {
            map.put("success", false);
            map.put("message", "用户删除失败");
        }
        return map;
    }

    @RequestMapping("sys-log-out")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:../login.jsp";
    }

    @RequestMapping("change-user-pwd")
    @ResponseBody
    public Map<String, String> changePwd(@RequestParam(value = "oldPwd", required = true) String oldPwd, @RequestParam(value = "newPwd", required = true) String newPwd, HttpSession session) {
        User curUser = (User) session.getAttribute(StringUtils.SYS_USER);
        Map<String, String> result = new HashMap<String, String>();
        if (oldPwd.equals(curUser.getPassword())) {//当前用户密码和用户输入一致
            userManager.changePassword(curUser, newPwd);
            result.put("success", "ok");
            result.put("message", "修改成功");
            return result;
        }
        result.put("message", "密码错误，修改失败");
        return result;

    }

    @RequestMapping(value = "get-patroler")
    @ResponseBody
    public Map<String, Object> getTypeList(HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        User currentUser = (User) session.getAttribute(StringUtils.SYS_USER);
        if (currentUser == null) {
            result.put("message", "用户未登录");
            return result;
        }
        Company company = currentUser.getCompany();
        Role role = roleManager.getRoleByType(5);
        List<User> users = userManager.getPatrolerByCompany(company,role);
        List<UserDto> userDtos = UserDto.Converts(users);

        if (userDtos == null) {//获取列表失败
            result.put("message", "获取列表出现错误");
            return result;
        } else {
            result.put("data", userDtos);
            result.put("success", "获取列表成功");
        }

        return result;
    }

}
