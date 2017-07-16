<%@page contentType="text/html;charset=UTF-8" %>

<%@include file="/taglibs.jsp" %>

<%pageContext.setAttribute("currentMenu", "statistic");%>
<%pageContext.setAttribute("secondMenu", "testPile");%>
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
        .btn{
            margin-bottom: 10px;
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

            <%@include file="/content/statistic/second-menu-test-pile.jsp"%>

            <div class="row-fluid">

                <div class="span12">

                    <div class="portlet box blue">

                        <div class="portlet-title">
                            <div class="caption"><i class="icon-edit"></i>测试桩(井)统计</div>
                        </div>

                        <div class="portlet-body">
                            <div class="row-fluid">
                                  <div class="span12">
                                            <div class="m-wrap input-append date date-picker"
                                                 data-date-format="yyyy" data-date-viewmode="years">
                                                <input style="max-width: 6em"
                                                       class="m-wrap m-ctrl-medium date-picker" size="10"
                                                       type="text"
                                                       data-date-format="yyyy" value=""
                                                       id="txt_begin_day" placeholder="年份"/>
                                                        <span class="add-on">
                                                            <i class="icon-calendar"></i>
                                                        </span>
                                            </div>
                                              <input class="input-append" style="max-width: 6em" placeholder="权属单位" type="text" class="m-wrap" id="belong-comp" list="belongCompList"/>
                                              <datalist id="belongCompList">
                                                  <option value="全部">全部</option>
                                                  <option value="高压公司">高压公司</option>
                                                  <option value="一分公司">一分公司</option>
                                                  <option value="二分公司">二分公司</option>
                                                  <option value="三分公司">三分公司</option>
                                                  <option value="四分公司">四分公司</option>
                                                  <option value="五分公司">五分公司</option>
                                                  <option value="密云公司">密云公司</option>
                                                  <option value="昌平公司">昌平公司</option>
                                                  <option value="延庆公司">延庆公司</option>
                                                  <option value="怀柔公司">怀柔公司</option>
                                                  <option value="平谷公司">平谷公司</option>
                                                  <option value="建管">建管</option>
                                                  <option value="用户工程">用户工程</option>
                                              </datalist>
                                            <button type="submit" id="searchMarker" class="btn blue input-append">查询</button>
                                            <button  onclick ="$('.statistic-table').tableExport({type:'excel',fileName:'测试桩(井)合格统计报表', excelstyles:['border-bottom', 'border-top', 'border-left', 'border-right']});" class="btn blue input-append"><i class="icon-add"></i>导出Excel</button>
                                            <button  onclick ="$('.statistic-table').tableExport({type:'word',fileName:'测试桩(井)合格统计报表'});" class="btn blue input-append"><i class="icon-add"></i>导出Word</button>
                                        </div>
                                    <!--TODO LIST:此处把table_user改成相关子模块的ID即可-->
                                   <div class="span6" style="height: 28.5em;" id="echart-pie-div"></div>
                                   <div class="span5" style="height: 28.5em;">
                                       <div style="width:100%;height: 16em;" id="echart-bar-div"></div>
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
<script type="text/javascript" charset="utf-8" src="${ctx}/s/echarts/echarts.js"></script>
<script src="${ctx}/s/media/js/app.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/s/app/statistic/test-pile-statistic.js"></script>
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
    $(function () {
        App.init(); // initlayout and core plugins
        handleDatePickers();
        TestPileStats.initForPotentialReport2();
    })
    $("#searchMarker").click(function(){
        var year = $("#txt_begin_day").val();
        var ownerComp = $("#belong-comp").val();
        TestPileStats.initForPotentialReport2(ownerComp,year)
    });
    $('.date-picker').datepicker({
        format: 'yyyy',
        weekStart: 1,
        autoclose: true,
        startView: 2,
        maxViewMode: 2,
        minViewMode:2,
        forceParse: false,
        language: 'zh-CN'
    });
</script>

<!-- END JAVASCRIPTS -->
<!-- END BODY -->
</body>

</html>

