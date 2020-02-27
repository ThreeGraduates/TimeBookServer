<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE>
<html>
<head>
    <title>管理员登录</title>
    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/css/login.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.base64.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.cookie.min.js" type="text/javascript"></script>
    <style>
        .mybtn{
            width: 320px;
            height:44px;
            background:rgb(221,94,73);
            color:white;
            border:0px;
            font-size:20px;
            letter-spacing:8px;
            text-indent : 8px;
            border-radius:5px
        }
        .mybtn:hover{
            color:white;
        }
    </style>
</head>
<body>
<%--<div class="top row">--%>
<%--    <img src="${ctx}/static/image/appLogo.png" style="height: 70px;"/>--%>
<%--    <div id="c1"></div>--%>
<%--    <img src="${ctx}/static/images/login/title.PNG" style="height: 80px;margin-top: 10px;"/>--%>
<%--</div>--%>
<div class="container">
    <img src="${ctx}/static/images/bg.jpg" style="float: left;width: 620px;margin-top: 20px;margin-top:120px;"/>
    <div class="loginDiv" style="float: left;margin-top:100px;">
        <p style="width: 100%;text-align: center;font-size: 25px;margin-top:15px;color:rgb(221,94,73)">管理员登录</p>
        <form id="loginForm" action="return false;" style="margin-top: 18px;">
            <span id="info"></span>
            <div class="form-group" style="text-align: center">
                <input class="inputInfo" id="username" type="text" name="username" style="background-image:url(/static/images/user.png);background-repeat: no-repeat;" autocomplete="off" placeholder="请输入用户名" required>
            </div>
            <div class="form-group" style="text-align: center">
                <input class="inputInfo" id="password" type="password" name="pwd" style="background-image:url(/static/images/pwd.png);background-repeat: no-repeat;" placeholder="请输入密码" required>
            </div>
            <div class="form-group form-check" style="margin-left: 60px;line-height:20px;">
                <input type="checkbox" class="form-check-input" id="rememberPwd">
                <label class="form-check-label" for="rememberPwd">记住密码</label>
            </div>
            <div class="form-group" style="text-align: center">
                <button type="button" class="mybtn" style="width: 320px;" onclick="submitForm()">登录</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        if($.cookie("username")){
            $("#username").val($.cookie("username"));
            $("#password").val($.base64.decode($.cookie("password")));
            $("#rememberPwd").attr('checked', 'checked');
        }
    });
    //提交登录表单
    function submitForm() {
        if($("#loginForm").valid()){
            rememberPassword();
            $.ajax({
                url: "${ctx}/admin/loginAuth",
                type: 'post',
                data: $('#loginForm').serialize(),
                success: function (result) {
                    if(result=="登录成功"){
                        location.href="${ctx}/admin/index"
                    }else{
                        $("#info").show();
                        $("#info").text(result);
                    }
                },
                error: function () {
                    alert("网络请求失败，请重试！");
                }
            })
        }
    }

    function rememberPassword(){
        var username=$("#username").val();
        var pwd=$("#password").val();   
        var checked = $("#rememberPwd").is(':checked');
        if(checked){
            $.cookie("username",username,{ expires: 7, path: '/' });  //账号密码保存7天
            $.cookie("password",$.base64.encode(pwd),{ expires: 7, path: '/' });  
        }else{
            $.cookie("username",'',{ expires: -1 });
            $.cookie("password",'',{ expires: -1 });
        }
    } 
</script>
</body>
</html>