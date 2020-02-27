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
    </style>
</head>
<body>
<%--<div class="top"></div>--%>
<div class="container" style="margin-top: 10px">
    <%--搜索框--%>
    <div class="row">
        <form action="return false;">
            <input class="inputInfo" id="username" type="text" name="username"   placeholder="请输入用户名">
            <input class="inputInfo" id="email" type="email" name="email"  placeholder="请输入邮箱">
            <button type="button" class="btn" style="height: 37px;margin-left: 10px;" onclick="refreshTable()">搜索</button>
        </form>
    </div>
    <table id="table" class="table table-bordered"></table>
</div>

<script>
    var tableData;
    $(function () {
        $("#table").bootstrapTable('destroy');
        tableData={
            url: '${ctx}/admin/findAllUsers',
            queryParams: function queryParams(params) {   //设置查询参数
                console.log(params);
                var param = {
                    offset: params.offset,
                    limit:params.limit,
                    username:$('#username').val(),
                    email:$('#email').val()
                };
                return param;
            },
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
                title: '番茄时长',
                align: 'center',
                formatter: function (value) {
                    return value+"分钟"
                }
            },{
                field: 'shortBreak',
                title: '短时休息时长',
                align: 'center',
                formatter:function (value) {
                    return value+"分钟"
                }
            },{
                field: 'longBreak',
                title: '长时休息时长',
                align: 'center',
                formatter:function (value) {
                    return value+"分钟"
                }
            },{
                field: 'longRestInterval',
                title: '长时休息间隔',
                align: 'center',
                formatter:function (value) {
                    return value+"个番茄"
                }
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
        }
        $('#table').bootstrapTable(tableData);
    });

    function actionFormatter(value, row, index) {
        var result = "";
        result += "<button type='button' class='btn' onclick='toUserDetail("+row.id+")'>查看图表</button>";
        return result;
    }

    function refreshTable() {
        $('#table').bootstrapTable('destroy');
        $("#table").bootstrapTable(tableData);
    }

    function toUserDetail(userId) {
        console.log(userId);
        location.href="${ctx}/admin/toUserChart?userId="+userId;
    }
</script>
</body>
</html>
