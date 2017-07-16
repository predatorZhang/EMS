var MarkerInfoEdit = function () {
    function hiddenAll(){
        $(".c1").hide();
        $(".c2").hide();
        $(".c3").hide();
        $(".c4").hide();
        $(".c5").hide();
    }
    function doChange(){
        var type = $("#recordType").val();
        hiddenAll();
        if (type == 1) {//管线
            $(".c2only").remove();
            $(".c3only").remove();
            $(".c1").show();
        } else if (type == 2) {//管线附属物
            $(".c3only").remove();
            $(".c2").show();
        } else if (type == 3) {//管线特征点
            $(".c2only").remove();
            $(".c3").show();
        } else if (type == 4){//
            $(".c2only").remove();
            $(".c3only").remove();
            $(".c4").show();
        }else{
            $(".c2only").remove();
            $(".c3only").remove();
            $(".c5").show();
        }
    }

    function editFormInit() {
          doChange();
//        $("#selectObjectType").live("change", function (e) {
//           doChange();
//        })
    }

    return {
        initForms: function () {
            editFormInit();

            var form = $('#submit_form');
            var error = $('.alert-error', form);
            var success = $('.alert-success', form);

            $('#cancelBtn').live('click', function (e) {

                location.href = $("#context").val() + "/content/marker/marker-info-list.jsp";

            });

            form.validate({
                doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
                errorElement: 'span', //default input error message container
                errorClass: 'validate-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    longitude:{
                        number:true
                    },
                    latitude:{
                        number:true
                    },
                    x_cord:{
                        number:true
                    },
                    y_cord:{
                        number:true
                    }
                },
                messages: { // custom messages for radio buttons and checkboxes
                    longitude:{
                        number: "请输入有效的数字."
                    },
                    latitude:{
                        number: "请输入有效的数字."
                    },
                    x_cord:{
                        number: "请输入有效的数字."
                    },
                    y_cord:{
                        number: "请输入有效的数字."
                    }
                },
                errorPlacement: function (error, element) { // render error placement for each input type

                    error.insertAfter(element); // for other inputs, just perform default behavoir

                },

                invalidHandler: function (event, validator) { //display error alert on form submit
                    success.hide();
                    error.hide();
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
                        url: $('#context').val() + "/marker/save-marker.do",
                        //  dataType:'json',
                        //TODO LIST：按条件查询服务器数据
                        //   data: JSON.stringify($('#submit_form').serialize()),
                        data: $('#submit_form').serialize(),
                        success: function (data) {
                            var jData = eval(data);
                            if (jData.success) {

                                location.href = $("#context").val() + "/content/marker/marker-info-list.jsp";
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
            $('#cancek_btn').live("click", function (e) {
                location.href = $("#context").val() + "/content/marker/marker-info-list.jsp";
            })
        }

    };

}();