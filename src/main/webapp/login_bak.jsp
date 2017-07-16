<%@page contentType="text/html;charset=UTF-8" %>

<%@include file="/taglibs.jsp" %>

<!DOCTYPE html>

<html>

<head lang="en">

    <meta charset="UTF-8">

    <title></title>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

    <meta http-equiv="Cache-Control" content="no-store"/>

    <meta http-equiv="Pragma" content="no-cache"/>

    <meta http-equiv="Expires" content="0"/>

    <link type="text/css" rel="stylesheet" href="${ctx}/s/login/LoginCss.css" media="screen"/>

    <script type="text/javascript" src="${ctx}/s/jquery-easyui-1.4.3/jquery.min.js"></script>

    <script type="text/javascript">

        if (window != top){

            top.location.href = location.href;

        }

        var submitForm = function () {

            $.ajax({

                type: "POST",

                url: "${ctx}/user/login.do",

                data: $('#loginForm').serialize(),

                success: function (data) {

                    var jData = eval(data);

                    if (jData.success == true) {

                        window.location.href = "${ctx}/content/gis/index.jsp";

                    }
                    else {
                        alert(jData.message);
                    }

                },

                error: function (request) {

                    alert("登录失败！");

                }
            });

        };

        var regUser = function(){

            window.location.href = '${ctx}/reguser.jsp';

        }

    </script>

</head>

<body class="Login">

<form id="loginForm" action="" method="post">

    <div class="login_bg">

        <img src="s/login/images/login_bg.jpg"/>

        <div class="login_dw"></div>

    </div>

    <div class="login_fot">

        <img src="s/login/images/login_fotBg.jpg"/>

        <div class="login_BT">

            智慧太湖新城地下管网综合信息平台

        </div>

        <div class="login_div">

            <input type="text" name="userName" class="userName" class="text required"/>

            <input type="password" name="passWord" class="password" class="text required"/>

            <input type="button" class="LoginBtn" name="submit" onclick="submitForm()"/>

            <input type="button" class="resetBtn" name="register" onclick="regUser()"/>

        </div>

    </div>

</form>

</body>

</html>

<script type="text/javascript">

    $(".login_bg").css('height', $('.Login').height() - $('.login_fot').height() - 50 + 'px');

    window.onresize = function () {

        $(".login_bg").css('height', $('.Login').height() - $('.login_fot').height() - 50 + 'px');

    }
</script>