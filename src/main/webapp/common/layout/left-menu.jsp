<%@ taglib prefix="region" uri="http://www.casic203.com/region/tags" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<!-- BEGIN SIDEBAR -->

<div class="page-sidebar nav-collapse collapse">

    <ul class="page-sidebar-menu">

        <li>
            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            <div class="sidebar-toggler hidden-phone"></div>
            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
        </li>

        <!--对象-->
        <li class="${currentMenu == 'ems' ? 'start active' : ''}">

            <a href="${ctx}/content/ems/ems.jsp" target="_top">

                <i class="icon-home"></i>
                <span class="title">地图总览</span>
                <span class="selected"></span>
            </a>

        </li>
       <region:region-permission permission="位置点管理:read" region="0">
            <!--权限管理-->
            <li class="${currentMenu == 'markerManagerMenu' ? 'start active' : ''}">
                <!--TODO LIST:换成相应模块下的jsp文件-->
                <a href="${ctx}/content/marker/marker-info-list.jsp" target="_top">
                    <i class="icon-location-arrow"></i>
                    <span class="title">位置点管理</span>
                    <span class="selected"></span>
                </a>
            </li>
            <!--权限管理-->
        </region:region-permission>
        <region:region-permission permission="工单管理:read" region="0">
        <!--权限管理-->
        <li class="${currentMenu == 'task' ? 'start active' : ''}">
            <!--TODO LIST:换成相应模块下的jsp文件-->
            <a href="${ctx}/content/task/task.jsp" target="_top">
                <i class="icon-file"></i>
                <span class="title">工单管理</span>
                <span class="selected"></span>
            </a>

        </li>
        <!--权限管理-->
        </region:region-permission>
        <region:region-permission permission="统计分析:read" region="0">
            <!--系统服务-->
            <li class="${currentMenu == 'statistic' ? 'start active' : ''}">
                <a href="javascript:;">
                    <i class="icon-bar-chart"></i>
                    <span class="title">统计分析</span>
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>
                <ul class="sub-menu">
                    <li  class="${secondMenu == 'marker' ? 'active' : ''}">
                        <a href="${ctx}/content/statistic/marker-statistic.jsp">
                            <i class="icon-map-marker"></i>
                            电子标识器统计
                        </a>
                    </li>
                    <li class="${secondMenu == 'testPile' ? 'start active' : ''}">
                        <a href="${ctx}/content/statistic/test-pile-stats.jsp">
                            <i class="icon-skype"></i>
                            测试桩统计
                        </a>
                    </li>
                </ul>
            </li>
        </region:region-permission>
        <region:region-permission permission="管道完整性管理:read" region="0">
            <!--系统服务-->
            <li class="${currentMenu == 'integrality' ? 'start active' : ''}">

                <a href="${ctx}/content/pipe/test-pile-list.jsp">
                    <i class="icon-briefcase"></i>
                    <span class="title">管道完整性管理</span>
                    <span class="selected"></span>
                </a>
            </li>
        </region:region-permission>
        <region:region-permission permission="用户管理:read" region="0">
            <!--系统服务-->
            <li class="${currentMenu == 'user' ? 'start active' : ''}">

                <a href="${ctx}/content/user/user-info-list.jsp">

                    <i class="icon-user"></i>

                    <span class="title">用户管理</span>

                    <span class="selected"></span>

                </a>
            </li>
        </region:region-permission>
    </ul>
</div>


<!-- END SIDEBAR MENU -->
