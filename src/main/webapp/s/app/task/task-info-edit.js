/**
 * Created by Administrator on 2015/7/26.
 */
var TaskInfoEdit = function () {

    return {

        initForms:function(){

            var form = $('#submit_form');
            var error = $('.alert-error', form);
            var success = $('.alert-success', form);

            $('#cancelBtn').live('click', function (e) {

                location.href = $("#context").val() + "/content/task/task-info-list.jsp";

            });

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'validate-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    patrolerId: {
                        required: true
                    },
                    description: {
                        required: true
                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
                    description:{
                        required: "请对工单进行描述"
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
                    $.ajax( {
                        type: "POST",
                        url: $('#context').val()+"/task/task-info-save.do",
                        //  dataType:'json',
                        //TODO LIST：按条件查询服务器数据
                        //   data: JSON.stringify($('#submit_form').serialize()),
                        data: $('#submit_form').serialize(),
                        success: function(data) {

                            var jData = eval(data);
                            if(jData.success==true) {

                                location.href = $("#context").val() + "/content/task/task-info-list.jsp";
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