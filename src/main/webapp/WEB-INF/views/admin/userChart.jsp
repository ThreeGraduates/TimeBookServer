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
    <script>
        $(function() {
            var chart;
            chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'container',        //在哪个区域呈现，对应HTML中的一个元素ID
                    plotBackgroundColor: null,  //绘图区的背景颜色
                    plotBorderWidth: null,      //绘图区边框宽度
                    plotShadow: false           //绘图区是否显示阴影
                },
                title: {
                    text: '${user.username}番茄任务完成情况'   //图表的主标题
                },
                tooltip: {     //当鼠标经过时的提示设置
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                //每种图表类型属性设置
                plotOptions: {   //饼状图
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{         //图表要展现的数据
                    type: 'pie',   //描述了数据列类型。默认值为 "line"
                    name: '番茄任务完成情况'
                }]
            });

            //异步请求数据
            $.ajax({
                type:"post",
                url:'${ctx}/user/getPieChart',//提供数据的Servlet
                data:{
                    'userId':'${user.id}'
                },
                success:function(data){
                    console.log(data);
                    browsers = [],
                    //迭代，把异步获取的数据放到数组中
                    $.each(data,function(i,d){
                        browsers.push([d.name,d.share]);
                    });
                    //设置数据
                    chart.series[0].setData(browsers);
                },
                error:function(e){
                    alert(e);
                }
            });
        });
    </script>
</head>
<body>
<span>
    统计
    <!-- Single button -->
<div class="btn-group">
  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Action <span class="caret"></span>
  </button>
  <ul class="dropdown-menu">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div>
</span>
<div id="container" style="min-width:800px;height:400px;">

</div>
</body>
</html>
