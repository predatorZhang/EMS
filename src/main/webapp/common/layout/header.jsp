<%@ page import="com.casic.accessControl.user.domain.User" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="com.casic.accessControl.util.StringUtils" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<script>
    /*  var logout = function () {

     window.location.href = '${ctx}/user/sys-log-out.do'

     }

     var resetPassword = function () {
     window.location.href = '${ctx}/content/user/user-change-pwd.jsp'
     }*/
</script>

<div class="header navbar navbar-inverse navbar-fixed-top">

<!-- BEGIN TOP NAVIGATION BAR -->

<div class="navbar-inner">

<div class="container-fluid">

<!-- BEGIN LOGO -->

<a class="brand" href="">

    <img src="${ctx}/s/media/image/br_logo.png" alt="logo"/>

</a>

<!-- END LOGO -->

<!-- BEGIN RESPONSIVE MENU TOGGLER -->

<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">

    <img src="${ctx}/s/media/image/menu-toggler.png" alt=""/>

</a>

<!-- END RESPONSIVE MENU TOGGLER -->

<!-- BEGIN TOP NAVIGATION MENU -->

<ul class="nav pull-right">

    <!-- BEGIN NOTIFICATION DROPDOWN -->

    <!--<li class="dropdown" id="header_notification_bar">

        <a href="#" class="dropdown-toggle" data-toggle="dropdown">

            <i class="icon-warning-sign"></i>

            <span class="badge">100000</span>

        </a>

        <ul class="dropdown-menu extended notification">

            <li>

                <p>新增标识:1000，总数:100000</p>

            </li>

            <li>

                <a href="#">

                    <span class="label label-success"><i class="icon-plus"></i></span>

                    New user registered.

                    <span class="time">Just now</span>

                </a>

            </li>

            <li class="external">

                <a href="#">See all notifications <i class="m-icon-swapright"></i></a>

            </li>

        </ul> -->

    </li>

    <!-- END NOTIFICATION DROPDOWN -->

    <!-- BEGIN TODO DROPDOWN -->

    <%--<li class="dropdown" id="header_task_bar">--%>

        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>

            <%--<i class="icon-tasks"></i>--%>

            <%--&lt;%&ndash;<span class="badge">3</span>&ndash;%&gt;--%>

        <%--</a>--%>

        <%--<ul class="dropdown-menu extended tasks">--%>

            <%--<li>--%>

                <%--<p id="title_runningTasks">正在执行任务数：20条</p>--%>

            <%--</li>--%>

            <%--<li>--%>

                <%--<a href="#">--%>

								<%--<span class="task">--%>

								<%--<span class="desc">正在执行任务占比</span>--%>

								<%--<span class="percent" id="runningTasks_per">30%</span>--%>

								<%--</span>--%>

								<%--<span class="progress progress-success ">--%>

								<%--<span style="width: 30%;" class="bar" id="runningTasks_bar"></span>--%>

								<%--</span>--%>

                <%--</a>--%>

            <%--</li>--%>

            <%--<li>--%>

                <%--<a href="#">--%>

								<%--<span class="task">--%>

								<%--<span class="desc">待领取任务占比：</span>--%>

								<%--<span class="percent" id="unfetchingTasks_per">65%</span>--%>

								<%--</span>--%>

								<%--<span class="progress progress-danger progress-striped active">--%>

								<%--<span style="width: 65%;" class="bar" id="unfetchingTasks_bar"></span>--%>

								<%--</span>--%>

                <%--</a>--%>

            <%--</li>--%>

            <%--<li>--%>

                <%--<a href="#">--%>

								<%--<span class="task">--%>

								<%--<span class="desc">已完成任务占比</span>--%>

								<%--<span class="percent" id="finishedTasks_per">98%</span>--%>

								<%--</span>--%>

								<%--<span class="progress progress-success">--%>

								<%--<span style="width: 98%;" class="bar" id="finishedTasks_bar"></span>--%>

								<%--</span>--%>

                <%--</a>--%>

            <%--</li>--%>

            <%--<li class="external">--%>

                <%--<a href="${ctx}/content/task/task-charts.jsp">查看所有任务 <i class="m-icon-swapright"></i></a>--%>

            <%--</li>--%>

        <%--</ul>--%>

    <%--</li>--%>

    <!-- END TODO DROPDOWN -->

    <!-- BEGIN USER LOGIN DROPDOWN -->

    <li class="dropdown user">

        <a href="#" class="dropdown-toggle" data-toggle="dropdown">

            <img alt="" src="${ctx}/s/media/image/avatar1_small.jpg"/>

            <span class="username"><%=((User) session.getAttribute(StringUtils.SYS_USER)).getUserName()%></span>

            <i class="icon-angle-down"></i>

        </a>

        <ul class="dropdown-menu">

            <%--<li><a href=""><i class="icon-user"></i>个人信息</a></li>--%>

            <li class="divider"></li>

            <li><a href="${ctx}/user/sys-log-out.do"><i class="icon-key"></i> 退出</a></li>

        </ul>

    </li>

    <!-- END USER LOGIN DROPDOWN -->

</ul>

<!-- END TOP NAVIGATION MENU -->

</div>

</div>

<!-- END TOP NAVIGATION BAR -->

</div>
<script src="${ctx}/s/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>

<script>
//    $(function () {
//
//        //TODO LIST：访问任务统计接口
//        var showTaskDetails = function (totalTasks, runningTasks, unfetchTasks) {
//            var endDate;//预留接口
//            var startDate;
//            var undo = 0;
//            var doing = 0;
//            var done = 0;
//            var total = 0;
//            $.ajax({
//                type: "POST",
//                url: $('#context').val() + "/task/getTaskCount.do",
//                dataType: "json",
//                data: {"startDate": startDate, "endDate": endDate},//startDate和endDate取本三十天内的，还未做
//                async: false,
//                success: function (res) {
//                    var jData = eval(res);
//                    if (jData.success == true) {
//                        var dataRes = res.data;
//                        var series = dataRes.length;
//                        for (var i = 0; i < series; i++) {
//                            if (dataRes[i].status == 0) {
//                                undo = dataRes[i].statusCount;
//                                total = total + undo;
//                            }
//                            if (dataRes[i].status == 1) {
//                                doing = dataRes[i].statusCount;
//                                total = total + doing;
//                            }
//                            if (dataRes[i].status == 2) {
//                                done = dataRes[i].statusCount;
//                                total = total + done;
//                            }
//
//                        }
//                        var doingValue = 0;
//                        var undoValue = 0;
//                        var doneValue = 0;
//                        if (total != 0) {
//                             doingValue = parseFloat(doing * 100 / total).toFixed(2);
//                             undoValue = parseFloat(undo * 100 / total).toFixed(2);
//                             doneValue = 100 - doingValue - undoValue;
//                        }
//                        $("#runningTasks_per").html(doingValue + "%");
//                        $("#runningTasks_bar").css("width", doingValue + "%");
//                        $("#unfetchingTasks_per").html(undoValue + "%");
//                        $("#unfetchingTasks_bar").css("width", undoValue + "%");
//                        $("#finishedTasks_per").html(doneValue + "%");
//                        $("#finishedTasks_bar").css("width", doneValue + "%");
//
//                    }
//                },
//                error: function (request) {
//
//                    alert("获取任务状态失败");
//
//                }
//            });
//
//
//            $("#title_runningTasks").html("本月任务");
//
//
//        };

//        showTaskDetails();

        //TODO LIST：访问标识器数据接口

//        var showMarkInfo = function () {
//
//        };


//    })
</script>