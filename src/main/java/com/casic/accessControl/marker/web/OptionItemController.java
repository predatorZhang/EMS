package com.casic.accessControl.marker.web;

import com.casic.accessControl.marker.dto.OptionItemDto;
import com.casic.accessControl.marker.manager.OptionItemManager;
import com.casic.accessControl.user.domain.User;
import com.casic.accessControl.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/12/6.
 */
@Controller
@RequestMapping("optionItems")
public class OptionItemController {
    @Resource
    private OptionItemManager optionItemManager;

    @RequestMapping("allItems")
    public @ResponseBody Map<String, Object> getAllItems( HttpSession session) {
        User user = (User) session.getAttribute(StringUtils.SYS_USER);//获取当前登录用户
        Long companyId = user.getCompany().getId();
        Map<String, Object> result = new HashMap<String, Object>();
        List<OptionItemDto> list = optionItemManager.getItemsList(companyId);
        result.put("data", list);
        result.put("success", true);
        return result;
    }
}
