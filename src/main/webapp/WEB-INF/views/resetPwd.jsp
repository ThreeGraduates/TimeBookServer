<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>重置密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <link type="image/x-icon" href="${ctx}/static/image/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/messages_bs_zh.js" type="text/javascript"></script>
    <style>
        #myForm{
            width: 360px;
            margin: 0 auto;
            border: 1px solid #CED4DA;
            padding: 20px 40px;
            border-radius: 2%;
        }
        .btn{
            width: 100%;
            background-color: rgb(222,96,75);
            color: white;
        }
        .btn:hover{
            color: white;
        }
    </style>
</head>
<body>
<div class="container" align="center" style="margin-top: 30px;">
    <img src="${ctx}/static/image/appLogo.png" width="60" height="60">
    <form id="myForm" style="margin-top: 20px;" onsubmit="return false;">
        <div class="form-group row">
            <label for="newPassword">新密码：</label>
            <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="6~18位字符">
        </div>
        <div class="form-group row">
            <label for="newPassword2">确认密码：</label>
            <input type="password" class="form-control" name="newPassword2" id="newPassword2">
        </div>
        <div class="form-group row">
            <button class="btn" onclick="resetPwd()">重置密码</button>
        </div>
    </form>
</div>
<script>
    jQuery.validator.addMethod("passwordLength", function(value, element) {
        var length = value.length;
        if(length>=6&&length<=18){
            return true;
        }
        return false;
    }, "密码为6~18位");
    $(function () {
        $("#myForm").validate({
            rules: {
                newPassword: {
                    passwordLength: true,
                },
                newPassword2: {
                    required: true,
                    equalTo: "#newPassword"
                }
            },
            messages: {
                newPassword: {
                    passwordLength: "密码必须为6~18位",
                },
                newPassword2: {
                    required: "确认密码是必填的",
                    equalTo:"确认密码与密码不同"
                }
            }
        });
    });
    function resetPwd() {
        if($("#myForm").valid()){
            $.ajax({
                url: "${ctx}/user/resetPassword",
                type: 'post',
                data: {
                    "email":"${email}",
                    "password":$("#newPassword").val()
                },
                success: function (result) {
                    alert(result);
                },
                error: function () {
                    alert("网络请求失败，请重试！");
                }
            })
        }
    }
</script>
</body>
</html>
