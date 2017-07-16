<%@page contentType="text/html;charset=UTF-8" %>

<%@include file="/taglibs.jsp" %>

<%pageContext.setAttribute("currentMenu", "task");%>

<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if IE 10]> <html lang="en" class="ie10"> <![endif]-->
<!--[if IE 11]> <html lang="en" class="ie11"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en"> <!--<![endif]-->

<head>
    <meta charset="utf-8"/>
    <title>地下管线电子标识系统</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <%@include file="/common/layout/common-css.html"%>

    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${ctx}/s/media/css/jquery.fancybox.css" rel="stylesheet"/>
    <link href="${ctx}/s/media/css/chosen.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/list/list.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/chosen.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/select2_metro.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/bootstrap-fileupload.css"/>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css"/>
    <!--加载鼠标绘制工具-->
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
    <!-- END PAGE LEVEL STYLES -->
    <link rel="shortcut icon" href="${ctx}/s/media/image/ht.jpg"/>

    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=ldIdfo22fMLn0g3bCWFnuuelk6WbFGQI"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">
<input id="context" type="hidden" value="${ctx}">
<!-- BEGIN HEADER -->
<%@include file="/common/layout/header.jsp" %>
<!-- END HEADER --
<!-- BEGIN CONTAINER -->
<div class="page-container">

    <!-- BEGIN SIDEBAR -->

    <%@include file="/common/layout/left-menu.jsp" %>

    <!-- END SIDEBAR -->

    <div class="page-content">

        <!-- BEGIN PAGE CONTAINER-->

        <div class="container-fluid">

            <div class="row-fluid">

                <div class="span8">

                    <div style="width:100%;border:0px solid gray" id="container"></div>

                </div>

                <div class="span4">

                    <!--历史工单信息-->
                    <div class="row-fluid">

                        <div class="portlet box blue">

                            <div class="portlet-title">
                                <div class="caption"><i class="icon-reorder"></i>历史工单</div>
                            </div>

                            <div class="portlet-body">
                                <div id="worksheets">
                                    <%--巡检员：<input class="search" placeholder="Search" hidden="hidden" style="width: 100px"/>--%>
                                    <div class="control-group">
                                        <div class="controls">
                                            <select class="" data-with-diselect="1"
                                                    data-placeholder="请选择巡检人员" tabindex="1" name="patrolerId"
                                                    id="selectPatroler3">
                                                <option value=""></option>
                                            </select>
                                            <button class="sort" data-sort="deployDate" id="btnSort">
                                                时间排序
                                            </button>
                                        </div>
                                    </div>
                                    <ul class="list">
                                    </ul>
                                    <ul class="pagination"></ul>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>
            </div>

        </div>

        <!-- END PAGE CONTAINER-->

        <div class="clearfix"></div>

    </div>

</div>


<!--事件展示对话框-->
<div id="eventModal" class="modal hide fade" tabindex="-1" data-focus-on="input:first">

    <div class="modal-header">

        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>

        <h3>事件详情</h3>

    </div>

    <div class="modal-body">
            <div class="row-fluid hidden" id="viewHelper">
                <div class="span4">
                    <div class="item">
                        <a class="fancybox-button hidden" data-rel="fancybox-button" title="Photo"
                           href="${ctx}/images/events/image1.jpg">
                            <div class="zoom">
                                <img src="${ctx}/images/events/image1.jpg" alt="Photo"/>

                                <div class="zoom-icon hidden"></div>
                            </div>
                        </a>

                        <div class="details">
                            <a href="#" class="icon"><i class="icon-paper-clip"></i></a>
                            <a href="#" class="icon"><i class="icon-link"></i></a>
                            <a href="#" class="icon"><i class="icon-pencil"></i></a>
                            <a href="#" class="icon"><i class="icon-remove"></i></a>
                        </div>
                    </div>
                </div>
            </div>

        <div class="hidden">
            <div class="controls">
                <input type="text" name="id" value="" id="eventId" />
            </div>
        </div>
        <div>
            <%--<label class="control-label">事件描述</label>--%>
            <div>
                <div id="eventDescription" align="left"></div>
            </div>
        </div>
        <div>

            <p id="eventImg"></p>
            <%--<p id="eventPagination" class="pagination"></p>--%>


            <%--</form>--%>
            <!-- END FORM-->
        </div>

    </div>

    <div class="modal-footer">

    </div>

</div>

<!--绘制区域添加标识对话框-->
<div id="addTaskModal2" class="modal hide fade" tabindex="-1" data-focus-on="input:first">

    <div class="modal-header">

        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>

        <h3>新建工单</h3>

    </div>

    <div class="modal-body">

        <div class="portlet-body form">
            <!-- BEGIN FORM-->
            <form action="#" class="form-horizontal">
                <%--隐藏字段--%>
                <!--隐藏主键-->
                <%--<div class="control-group hidden">--%>
                    <%--<label class="control-label">主键ID</label>--%>

                    <%--<div class="controls">--%>
                        <%--&lt;%&ndash;<s:if test="model != null">&ndash;%&gt;--%>
                        <%--<input type="text" name="id" value="${model.id}" id="id2"/>--%>
                        <%--&lt;%&ndash;</s:if>&ndash;%&gt;--%>
                        <%--<span class="help-inline"></span>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <div class="control-group">

                    <label class="control-label">巡检人员<span class="required">*</span></label>

                    <div class="controls">
                        <select class="" data-with-diselect="1"
                                data-placeholder="选择巡检人员" tabindex="1" name="patrolerId" id="selectPatroler2">
                        </select>
                    </div>

                </div>

                <div class="control-group">

                    <label class="control-label">工单描述<span class="required">*</span></label>
                    <div class="controls">
                        <textarea class="large m-wrap" rows="5" name="description" id="description2"></textarea>
                    </div>

                </div>

            </form>
            <!-- END FORM-->
        </div>

    </div>

    <div class="modal-footer">

        <button type="button" data-dismiss="modal" class="btn">关闭</button>

        <button type="button" class="btn green" id="saveTask2">确定</button>

    </div>

</div>
<!-- END CONTAINER -->

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<script src="${ctx}/s/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/bootstrap.min.js" type="text/javascript"></script>

<!--[if lt IE 9]>
<![endif]-->
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script language="javascript" type="text/javascript" src="${ctx}/s/layout/jquery.layout.min.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/DT_bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-fileupload.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/select2.min.js"></script>
<script src="${ctx}/s/media/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/chosen.jquery.min.js"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
<script type="text/javascript" src="${ctx}/s/list/list.min.js"></script>
<script type="text/javascript" src="${ctx}/s/list/list.pagination.min.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="${ctx}/s/media/js/app.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/json2.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/EmsControl.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/EmsEditControl.js" type="text/javascript"></script>
<script src="${ctx}/s/app/task/task.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/gps-bd-Utils.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/gallery.js"></script>
<script src="${ctx}/s/app/ems/MarkerInfo.js" type="text/javascript"></script>
<script>

    $(function () {
        Gallery.init();
        App.init(); // initlayout and core plugins
        $('.hidden-phone').trigger("click");
        Task.initMap();
        Task.initList();
        Task.initForm();
        Task.initSelect();


    })

    function enlargeImage() {
    }
</script>

<!-- END JAVASCRIPTS -->
<!-- END BODY -->
</body>

</html>

