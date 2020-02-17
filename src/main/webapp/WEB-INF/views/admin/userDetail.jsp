<%--
  Created by IntelliJ IDEA.
  User: LIU
  Date: 2020/2/17
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图标统计</title>
</head>
<body>

<script>
    (function($) { // encapsulate jQuery
        $(document).ready(function() {
            var xx = [];
            var yy = [];
            for (var i = 1; i <$('#table tr').length; i++) {
                xx.push(parseFloat($('#table tr').eq(i).children("td").eq(0).text().trim()));
                yy.push(parseFloat($('#table tr').eq(i).children("td").eq(2).text()));
            }
            var chart;
            chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'container',
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '数据饼状图表'
                },
                tooltip: {
                    formatter: function() {
                        return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            color: '#000000',
                            connectorColor: '#000000',
                            formatter: function() {
                                return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
                            }
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'pie',
                    data:   yy
                }]
            });

        });
    })(jQuery);
</script>
</body>
</html>
