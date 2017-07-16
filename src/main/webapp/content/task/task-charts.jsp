<%@page contentType="text/html;charset=UTF-8" %>

<%@include file="/taglibs.jsp" %>

<%pageContext.setAttribute("currentMenu", "task-charts");%>

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
    <%--<link href="${ctx}/s/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="${ctx}/s/media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="${ctx}/s/media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="${ctx}/s/media/css/style-metro.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="${ctx}/s/media/css/style.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="${ctx}/s/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="${ctx}/s/media/css/blue.css" rel="stylesheet" type="text/css" id="style_color"/>--%>
    <%--<link href="${ctx}/s/media/css/uniform.default.css" rel="stylesheet" type="text/css"/>--%>
    <%@include file="/common/layout/common-css.html"%>

    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN PAGE LEVEL STYLES -->
    <%--<link rel="stylesheet" type="text/css" href="${ctx}/s/list/list.css"/>--%>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/select2_metro.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/datepicker.css"/>
    <!-- END PAGE LEVEL STYLES -->
    <link rel="shortcut icon" href="${ctx}/s/media/image/ht.jpg"/>
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
        <div class="span12"><span></span></div>
        <div class="row-fluid">

            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form action="#" class="form-horizontal">
                    <div class="control-group span5">
                        <label class="control-label">开始时间：</label>

                        <div class="controls">
                            <div class="input-append date date-picker" value=""
                                 data-date-format="yyyy-mm-dd" data-date-viewmode="years">
                                <input class="m-wrap m-ctrl-medium date-picker" id="startDate" readonly size="16" type="text"
                                       data-date-format="yyyy-mm-dd"   value=""/><span class="add-on"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>

                    <div class="control-group span5">
                        <label class="control-label">结束时间：</label>

                        <div class="controls">
                            <div class="input-append date date-picker" value=""
                                 data-date-format="yyyy-mm-dd" data-date-viewmode="years">
                                <input class="m-wrap m-ctrl-medium date-picker" id="endDate" readonly size="16" type="text"
                                       data-date-format="yyyy-mm-dd"    value=""/><span class="add-on"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="span2">
                        <button type="submit" id="changeCharts" class="btn blue"><i class="icon-ok"></i>图表展示</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="container-fluid">
            <%--echarts展示位置行--%>
            <div class="row-fluid">

                <div class="span5">
                    <%--echarts展示位置列--%>
                    <div class="portlet box yellow">
                        <div class="portlet-title">
                            <div class="caption"><i class="icon-reorder"></i>任务占比</div>
                            <div class="tools">
                                <a href="#portlet-config" data-toggle="modal" class="config"></a>
                                <a href="javascript:;" class="reload"></a>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <h4></h4>

                            <div id="interactive" class="chart"></div>
                        </div>
                    </div>
                </div>

                <div class="span7">
                    <%--任务详情展示列--%>
                    <div>
                        <div class="row-fluid">

                            <div class="span12">

                                <div class="portlet box blue">

                                    <div class="portlet-title">

                                        <!--TODO LIST：换成模块名称，即使一级标题的名称-->
                                        <div class="caption"><i class="icon-edit"></i>任务详情</div>

                                        <div class="tools">

                                            <a href="javascript:;" class="collapse"></a>

                                        </div>

                                    </div>

                                    <div class="portlet-body">

                                        <div class="row-fluid">

                                            <!--TODO LIST:增加相应的表单以及表格信息-->
                                            <div class="portlet-body">
                                                <div class="clearfix">
                                                </div>

                                                <!--TODO LIST:此处把table_user改成相关子模块的ID即可-->
                                                <table class="table table-striped table-bordered table-hover"
                                                       id="table_task">

                                                    <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>任务编号</th>
                                                        <th>创建时间</th>
                                                        <%--<th>开始时间</th>--%>
                                                        <th>状态</th>
                                                        <%--<th>结束日期</th>--%>
                                                        <th>创建人</th>
                                                        <%--<th>巡检人编号</th>--%>
                                                        <%--<th>巡检人姓名</th>--%>
                                                    </tr>

                                                    </thead>

                                                </table>

                                            </div>

                                        </div>

                                    </div>

                                </div>

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

<!-- END CONTAINER -->


<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<script src="${ctx}/s/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>


<script src="${ctx}/s/media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<![endif]-->
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->

<script type="text/javascript" src="${ctx}/s/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/DT_bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/jquery.flot.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/jquery.flot.pie.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="${ctx}/s/media/js/app.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/json2.js" type="text/javascript"></script>
<script src="${ctx}/s/app/task/task-charts.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/form-components.js"></script>

<script>

    $(function () {

        App.init(); // initlayout and core plugins
        TaskCharts.init();
        TaskCharts.initPieCharts();
        TaskCharts.initTable();
        FormComponents.init();
    })
</script>

<!-- END JAVASCRIPTS -->
<!-- END BODY -->
</body>

</html>

