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
    <script src="${ctx}/static/js/jquery.cookie.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.base64.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jQuery.md5.js" type="text/javascript"></script>
</head>
<body>
<div class="top row">
    <img src="${ctx}/static/images/login/logo.jpg" style="height: 100px;"/>
    <div id="c1"></div>
    <img src="${ctx}/static/images/login/title.PNG" style="height: 80px;margin-top: 10px;"/>
</div>
<div class="main">
    <div class="loginDiv">
        <ul class="nav nav-tabs nav-fill" id="pills-tab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="pills-contact-tab" data-toggle="pill" href="#pills-contact" role="tab" aria-controls="pills-contact" aria-selected="false">管理员登录</a>
            </li>
        </ul>

        <form id="loginForm" action="return false;" style="margin-top: 30px;">
            <span id="info"></span>
            <input type="hidden" name="role" id="userRole" value="2">
            <div class="form-group" style="text-align: center">
                <input class="inputInfo" id="username" type="text" name="username" style="background-image:url(/static/images/login/user.png);background-repeat: no-repeat;" autocomplete="off" placeholder="请输入用户名" required>
            </div>
            <div class="form-group" style="text-align: center">
                <input class="inputInfo" id="password" type="password" name="pwd" style="background-image:url(/static/images/login/pwd.png);background-repeat: no-repeat;" placeholder="请输入密码" required>
            </div>
            <div class="form-group form-check" style="margin-left: 60px;line-height:20px;">
                <input type="checkbox" class="form-check-input" id="rememberPwd">
                <label class="form-check-label" for="rememberPwd">记住密码</label>
            </div>
            <div class="form-group" style="text-align: center">
                <img src="${ctx}/static/images/login/loginbtn.png" onclick="submitForm()"/>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    //提交登录表单
    function submitForm() {
        var password=$("#password").val();
        $("#password").val($.md5(password));
        if($("#loginForm").valid()){
            rememberPassword();
            $.ajax({
                url: "${ctx}/login/valid",
                type: 'post',
                data: $('#loginForm').serialize(),
                success: function (result) {
                    if(result=="ok"){
                        location.href="${ctx}/admin/index";
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
            $.cookie("password",$.md5(pwd),{ expires: 7, path: '/' });  
        }else{
            $.cookie("username", null);
            $.cookie("password",null);
        }
    } 
</script>
</body>
</html>