var Login = function () {

	var form;
	var error;

    return {
        //main function to initiate the module
        init: function () {

			 form = $('.login-form');
			 error = $('.alert-error', form);

			$('.login-form').validate({
				doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                userName: {
	                    required: true
	                },
					passWord: {
	                    required: true
	                }
	            },
	            messages: {
					userName: {
	                    required: "用户名不能为空"
	                },
					passWord: {
	                    required: "密码不能为空"
	                }
	            },
	            invalidHandler: function (event, validator) { //display error alert on form submit   
/*
	                $('.alert-error', $('.login-form')).show();
*/
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

				unhighlight: function (element) { // revert the change dony by hightlight
					$(element)
						.closest('.control-group').removeClass('error'); // set error class to the control group
				},

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
					error.insertAfter(element); // for other inputs, just perform default behavoir	            },
				},

	            submitHandler: function (form) {

					Login.submitForm();
	            }
	        });

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
						Login.submitForm();
	                }
	                return false;
	            }
	        });

        },

		submitForm:function() {

			$.ajax({

				type: "POST",

				url: $('#context').val()+"/user/login.do",

				data: $('#loginForm').serialize(),

				success: function (data) {

					var jData = eval(data);

					if (jData.success == true) {

						window.location.href = $('#context').val()+"/content/ems/ems.jsp";

					}
					else {
						$("#errorMessage").html(jData.message);
						error.show();
					}
				},

				error: function (request) {
					$("#errorMessage").html("连接服务器失败");
					error.show();
				}
			});
		}

    };

}();