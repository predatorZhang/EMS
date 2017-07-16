<%@ page import="com.casic.accessControl.user.domain.User" %>
<%@ page import="java.util.HashSet" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<script>
    var logout = function () {

        window.location.href = '${ctx}/user/sys-log-out.do'

    }
    
    var resetPassword = function () {
        window.location.href = '${ctx}/content/user/user-change-pwd.jsp'
    }
</script>

<div class="header navbar navbar-inverse navbar-fixed-top">

    <div class="navbar-inner">

        <div class="head-img">

            <span class="left-img"><img src="${ctx}/images/basic/top-left.png" style="height:48px"/></span>

            <span class="center-img"><img src="${ctx}/images/basic/top-center.png" style="height:48px"/></span>

            <span class="right-img"><img src="${ctx}/images/basic/top-right.png" style="height:48px"/></span>

        </div>

        <div class="back-img">

            <img style="height:37px;" src="${ctx}/images/basic/sys-banner.png"/>

            <div class="log-info">
                <%--<a>在线用户：<%= ((HashSet)application.getAttribute("sessions")).size()-1%></a>--%>
<%--
                <a style="margin-left: 10px;">当前用户：<%=((UserInfo) session.getAttribute("_current_user")).getUserName()%> </a>
--%>
                <a style="margin-left: 10px; cursor: pointer" onclick="resetPassword()">修改密码</a>
                <a style="margin-left: 10px; cursor: pointer" onclick="logout()">注销</a>

            </div>

        </div>
    </div>

    <%--<div class="container-fluid">--%>
    <%--</div>--%>

</div>

<div class="space9"></div>
