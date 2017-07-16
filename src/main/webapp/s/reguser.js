var RegUser = function () {

    return {

        initForms: function () {

            var form = $('#submit_form');
            var error = $('.alert-error', form);
            var success = $('.alert-success', form);

            $('#cancelBtn').live('click', function (e) {

                location.href = $("#context").val() + "/login.jsp";

            });

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'validate-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    username: {
                        required: true,
                        remote: {
                            url: $('#context').val() + '/user/validate-account.do',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                username: function () {
                                    return $('#username').val();
                                }
                            },
                            dataFilter: function (data) {
                                if (data == "true") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    },
                    password: {
                        required: true
                    },
                    confirm: {
                        required: true,
                        equalTo: '#password'
                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
                    username: {
                        required: "账号不能为空！",
                        remote: "此账号已存在！"
                    },
                    password: {
                        required: "密码不能为空！"
                    },
                    confirm: {
                        required: "请输入确认密码！",
                        equalTo: '密码不一致，请重新确认！'
                    }
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
                    //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
                    $.ajax({
                        type: "POST",
                        url: $('#context').val() + "/user/user-info-reg.do",
                        dataType:'json',
                        //TODO LIST：按条件查询服务器数据
                        //   data: JSON.stringify($('#submit_form').serialize()),
                        data: $('#submit_form').serialize(),
                        success: function (data) {
                            var jData = data;
                            if (jData.success == true) {

                                //location.href = $("#context").val() + "/content/auth/resc-info-list.jsp";
                                success.show();
                            }
                            else {

                                success.hide();
                                $("#errorMessage").html(jData.message);
                                error.show();

                            }
                        },
                        error: function (request) {
                            success.hide();
                            error.show();
                        }
                    });
                }
            });
        }

    };

}();