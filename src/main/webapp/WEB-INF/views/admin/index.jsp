<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>主页</title>
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
    <table id="table" class="table table-striped"></table>
</div>

<script>
    $(function () {
        $('#table').bootstrapTable({
            url: '${ctx}/user/findAll',
            method:'get',
            sidePagination:"server",
            pagination:true,
            pageNum:1,
            pageSize:10,
            pageList:[10],
            columns: [{
                field: 'username',
                title: '昵称',
                align: 'center'
            }, {
                field: 'email',
                title: '邮箱',
                align: 'center'
            },{
                field: 'tomatoTime',
                title: '番茄时长（分钟）',
                align: 'center'
            },{
                field: 'shortBreak',
                title: '短时休息时长（分钟）',
                align: 'center'
            },{
                field: 'longBreak',
                title: '长时休息时长（分钟）',
                align: 'center'
            },{
                field: 'longRestInterval',
                title: '长时休息间隔（番茄数）',
                align: 'center'
            },{
                field:'createTime',
                title: '注册时间',
                align: 'center'
            },{
                field:'signature',
                title: '个性签名',
                align: 'center'
            },{
                field: 'operate',
                title: '操作',
                align: 'center',
                formatter: actionFormatter,
            }]
        });
    });

    function actionFormatter(value, row, index) {
        var result = "";
        result += "<button type='button' class='btn myBtn' onclick='toUserDetail("+row.id+")'>查看图表</button>";
        return result;
    }

    function toUserDetail(userId) {
        console.log(userId);
        location.href="${ctx}/user/toUserDetail?userId="+userId;
    }
</script>
</body>
</html>
