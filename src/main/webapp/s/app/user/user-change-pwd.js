/**
 * Created by lenovo on 2016/5/3.
 */
/**
 * Created by Administrator on 2015/7/26.
 */
var UserChangePwd = function () {

    return {

        initForms:function(){
            var form = $('#submit_form');
            var error = $('.alert-error', form);
            var success = $('.alert-success', form);
            $('#cancelBtn').live('click', function (e) {//取消的时候，到地图页
                location.href = $("#context").val() + "/content/gis/index.jsp";
            });

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'validate-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
//                    serviceName: {
//                        required: true
//                    },
//                    url: {
//                        required: true
//                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
//                    serviceName: {
//                        required: "请输入服务名称"
//                    },
//                    url:{
//                        required: "请输入服务URL"
//                    }
                },
                errorPlacement: function (error, element) { // render error placement for each input type

                    error.insertAfter(element); // for other inputs, just perform default behavoir

                },

                invalidHandler: function (event, validator) { //display error alert on form submit
                    success.hide();
                    error.show();
                    App.scrollTo(error, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.help-inline').removeClass('ok'); // display OK icon
                    $(element)
                        .closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change dony by hightlight
                    $(element)
                        .closest('.control-group').removeClass('error'); // set error class to the control group
                },

                success: function (label) {
                    label
                        .addClass('valid ok') // mark the current input as valid and display OK icon
                        .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group

                },

                submitHandler: function (form) {
                    var newPwd = $("#newPwd").val();
                    var confirmdpwd = $("#confirmpwd").val();
                    if(newPwd != confirmdpwd){
                        success.hide();
                        $("#errorMessage").html("两次密码不一致");
                        error.show();
                        return;
                    }
                    $.ajax( {
                        type: "POST",
                        url: $('#context').val()+"/user/change-user-pwd.do",
                        dataType:'json',
                        //TODO LIST：按条件查询服务器数据
                        //   data: JSON.stringify($('#submit_form').serialize()),
                        data: $('#submit_form').serialize(),
                        success: function(data) {

                            var jData = eval(data);
                            if(jData.success=='ok') {//成功，重新登录

                                location.href = $("#context").val() + "/login.jsp";
                            }
                            else{

                                success.hide();
                                $("#errorMessage").html(jData.message);
                                error.show();
                            }
                        },
                        error:function(request){
                            success.hide();
                            error.show();
                        }
                    });

                }
            });
        }

    };

}();