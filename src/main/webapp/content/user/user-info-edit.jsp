<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/taglibs.jsp" %>
<%pageContext.setAttribute("currentMenu", "user");%>

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
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/bootstrap-fileupload.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/jquery.gritter.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/chosen.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/select2_metro.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/jquery.tagsinput.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/clockface.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/bootstrap-wysihtml5.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/datepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/timepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/colorpicker.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/bootstrap-toggle-buttons.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/daterangepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/s/media/css/multi-select-metro.css"/>
    <link href="${ctx}/s/media/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
    <!-- END PAGE LEVEL STYLES -->
    <link rel="shortcut icon" href="${ctx}/s/media/image/ht.jpg"/>

    <style type="text/css">

        #table_user th {
            font-size: 10pt;
            color: #ffffff;
        }

        #table_user thead tr {
            background-color: #3D3D3D;
        }

    </style>

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">

<input type="hidden" id="context" value="${ctx}"/>

<!-- BEGIN HEADER -->
<%@include file="/common/layout/header.jsp" %>
<!-- END HEADER -->

<!-- BEGIN CONTAINER -->
<div class="page-container">

    <!-- BEGIN SIDEBAR -->
    <%@include file="/common/layout/left-menu.jsp" %>
    <!-- END SIDEBAR -->

    <!-- BEGIN PAGE -->
    <div class="page-content">

        <!-- BEGIN PAGE CONTAINER-->
        <div class="container-fluid">

            <div class="clearfix"></div>

            <!--BEGIN SECOND MENU-->
            <%--<%@include file="/common/layout/second-menu-user.jsp"%>--%>
            <!--BEGIN SECOND MENU-->

            <div class="row-fluid">
                <div class="span12"></div>
            </div>

            <!--BEGIN PAGE CONTENT-->
            <div class="row-fluid">

                <div class="span12">

                    <div class="portlet box blue">

                        <div class="portlet-title">
                            <div class="caption"><i class="icon-edit"></i>用户信息编辑</div>
                        </div>

                        <div class="portlet-body form">
                            <div class="portlet-body form">
                                <form action="" class="form-horizontal" id="submit_form">
                                    <div class="alert alert-error hide">
                                        <button class="close" data-dismiss="alert"></button>
                                        操作失败：
                                        <span id="errorMessage"></span>
                                    </div>

                                    <div class="alert alert-success hide">
                                        <button class="close" data-dismiss="alert"></button>
                                        添加成功
                                    </div>

                                    <!--用户名称与密码 -->
                                    <div class="control-group">
                                        <label class="control-label">用户名<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" id="userName" class="m-wrap span6" name="userName"
                                                   value="${model.userName}">
                                            <span class="help-inline"> </span>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label class="control-label">密码<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" id="password" class="m-wrap span6" name="password"
                                                   value="${model.password}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <label class="control-label">性别<span class="required">*</span></label>
                                        <div class="controls">
                                            <label class="radio">
                                                <input type="radio" name="sex" value="男" <c:if test="${model.sex == '男'||model.sex == null}">checked</c:if>/>
                                                男
                                            </label>
                                            <label class="radio">
                                                <input type="radio" name="sex" value="女" <c:if test="${model.sex == '女'}">checked</c:if> />
                                                女
                                            </label>
                                        </div>
                                    </div>


                                    <%--<div class="control-group">--%>
                                        <%--<label class="control-label">账号类型<span class="required">*</span></label>--%>
                                        <%--<div class="controls">--%>
                                            <%--<label class="radio">--%>
                                                <%--<input type="radio" name="role" value="1" <c:if test="${model.roleType == 1}">checked</c:if>/>--%>
                                                <%--普通用户--%>
                                            <%--</label>--%>
                                            <%--<label class="radio">--%>
                                                <%--<input type="radio" name="role" value="0" <c:if test="${model.roleType == 0}">checked</c:if> />--%>
                                                <%--管理员--%>
                                            <%--</label>--%>
                                            <%--&lt;%&ndash;<label class="radio">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<input type="radio" name="optionsRadios1" value="2"/>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;其他&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</label>&ndash;%&gt;--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <div class="control-group">

                                        <label class="control-label">权属单位<span class="required">*</span></label>

                                        <div class="controls">

                                            <select class="span6 chosen" data-with-diselect="1"
                                                    data-placeholder="权属单位" tabindex="1" name="companyId">

                                                <c:forEach items="${companies}" var="item">

                                                    <option value="${item.id}"  ${ item.id==model.companyId ? 'selected' : '' }>${item.companyName}</option>

                                                </c:forEach>

                                            </select>

                                        </div>

                                    </div>

                                    <div class="control-group">

                                        <label class="control-label">人员角色<span class="required">*</span></label>

                                        <div class="controls">

                                            <select class="span6 chosen" data-with-diselect="1"
                                                    data-placeholder="选择人员角色" tabindex="1" name="roleId">

                                                <c:forEach items="${roles}" var="item">

                                                    <option value="${item.id}"  ${ item.id==model.roleId ? 'selected' : '' }>${item.roleName}</option>

                                                </c:forEach>

                                            </select>

                                        </div>

                                    </div>

                                    <div class="control-group">
                                        <label class="control-label">电话</label>
                                        <div class="controls">
                                            <input type="text" id="phoneNumber" class="m-wrap span6" name="phoneNumber"
                                                   value="${model.phoneNumber}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>


                                    <%--隐藏字段--%>
                                    <div class="control-group hidden">
                                        <label class="control-label">是否有效</label>
                                        <div class="controls">
                                            <input type="text" id="isValid" class="m-wrap span6" name="isValid"
                                                   value="${model.isValid}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <%--隐藏字段--%>
                                    <!--隐藏主键-->
                                    <div class="control-group hidden">
                                        <label class="control-label">主键ID</label>
                                        <div class="controls">
                                            <%--<s:if test="model != null">--%>
                                                <input type="text" name="id" value="${model.id}" id="id"/>
                                            <%--</s:if>--%>
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>

                                    <div class="form-actions">
                                        <button type="submit" class="btn blue">提交</button>
                                        <button type="button" class="btn" id="cancelBtn">取消</button>
                                    </div>

                                </form>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <!--END PAGE CONTENT-->

        </div>

        <!-- END PAGE CONTAINER-->

        <div class="clearfix"></div>

    </div>

    <!-- END PAGE -->

</div>

<!-- END CONTAINER -->

<!-- BEGIN FOOTER -->

<%@include file="/common/layout/footer.jsp" %>

<!-- END FOOTER -->

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

<!-- BEGIN CORE PLUGINS -->

<script src="${ctx}/s/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>

<script src="${ctx}/s/media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>

<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->

<script src="${ctx}/s/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>

<script src="${ctx}/s/media/js/bootstrap.min.js" type="text/javascript"></script>

<!--[if lt IE 9]>

<script src="${ctx}/s/media/js/excanvas.min.js"></script>

<script src="${ctx}/s/media/js/respond.min.js"></script>

<![endif]-->

<script src="${ctx}/s/media/js/jquery.slimscroll.min.js" type="text/javascript"></script>

<script src="${ctx}/s/media/js/jquery.blockui.min.js" type="text/javascript"></script>

<script src="${ctx}/s/media/js/jquery.cookie.min.js" type="text/javascript"></script>

<script src="${ctx}/s/media/js/jquery.uniform.min.js" type="text/javascript"></script>

<!-- END CORE PLUGINS -->

<!-- BEGIN PAGE LEVEL PLUGINS -->

<%--<script type="text/javascript" src="${ctx}/s/media/js/ckeditor.js"></script>--%>

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-fileupload.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/chosen.jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/select2.min.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/wysihtml5-0.3.0.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-wysihtml5.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/jquery.tagsinput.min.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/jquery.toggle.buttons.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-datepicker.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-datetimepicker.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/clockface.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/date.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/daterangepicker.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-colorpicker.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/bootstrap-timepicker.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/jquery.inputmask.bundle.min.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/jquery.input-ip-address-control-1.0.min.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/jquery.multi-select.js"></script>

<script src="${ctx}/s/media/js/bootstrap-modal.js" type="text/javascript"></script>

<script src="${ctx}/s/media/js/bootstrap-modalmanager.js" type="text/javascript"></script>


<script type="text/javascript" src="${ctx}/s/media/js/jquery.validate.min.js"></script>

<script type="text/javascript" src="${ctx}/s/media/js/additional-methods.min.js"></script>

<!-- END PAGE LEVEL PLUGINS -->

<!-- BEGIN PAGE LEVEL SCRIPTS -->

<script src="${ctx}/s/media/js/app.js" type="text/javascript"></script>

<script src="${ctx}/s/app/user/user-info-edit.js"></script>

<!-- END PAGE LEVEL SCRIPTS -->

<script>

    jQuery(document).ready(function () {

        App.init();

        UserInfoEdit.initForms();
    });

</script>

<!-- END JAVASCRIPTS -->

<!-- END BODY -->
</body>
</html>
</html>