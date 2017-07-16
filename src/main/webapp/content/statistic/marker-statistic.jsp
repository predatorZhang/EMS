<%@page contentType="text/html;charset=UTF-8" %>

<%@include file="/taglibs.jsp" %>

<%pageContext.setAttribute("currentMenu", "statistic");%>
<%pageContext.setAttribute("secondMenu", "marker");%>
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
    <link href="${ctx}/s/media/css/bootstrap-toggle-buttons.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/datepicker.css"/>
    <!-- END PAGE LEVEL STYLES -->
    <link rel="shortcut icon" href="${ctx}/s/media/image/ht.jpg"/>
    <style type="text/css">
        <!-- h3 {
            font-size: 1em;
            font-weight: normal;
        }

        table > thead > tr > td,
        .statistic-table > thead > tr > th {
            background-color: #343434;
            color: white;
        }

        .table-striped > tbody > tr:nth-child(2n+1) > td {
            background-color: #ccdf88;
        }

        .table-striped.numformat > tbody > tr > td {
            text-align: right;
            vertical-align: top;
        }

        .table-striped th.hidden,
        .table-striped td.hidden {
            position: absolute;
            visibility: hidden;
        }
        .input-append{
            margin-left: 10px;
            margin-right: 10px;
        }
        -->
    </style>
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
    <div class="page-content" style="margin-top: 2em">
        <div class="container-fluid">

            <div class="clearfix"></div>

            <%@include file="/content/statistic/second-menu-marker-statistic.jsp"%>

            <div class="row-fluid">

                <div class="span12">

                    <div class="portlet box blue">

                        <div class="portlet-title">
                            <div class="caption"><i class="icon-edit"></i>电子标识器统计</div>
                        </div>

                        <div class="portlet-body">
                            <div class="row-fluid">
                                  <div class="span12">
                                        <div class="m-wrap input-append date date-picker"
                                             data-date-format="yyyy-mm-dd" data-date-viewmode="years">
                                            <input style="max-width: 6em"
                                                   class="m-wrap m-ctrl-medium date-picker" size="10"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd" value=""
                                                   id="txt_begin_day" placeholder="开始日期"/>
                                                    <span class="add-on">
                                                        <i class="icon-calendar"></i>
                                                    </span>
                                        </div>
                                        <div class="m-wrap input-append date date-picker"
                                             data-date-format="yyyy-mm-dd" data-date-viewmode="years">
                                            <input style="max-width: 6em"
                                                   class="m-wrap m-ctrl-medium date-picker" size="10"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd" value=""
                                                   id="txt_end_day" placeholder="结束日期"/>
                                                    <span class="add-on">
                                                        <i class="icon-calendar"></i>
                                                    </span>
                                        </div>
                                        <div class="text-toggle-button input-append">
                                            <input type="checkbox" class="toggle" id="object-type"/>
                                        </div>
                                        <button type="submit" id="searchMarker" class="btn blue input-append" style="margin-bottom: 10px">查询</button>
                                        <button  onclick ="$('.statistic-table').tableExport({type:'excel',fileName:'按照权属单位统计电子标识器', excelstyles:['border-bottom', 'border-top', 'border-left', 'border-right']});" class="btn blue input-append" style="margin-bottom: 10px"><i class="icon-add"></i>导出Excel</button>
                                        <button  onclick ="$('.statistic-table').tableExport({type:'word',fileName:'按照权属单位统计电子标识器'});" class="btn blue input-append" style="margin-bottom: 10px"><i class="icon-add"></i>导出Word</button>
                                   </div>
                                    <!--TODO LIST:此处把table_user改成相关子模块的ID即可-->
                                   <div class="span6" style="height: 28.5em;" id="echart-div"></div>
                                   <div class="span5" style="height: 28.5em;" id="report-div">
                                       <table width="100%"  class="statistic-table" id="statistic-table" border="1em">

                                       </table>
                                   </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <!--End the content-->
        </div>
    </div>
    <!-- END PAGE CONTAINER-->
    <div class="clearfix"></div>

</div>
<!-- END CONTAINER -->
<!-- BEGIN CORE PLUGINS -->
<script src="${ctx}/s/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<![endif]-->
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/s/echarts/echarts.js"></script>
<script src="${ctx}/s/media/js/app.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/s/media/js/jquery.toggle.buttons.js"></script>
<script type="text/javascript" src="${ctx}/s/app/statistic/marker-statistic.js"></script>
<script type="text/javascript" src="${ctx}/s/bootstrap-table/FileSaver.min.js"></script>
<script type="text/javascript" src="${ctx}/s/bootstrap-table/xlsx.core.min.js"></script>
<script type="text/javascript" src="${ctx}/s/bootstrap-table/tableExport.js"></script>
<script>
    var handleDatePickers = function () {
        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                autoclose: true
            });
        }
    }
    $('.text-toggle-button').toggleButtons({
        label: {
            enabled: "阴保桩",
            disabled: "全部"
        }
    });
    $(function () {
        App.init(); // initlayout and core plugins
        handleDatePickers();
        MarkerStatistic.initForOwnerComp();
    })
    $("#searchMarker").click(function(){
        var beginDate = $("#txt_begin_day").val();
        var endDate = $("#txt_end_day").val();
        var recordType = $("#object-type").is(':checked')?"2":"";//2是阴保桩的类型编号
        MarkerStatistic.initForOwnerComp(beginDate,endDate,recordType)
    });
</script>

<!-- END JAVASCRIPTS -->
<!-- END BODY -->
</body>

</html>

