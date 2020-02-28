<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>清单管理</title>
    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/static/css/bootstrap3.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap3.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.base64.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/jquery.cookie.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/css/bootstrap-table.min.css" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap-table.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
    <style>
        .top{
            width: 100%;
            height: 60px;
            background-color: #B22222;
            position: fixed;
            top:0;
        }
        .title{
            color:white;
            height: 60px;
            width: 150px;
            font-size: 20px;
            line-height: 60px;
            margin-left: 1px;
            text-align: center;
        }
        .active{
            background-color: rgb(205,92,92);
        }

        /**表格样式修改**/
        #table thead{
            background-color: rgb(205,92,92);
            color: white;
        }
        #table tr:hover{
            background-color: rgba(255,228,225,0.3);
        }

        /**按钮样式修改**/
        .btn{
            background-color: white;
            border: 1px solid #CD5C5C;
            color: #CD5C5C;
        }
        .btn:hover{
            background-color: #CD5C5C;
            color: white;
        }

        /**搜索框样式修改**/
        .inputInfo{
            width: 230px;
            height: 38px;
            background-color: white;
            opacity: 1;
            padding-left: 10px;
            outline-color:red;
            border: 1px solid #B3B2B3;
            margin-left:14px;
        }

        /**表单类型**/
        .form-control:focus {
            border-color: #CD5C5C;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(255,255,255,.075),0 0 8px rgba(255,255,255,.6);
            box-shadow: inset 0 1px 1px rgba(255,255,255,.075),0 0 8px rgba(255,255,255,.6);
        }
    </style>
</head>
<body>
<div class="top">
    <div class="row" style="width: 100%;">
        <div class="title col-md-1" style="margin-left: 144px;" onclick="toStudent()">用户管理</div>
        <div class="title active col-md-1">清单管理</div>
        <div class="title col-md-1">任务管理</div>
    </div>
    <span style="color: white;font-size: 25px;line-height: 60px;float: right;margin-right: 50px;margin-top:-55px;">Admin</span>
    <img src="${ctx}/static/images/headImg.png" style="height: 50px;float: right;margin-top: 5px;margin-top:-55px;"/>
</div>

<div class="container" style="margin-top: 80px">
    <form style="width: 800px;margin:0px auto;" id="myForm" action="return false;">
        <div class="form-group">
            <label for="title" style="font-size: 17px;">清单名称</label>
            <input type="text" class="form-control" id="title" placeholder="请输入清单名称">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1" style="font-size: 17px;">清单颜色</label>
            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
        </div>
        <div class="form-group">
            <label for="selectUser" style="font-size: 17px;">选择用户</label>
            <input type="password" class="form-control" id="selectUser" placeholder="Password">
        </div>
        <button type="button" class="btn btn-default">Submit</button>
    </form>
</div>

<script>


    function toStudent() {
        location.href="${ctx}/admin/index";
    }

</script>
</body>
</html>
