/**
 * Created by lenovo on 2017/6/21.
 */
var MarkerStatistic = function(){
    var xAxisData = [];
    var yAxisData = [];
    var title = "按权属单位统计标识器";
    var tableHeader = "<thead><tr><th >权属单位</th><th  >个数</th></tr></thead>";
    var myChart = echarts.init(document.getElementById('echart-div'));
    function initChart(){
        var chart_option = {
            title: {
                text: title,
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
        var location = (18-xAxisData.length)>0?18-xAxisData.length:0;
        $("#statistic-table").empty();
        $("#statistic-table").css({
            "margin-top":""+location+"em"
        });
        for(var i=0;i<xAxisData.length;i++){
            tableHeader = tableHeader+"<tr><td>"+xAxisData[i]+"</td><td>"+yAxisData[i]+"</td></tr>";
        }
        $("#statistic-table").append(tableHeader);
    }
    return {
        initForOwnerComp: function (beginDate,endDate,recordType) {
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >权属单位</th><th  >个数</th></tr></thead>";
            if(recordType==2) title ="按权属单位统计标识器-阴保桩";
            else title="按权属单位统计标识器";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-ownercomp.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "recordType":recordType
                },
                dataType: "json",
                async:false,
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
        initForCreateTime:function(beginDate,endDate,ownerComp){
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >年份</th><th  >个数</th></tr></thead>";
            if(ownerComp) title ="按建造年份统计标识器-"+ownerComp;
            else title="按建造年份统计标识器-全部";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-createTime.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "ownerComp":ownerComp
                },
                dataType: "json",
                async:false,
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
        initForRecordType:function(beginDate,endDate,ownerComp){
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >对象类型</th><th  >个数</th></tr></thead>";
            if(ownerComp) title ="按对象类型统计标识器-"+ownerComp;
            else title="按对象类型统计标识器-全部";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-recordType.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "ownerComp":ownerComp
                },
                dataType: "json",
                async:false,
                success: function (data) {
                    if (data.success) {
                        for (var i = 0; i < data.data.length; i++) {
                            //数字转化为具体的对象类型
                            if(data.data[i][0]==1)xAxisData.push("管线");
                            else if(data.data[i][0]==2)xAxisData.push("附属物");
                            else if(data.data[i][0]==3)xAxisData.push("特征管点");
                            else if(data.data[i][0]==4)xAxisData.push("抢修点");
                            else if(data.data[i][0]==5)xAxisData.push("接切线点");
                            else xAxisData.push("未知");
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
        initForPressLevel:function(beginDate,endDate,ownerComp){
            xAxisData = [];
            yAxisData = [];
            tableHeader = "<thead><tr><th >压力级制</th><th  >个数</th></tr></thead>";
            if(ownerComp) title ="按压力级制统计标识器-"+ownerComp;
            else title="按压力级制统计标识器-全部";
            if(!beginDate) beginDate = '';
            if(!endDate) endDate ='';
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/marker/stats-by-pressLevel.do",
                data: {
                    "beginDate": beginDate,
                    "endDate": endDate,
                    "ownerComp":ownerComp
                },
                dataType: "json",
                async:false,
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
        }
    }

}();