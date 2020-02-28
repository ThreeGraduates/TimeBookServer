<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>图表统计</title>
    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/static/css/bootstrap3.min.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap3.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/highcharts.js" type="text/javascript"></script>
    <style>
        .top{
            width: 100%;
            height: 60px;
            background-color: #B22222;
            position: fixed;
            top:0;
        }
        ol li a,ol li a:hover{
            color:white;
        }

        <%--标题--%>
        .title{
            font-size: 20px;
            margin-left: 285px;
            margin-top: 30px;
        }

        /**按钮样式修改**/
        .btn{
            margin-top: -3px;
            height: 30px!important;
            background-color: white;
            border: 1px solid #CD5C5C;
            color: #CD5C5C;
        }
        .btn:hover{
            background-color: #CD5C5C;
            color: white;
        }

        /*下拉列表样式修改*/
        .btn-group ul li a:hover{
            background-color: #CD5C5C!important;
            color: white;
        }

        .highcharts-credits{
            display: none;
        }

    </style>

</head>
<body>
<div class="top">
    <ol class="breadcrumb" style="background-color: #B22222;font-size:20px;line-height:40px;padding-left:50px;margin-left: 20px;">
        <li><a href="${ctx}/admin/index">首页</a></li>
<%--        <li><a href="#">用户信息</a></li>--%>
        <li class="active" style="color:#D3D3D3">用户图表</li>
    </ol>
    <span style="color: white;font-size: 25px;line-height: 60px;float: right;margin-top:-74px;margin-right: 40px;">Admin</span>
    <img src="${ctx}/static/images/headImg.png" style="height: 50px;float: right;margin-top:-70px;margin-right: 110px;"/>
</div>
<div class="container" style="margin-top: 80px">
    <%--图标标题--%>
    <div class="title">
        查询 “${user.username}” 用户
        <div class="btn-group">
            <button id="btnYear" type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                2020 <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" style="min-width: 33px;!important;">
                <li><a href="#" onclick="getYear('2020')">2020</a></li>
                <li><a href="#" onclick="getYear('2019')">2019</a></li>
                <li><a href="#" onclick="getYear('2018')">2018</a></li>
                <li><a href="#" onclick="getYear('2017')">2017</a></li>
            </ul>
        </div> 年
        <div class="btn-group">
            <button id="btnMonth" type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                01 <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" style="min-width: 33px;!important;">
                <li><a href="#" onclick="getMonth('01')">01</a></li>
                <li><a href="#" onclick="getMonth('02')">02</a></li>
                <li><a href="#" onclick="getMonth('03')">03</a></li>
                <li><a href="#" onclick="getMonth('04')">04</a></li>
                <li><a href="#" onclick="getMonth('05')">05</a></li>
                <li><a href="#" onclick="getMonth('06')">06</a></li>
                <li><a href="#" onclick="getMonth('07')">07</a></li>
                <li><a href="#" onclick="getMonth('08')">08</a></li>
                <li><a href="#" onclick="getMonth('09')">09</a></li>
                <li><a href="#" onclick="getMonth('10')">10</a></li>
                <li><a href="#" onclick="getMonth('11')">11</a></li>
                <li><a href="#" onclick="getMonth('12')">12</a></li>
            </ul>
        </div> 月的番茄任务完成情况

        <button type="button" class="btn" style="height: 37px;margin-left: 10px;" onclick="setChart()">查询</button>
    </div>

    <div id="myChart" style="min-width:800px;height:400px;margin-top: 40px;"/>

</div>
<script>
    var year;
    var month;
    var chart;
    $(function() {
        var date=new Date();
        year=date.getFullYear();
        getYear(year);
        month=date.getMonth()+1+"";
        if(month.length==1){month="0"+month;}
        getMonth(month);

        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'myChart',        //在哪个区域呈现，对应HTML中的一个元素ID
                plotBackgroundColor: null,  //绘图区的背景颜色
                plotBorderWidth: null,      //绘图区边框宽度
                plotShadow: false,         //绘图区是否显示阴影
            },
            title: {
                floating:true,
                text: year+"年"+month+"月"
            },
            tooltip: {     //当鼠标经过时的提示设置
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            colors:['#56BCA6','#EA615A','#F8D651'],
            //每种图表类型属性设置
            plotOptions: {   //饼状图
                pie: {
                    showInLegend: true,
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    point: {
                        events: {
                            mouseOver: function(e) {  // 鼠标滑过时动态更新标题
                                chart.setTitle({
                                    text: e.target.name+ '\t'+ e.target.y.toFixed(1) + ' %'
                                });
                            }
                        }
                    },
                }
            },
            series: [{         //图表要展现的数据
                type: 'pie',   //描述了数据列类型。默认值为 "line"
                innerSize: '60%',
                name: '番茄任务完成情况'
            }]
        }, function(c) {
            // 环形图圆心
            var centerY = c.series[0].center[1],
                titleHeight = parseInt(c.title.styles.fontSize);
            c.setTitle({
                y:centerY-30
            });
            chart = c;
        });
        setChart();
    });

    function getYear(y) {
        $('#btnYear').html(y+" <span class=\"caret\"></span>");
        year=y;
    }
    function getMonth(m) {
        $("#btnMonth").html(m+" <span class=\"caret\"></span>");
        month=m;
    }
    
    function setChart() {
        //异步请求数据
        $.ajax({
            type:"post",
            url:'${ctx}/task/getTaskStatusPieChart',
            data:{
                'userId':'${user.id}',
                'year':year+"",
                'month':month+""
            },
            success:function(data){
                if("empty"!=data){
                    var obj = JSON.parse(data);
                    var s1=parseFloat(obj.finish);
                    var s2=parseFloat(obj.unFinish);
                    var s3=(1-s1-s2).toFixed(3);
                    var dataList = [];
                    dataList.push(["已完成",s1*100]);
                    dataList.push(["未完成",s2*100]);
                    dataList.push(["中途放弃",s3*100]);
                    chart.series[0].setData(dataList);
                }else{
                    alert("数据不存在");
                    var date=new Date();
                    year=date.getFullYear();
                    getYear(year);
                    month=date.getMonth()+1+"";
                    if(month.length==1){month="0"+month;}
                    getMonth(month);
                }
            },
            error:function(e){
                alert(e);
            }
        });
    }
</script>
</body>
</html>
