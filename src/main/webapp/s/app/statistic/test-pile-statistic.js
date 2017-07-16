/**
 * Created by lenovo on 2017/6/21.
 */
var TestPileStats = function(){
    var xAxisData = [];
    var yAxisData = [];
    var quarters = [{},{},{},{}];
    var testPileStatus = ["良好","缺失","部件缺失","损坏","占压","路中","不让进","新发现","删除","其他"];
    var ownerComps = ["高压公司","一分公司","二分公司","三分公司","四分公司","五分公司","密云公司","昌平公司","延庆公司","怀柔公司","平谷公司","建管","用户工程"];
    var maintains = ["刷漆","换螺栓","换接线","换铜鼻","换帽子","清理井","其他"];
    var maintainDatas = [];

    var potentialStatus = ["检测总量","无法检测","合格","不合格","过保护","合格率"];
    var seasons = ["一季度","二季度","三季度","四季度"];
    var acceptableRates =[];
    var potentialDatas = [];

    var chartTitle = "按权属单位统计标识器";
    var tableHeader = "<thead><tr><th >权属单位</th><th  >个数</th></tr></thead>";
    function initChart(){
        var myChart = echarts.init(document.getElementById('echart-div'));
        var chart_option = {
            title: {
                text: chartTitle,
                left: 'center',
                top: 0
            },
            color: ['#3398DB'],
            toolbox: {
                feature: {
                    saveAsImage: {
                        title : '导出图片',
                        pixelRatio: 2
                    }
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                top: 70,
                bottom: 50
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: true
                    },
                    data: xAxisData
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [

                {
                    name: '标识器数量',
                    type: 'bar',
                    data:yAxisData
                }
            ]
        };
        myChart.setOption(chart_option);
    }
    function initTable(){
        var location = (20-xAxisData.length)>0?20-xAxisData.length:0;
        $("#statistic-table").empty();
        $("#statistic-table").css({
            "margin-top":""+location+"em"
        });
        for(var i=0;i<xAxisData.length;i++){
            tableHeader = tableHeader+"<tr><td>"+xAxisData[i]+"</td><td>"+yAxisData[i]+"</td></tr>";
        }
        $("#statistic-table").append(tableHeader);
    }

    function initTestPilePieCharts(){
        var myChart = echarts.init(document.getElementById('echart-div'));
        var canvas = document.createElement('canvas');
        canvas.width = canvas.height = 100;
        var option = {
            backgroundColor: {
                type: 'pattern',
                image: canvas,
                repeat: 'repeat'
            },
            toolbox: {
                feature: {
                    saveAsImage: {
                        title : '导出图片',
                        name:"测试桩(井)状态统计",
                        pixelRatio: 2
                    }
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} ({d}%)"
            },
            title: [{
                text: '第一季度',
                subtext: '总计 ' +  Object.keys(quarters[0]).reduce(function (all, key) {
                    return all + quarters[0][key];
                },0),
                x: '25%',
                textAlign: 'center'
            },
                {
                    text: '第二季度',
                    subtext: '总计 ' +  Object.keys(quarters[1]).reduce(function (all, key) {
                        return all + quarters[1][key];
                    },0),
                    x: '75%',
                    textAlign: 'center'
                },
                {
                    text: '第三季度',
                    subtext: '总计 ' + Object.keys(quarters[2]).reduce(function (all, key) {
                        return all + quarters[2][key];
                    }, 0),
                    x: '25%',
                    y:"50%",
                    textAlign: 'center'
                }, {
                    text: '第四季度',
                    subtext: '总计 ' + Object.keys(quarters[3]).reduce(function (all, key) {
                        return all + quarters[3][key];
                    }, 0),
                    x: '75%',
                    y: '50%',
                    textAlign: 'center'
                }],
            series: [{
                type: 'pie',
                radius: [0, '30%'],
                center: ['25%', '25%'],
                data: Object.keys(quarters[0]).map(function (key) {
                    return {
                        name: key,
                        value: quarters[0][key]
                    };
                })
            }, {
                type: 'pie',
                radius: [0, '30%'],
                center: ['75%', '25%'],
                data: Object.keys(quarters[1]).map(function (key) {
                    return {
                        name: key,
                        value: quarters[1][key]
                    };
                })
            }, {
                type: 'pie',
                radius: [0, '30%'],
                center: ['25%', '75%'],
                data: Object.keys(quarters[2]).map(function (key) {
                    return {
                        name: key,
                        value: quarters[2][key]
                    };
                })
            }, {
                type: 'pie',
                radius: [0, '30%'],
                center: ['75%', '75%'],
                data: Object.keys(quarters[3]).map(function (key) {
                    return {
                        name: key,
                        value: quarters[3][key]
                    };
                })
            }]
        };
        myChart.setOption(option);
    }
    function initTestPileReportTable(){
        $("#statistic-table").empty();
        $("#statistic-table").css({
            "margin-top":"1em"
        });
        var sum = [0,0,0,0];
        for(var i=0;i<testPileStatus.length;i++){
            var tmp =[0,0,0,0];
            for(var j =0;j<4;j++){
                tmp[j] = quarters[j][testPileStatus[i]]?quarters[j][testPileStatus[i]]:0;
                if(tmp[j]) sum[j] +=tmp[j];
            }
            tableHeader = tableHeader+"<tr>" +
                "<td>"+testPileStatus[i]+"</td>" +
                "<td>"+tmp[0]+"</td>" +
                "<td>"+tmp[1]+"</td>" +
                "<td>"+tmp[2]+"</td>" +
                "<td>"+tmp[3]+"</td>" +
                "</tr>";
        }
        tableHeader =tableHeader+"<tr>" +
            "<td>小计</td>" +
            "<td>"+sum[0]+"</td>" +
            "<td>"+sum[1]+"</td>" +
            "<td>"+sum[2]+"</td>" +
            "<td>"+sum[3]+"</td>" +
            "</tr>";
        $("#statistic-table").append(tableHeader);
    }

    function initMaintainReportTable(){
        $("#statistic-table").empty();
        $("#statistic-table").css({
            "margin-top":"1em"
        });
        for(var i=0;i<ownerComps.length;i++){
            tableHeader = tableHeader+"<tr><td>"+ownerComps[i]+"</td>";
            for(var j =0;j<maintains.length;j++){
                var tmp =0;
                var flag =(maintainDatas[ownerComps[i]])&&maintainDatas[ownerComps[i]][maintains[j]];
                if(flag) tmp = maintainDatas[ownerComps[i]][maintains[j]];
                tableHeader +="<td>"+tmp+"</td>";
            }
            tableHeader = tableHeader+"</tr>"
        }
        $("#statistic-table").append(tableHeader);
    }

    function initPotentialChart() {
        var myChart = echarts.init(document.getElementById('echart-div'));
        var option = {title: {
            x: 'center',
            text: '保护电位合格率'
        },
            tooltip: {
                trigger: 'item',
                formatter: '{b}:\n{c}%'
            },
            toolbox: {
                show: true,
                feature: {
                    saveAsImage: {
                        title : '导出图片',
                        name:"保护电位合格率统计",
                        pixelRatio: 2
                    }
                }
            },
            calculable: true,
            grid: {
                borderWidth: 0,
                y: 80,
                y2: 60
            },
            xAxis: [
                {
                    type: 'category',
                    show: true,
                    data:seasons
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    max:"100",
                    min:"0",
                    axisLabel: {
                        show: true,
                        interval: 'auto',
                        formatter: '{value} %'
                    },
                    show: true
                }
            ],
            series: [
                {
                    name: '合格率',
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            color: function (params) {
                                var colorList = [
                                    '#C1232B', '#B5C334', '#FCCE10', '#E87C25'
                                ];
                                return colorList[params.dataIndex]
                            },
                            label: {
                                show: true,
                                position: 'top',
                                formatter: '{b}\n{c}%'
                            }
                        }
                    },
                    data: acceptableRates
                }
            ]
        }
        myChart.setOption(option);
    }
    function initPotentialReportTable(type){
        var location = type?1:8;
        $("#statistic-table").empty();
        $("#statistic-table").css({
            "margin-top":location+"em"
        });
        for(var i=0;i<4;i++){
            tableHeader = tableHeader+"<tr><td>"+seasons[i]+"</td>";
            var sum = 0;
            for(var count in potentialDatas[i]){sum+=potentialDatas[i][count];}
            tableHeader = tableHeader+"<td>"+sum+"</td>";
            for(var j =1;j<potentialStatus.length-1;j++){
                var tmp =0;
                var flag =(potentialDatas[i])&&potentialDatas[i][potentialStatus[j]];
                if(flag) tmp = potentialDatas[i][potentialStatus[j]];
                tableHeader +="<td>"+tmp+"</td>";
            }
            if(sum>0&&potentialDatas[i]["合格"]) acceptableRates[i] = (potentialDatas[i]["合格"]/sum*100).toFixed(2);//合格率
            else acceptableRates[i] = 0;
            if(!type)tableHeader +="<td>"+acceptableRates[i]+"%</td>";
            tableHeader +="</tr>";
        }
        $("#statistic-table").append(tableHeader);
    }

    function initPotential2BarChart(){
        var items = ['合格','不合格','过保护','无法检测'];
        var tmpDatas = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]];
        for(var i=0;i<potentialDatas.length;i++){
            for(var j=0;j<items.length;j++){
                 if(potentialDatas[i]&&potentialDatas[i][items[j]]) tmpDatas[j][i] = potentialDatas[i][items[j]];
            }
        }
        var myChart = echarts.init(document.getElementById('echart-bar-div'));
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    saveAsImage: {
                        title : '导出图片',
                        name:"保护电位状况(柱状图)",
                        show: true
                    }
                }
            },
            calculable : true,
            legend: {
                bottom:0,
                data:items
            },
            xAxis: [
                {
                    type: 'category',
                    data: seasons,
                    axisPointer: {
                        type: 'shadow'
                    }
                }
            ],

            yAxis: [
                {
                    type: 'value',
                    name: '数量'
                }
            ],
            series: [
                {
                    name:items[0],
                    type:'bar',
                    data:tmpDatas[0]
                },
                {
                    name:items[1],
                    type:'bar',
                    data:tmpDatas[1]
                },
                {
                    name:items[2],
                    type:'bar',
                    data:tmpDatas[2]
                },
                {
                    name:items[3],
                    type:'bar',
                    data:tmpDatas[3]
                }
            ]
        };
        myChart.setOption(option);
    }
    function initPotential2PieChart(){
        var datas = potentialDatas;
        for(var i = 0;i<4;i++) if(!datas[i]) datas[i]=[];
        var myPieChart = echarts.init(document.getElementById('echart-pie-div'));
        var canvas = document.createElement('canvas');
        canvas.width = canvas.height = 100;
        var option = {
            backgroundColor: {
                type: 'pattern',
                image: canvas,
                repeat: 'repeat'
            },
            toolbox: {
                feature: {
                    saveAsImage: {
                        title : '导出图片',
                        name:"保护电位状况",
                        pixelRatio: 2
                    }
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} ({d}%)"
            },
            title: [{
                text: '第一季度',
                subtext: '总计 ' +  Object.keys(datas[0]).reduce(function (all, key) {
                    return all + datas[0][key];
                },0),
                x: '25%',
                textAlign: 'center'
            },
                {
                    text: '第二季度',
                    subtext: '总计 ' +  Object.keys(datas[1]).reduce(function (all, key) {
                        return all + datas[1][key];
                    },0),
                    x: '75%',
                    textAlign: 'center'
                },
                {
                    text: '第三季度',
                    subtext: '总计 ' + Object.keys(datas[2]).reduce(function (all, key) {
                        return all + datas[2][key];
                    }, 0),
                    x: '25%',
                    y:"50%",
                    textAlign: 'center'
                }, {
                    text: '第四季度',
                    subtext: '总计 ' + Object.keys(datas[3]).reduce(function (all, key) {
                        return all + datas[3][key];
                    }, 0),
                    x: '75%',
                    y: '50%',
                    textAlign: 'center'
                }],
            series: [{
                type: 'pie',
                radius: [0, '30%'],
                center: ['25%', '25%'],
                data: Object.keys(datas[0]).map(function (key) {
                    return {
                        name: key,
                        value: datas[0][key]
                    };
                })
            }, {
                type: 'pie',
                radius: [0, '30%'],
                center: ['75%', '25%'],
                data: Object.keys(datas[1]).map(function (key) {
                    return {
                        name: key,
                        value: datas[1][key]
                    };
                })
            }, {
                type: 'pie',
                radius: [0, '30%'],
                center: ['25%', '75%'],
                data: Object.keys(datas[2]).map(function (key) {
                    return {
                        name: key,
                        value: datas[2][key]
                    };
                })
            }, {
                type: 'pie',
                radius: [0, '30%'],
                center: ['75%', '75%'],
                data: Object.keys(datas[3]).map(function (key) {
                    return {
                        name: key,
                        value: datas[3][key]
                    };
                })
            }]
        };
        myPieChart.setOption(option);
    }
    return {
        initForTestPileStatus: function (beginDate,endDate,ownerComp,area) {
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >状态</th><th  >个数</th></tr></thead>";
            chartTitle ="阴保桩(井)状态统计";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-testPileStatus.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "ownerComp":ownerComp,
                    "area":area
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        for (var i = 0; i < data.data.length; i++) {
                            xAxisData.push(data.data[i][0]);
                            yAxisData.push(data.data[i][1]);
                        }
                        initChart();
                        initTable();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        },
        initForTestPileReport:function(ownerComp,year){
            tableHeader = "<thead><tr><th >状态</th><th>一季度</th><th>二季度</th><th>三季度</th><th>四季度</th></tr></thead>";
            quarters = [{},{},{},{}];
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-testPileReport.do",
                data: {
                    "year": year,
                    "ownerComp":ownerComp
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        var values = data.data;
                        for (var i = 0; i < values.length; i++) {
                           quarters[values[i][0]][values[i][1]] = values[i][2];
                        }
                        initTestPilePieCharts();
                        initTestPileReportTable();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        },
        initForMaintain:function (beginDate,endDate,ownerComp,area) {
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >维护方式</th><th  >次数</th></tr></thead>";
            chartTitle ="阴保桩(井)维护处理统计";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-maintain.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "ownerComp":ownerComp,
                    "area":area
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        for (var i = 0; i < data.data.length; i++) {
                            xAxisData.push(data.data[i][0]);
                            yAxisData.push(data.data[i][1]);
                        }
                        initChart();
                        initTable();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        },
        initForMaintainReport:function(beginDate,endDate){
            maintainDatas = []
            tableHeader = "<thead><tr><th >权属单位</th>";
            for(var i=0;i<maintains.length;i++) tableHeader=tableHeader+"<th >"+maintains[i]+"</th>";
            tableHeader +="</tr></thead>";
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-maintainReport.do",
                data: {
                    "beginDate": beginDate,
                    "endDate":endDate
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        var values = data.data;
                        for (var i = 0; i < values.length; i++) {
                            if(!maintainDatas[values[i][0]]) maintainDatas[values[i][0]] = [];
                            maintainDatas[values[i][0]][values[i][1]] = values[i][2];
                        }
                        initMaintainReportTable();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        },
        initForPotential:function (beginDate,endDate,ownerComp,area) {
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >电位状态</th><th  >个数</th></tr></thead>";
            chartTitle ="阴保桩(井)电位统计";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-potentialStatus.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "ownerComp":ownerComp,
                    "area":area
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        for (var i = 0; i < data.data.length; i++) {
                            xAxisData.push(data.data[i][0]);
                            yAxisData.push(data.data[i][1]);
                        }
                        initChart();
                        initTable();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        },
        initForPotentialReport:function(ownerComp,year){
            potentialDatas = []
            tableHeader = "<thead><tr><th >季度</th>";
            for(var i = 0;i<potentialStatus.length;i++) tableHeader +="<th >"+potentialStatus[i]+"</th>";
            tableHeader +="</tr></thead>";
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-potentialReport.do",
                data: {
                    "year": year,
                    "ownerComp":ownerComp
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        var values = data.data;
                        for (var i = 0; i < values.length; i++) {
                            if(!potentialDatas[values[i][0]]) potentialDatas[values[i][0]] = [];
                            potentialDatas[values[i][0]][values[i][1]] = values[i][2];
                        }
                        initPotentialReportTable();
                        initPotentialChart();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        },

        initForPotentialReport2:function(ownerComp,year){
            potentialDatas = []
            tableHeader = "<thead><tr><th >季度</th>";
            for(var i = 0;i<potentialStatus.length-1;i++) tableHeader +="<th >"+potentialStatus[i]+"</th>";
            tableHeader +="</tr></thead>";
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-potentialReport.do",
                data: {
                    "year": year,
                    "ownerComp":ownerComp
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        var values = data.data;
                        for (var i = 0; i < values.length; i++) {
                            if(!potentialDatas[values[i][0]]) potentialDatas[values[i][0]] = [];
                            potentialDatas[values[i][0]][values[i][1]] = values[i][2];
                        }
                        initPotentialReportTable(true);
                        initPotential2BarChart();
                        initPotential2PieChart();
                    } else {
                        alert("请求失败");
                    }
                },
                error: function () {
                    alert("网络异常");
                }
            });
        }
    }

}();