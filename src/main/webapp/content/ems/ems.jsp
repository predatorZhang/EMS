<%@page contentType="text/html;charset=UTF-8" %>

<%@include file="/taglibs.jsp" %>

<%pageContext.setAttribute("currentMenu", "ems");%>

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

    <link href="${ctx}/css/baidu.css" rel="stylesheet" type="text/css"/>

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

        <div class="container-fluid" style="padding-left: 5px;padding-top: 5px">

            <div class="row-fluid">
                <div class="span12">
                    <div style="width:100%;border:0px solid gray" id="container"></div>
                </div>
            </div>

        </div>
        <!-- END PAGE CONTAINER-->
        <div class="clearfix"></div>

    </div>

</div>

<!--上传对话框-->
<div id="uploadModal" class="modal hide fade" tabindex="-1" data-focus-on="input:first">

    <div class="modal-header">

        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>

        <h3>批量导入</h3>

    </div>

    <div class="modal-body">

        <div class="portlet-body form">
            <!-- BEGIN FORM-->
            <form action="#" class="form-horizontal" id="excelForm" method="post" enctype="multipart/form-data">
                <div class="control-group">
                    <label class="control-label">仅支持xls类型的文件</label>

                    <div class="controls">
                        <div class="fileupload fileupload-new" data-provides="fileupload">
												<span class="btn btn-file green">
												<span class="fileupload-new">选择EXCEL</span>
												<span class="fileupload-exists">更改</span>
												<input type="file" name="excelFile" class="default" id="excelFile"/>
												</span>
                            <span class="fileupload-preview"></span>
                            <a href="#" class="close fileupload-exists" data-dismiss="fileupload"
                               style="float: none"></a>
                        </div>
                    </div>
                </div>

            </form>
            <!-- END FORM-->
        </div>

    </div>

    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">关闭</button>
        <button type="button" class="btn yellow" id="excel_import">确定</button>
    </div>

</div>


<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<script src="${ctx}/s/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/s/media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<![endif]-->
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${ctx}/s/media/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-fileupload.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-datepicker.js"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>

<!-- END PAGE LEVEL SCRIPTS -->
<script src="${ctx}/s/media/js/app.js" type="text/javascript"></script>
<%--<script src="${ctx}/s/media/js/json2.js" type="text/javascript"></script>--%>
<script src="${ctx}/s/app/ems/ems.js" type="text/javascript"></script>

<script src="${ctx}/s/app/ems/EmsControl.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/MarkerInfo.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/EmsEditControl.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/ems-marker-opt.js" type="text/javascript"></script>
<script src="${ctx}/s/app/ems/gps-bd-Utils.js" type="text/javascript"></script>
<script src="${ctx}/s/app/util/optionitemprocess.js" type="text/javascript"></script>
<script src="${ctx}/s/baiduTemplate/baiduTemplate.js" type="text/javascript"></script>

<script>

    $(function () {

        App.init(); // initlayout and core plugins
        $('.hidden-phone').trigger("click");
        Ems.initMap();
//        Ems.initForm();
        EmsMarkerOpt.init();

        $('.date-picker').datepicker({
            autoclose: true,
            forceParse: false,
            language: 'zh-CN'
        });

    })
</script>

<!-- END JAVASCRIPTS -->
<!-- END BODY -->
</body>

</html>

