<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>主页</title>
    <link type="image/x-icon" href="${ctx}/static/image/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.base64.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.cookie.min.js" type="text/javascript"></script>
    <style>
        .top{
            width: 100%;
            height: 50px;
            background-color: #B22222;
            position: fixed;
            top:0;
        }
    </style>
</head>
<body>
<div class="top"></div>
<div class="container-fluid" style="margin-top: 50px">
    <div class="row">
        <div class="col-md-2" style="background-color: gray">
            <ul id="main-nav" class="nav nav-tabs nav-stacked">
                <li><a href="#">首页</a></li>
                <li><a href="#">第一</a></li>
                <li><a href="#">第二</a></li>

            </ul>
        </div>
        <div class="col-md-10">
            主窗口
        </div>

    </div>
</div>

</body>
</html>
