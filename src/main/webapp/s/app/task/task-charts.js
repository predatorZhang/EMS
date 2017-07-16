/**
 * Created by lenovo on 2016/5/30.
 */

var TaskCharts = function () {

    var oTable;
    var statusValue;
    var startDate;
    var endDate;
    return {
        //绑定监听按钮点击事件
        init: function () {
            $('#changeCharts').live('click',function(){
                 startDate = $("#startDate").val();
                 endDate =$("#endDate").val();
                TaskCharts.initPieCharts();
                oTable.fnDraw();
            });
        },

        initPieCharts: function () {
            //请求后台数据
            var data = [];
            var series;
            $.ajax({
                type: "POST",
                url: $('#context').val() + "/task/getTaskCount.do",
                dataType: "json",
                data:{"startDate":startDate,"endDate":endDate},
                async: false,
                success: function (res) {
                    var jData = eval(res);
                    if (jData.success == true) {
                        var dataRes = res.data;
                        series = dataRes.length;
                        for (var i = 0; i < series; i++) {
                            var state;
                            var realStatus = dataRes[i].status;
                            if (realStatus == 0) {
                                state = "未巡检"
                            } else if (realStatus == 1) {
                                state = "正在巡检"
                            } else if (realStatus == 2) {
                                state = "巡检完成"
                            }
                            data[i] = {
                                label: state,
                                data: dataRes[i].statusCount
                            }
                        }

                    }
                },
                error: function (request) {

                    alert("获取任务状态失败");

                }
            });

            // INTERACTIVE
            $.plot($("#interactive"), data, {
                series: {
                    pie: {
                        show: true
                    }
                },
                grid: {
                    hoverable: true,
                    clickable: true
                }
            });
            $("#interactive").bind("plothover", pieHover);
            $("#interactive").bind("plotclick", pieClick);

            function pieHover(event, pos, obj) {
                if (!obj)
                    return;
                percent = parseFloat(obj.series.percent).toFixed(2);
                //这里使用datatable请求后台列表信息
//                alert(pos.pageX+":"+pos.pageY);
                $("#hover").html('<span style="font-weight: bold; color: ' + obj.series.color + '">' + obj.series.label + ' (' + percent + '%)</span>');
            }

            function pieClick(event, pos, obj) {
                if (!obj)
                    return;
                percent = parseFloat(obj.series.percent).toFixed(2);
                //请求后台信息
                var temp = obj.series.label;
                var status = temp == "未巡检" ? 0 : (temp =="正在巡检" ? 1 : 2);//判断用户点击的是哪个
                statusValue = status;
                oTable.fnDraw();

            }

        },
        initTable: function () {
            function retrieveData(sSource, aoData, fnCallback) {
                //查询条件称加入参数数组
                $.ajax({
                    type: "POST",
                    url: sSource,
                    dataType: "json",
                    //TODO LIST：按条件查询服务器数据
                    data: {jsonParam: JSON.stringify(aoData),status:statusValue,startDate:startDate,endDate:endDate},
                    success: function (data) {
                        fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式
                    }
                });
            };
            oTable = $('#table_task').dataTable({
                // set the initial value
                "iDisplayLength": 5,
                "bServerSide": true,
                "bLengthChange": false,
                "bFilter": false,
                "bPaginate": true,
                "sPaginationType": "bootstrap",
                "sAjaxSource": $('#context').val() + "/task/task-info-list.do", //TODO LIST:修改成对应的后台Controller地址
                "fnServerData": retrieveData,
                "oLanguage": {
                    "sSearch": "用户名:",
                    "sLengthMenu": "每页显示 _MENU_ 条记录",
                    "sZeroRecords": "抱歉， 没有找到",
                    "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                    "sInfoEmpty": "没有数据",
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页",
                        "sLast": "末页",
                        "sFirst": "首页"
                    }
                },
                "aoColumns": [
                    {
                        "mDataProp": "id"
                    },
                    {
                        "mDataProp": "taskCode"
                    },
                    {
                        "mDataProp": "deployDate"
                    },
//                    {
//                        "mDataProp": "beginDate"
//                    },
                    {
                        "mDataProp": "status"
                    },
//                    {
//                        "mDataProp": "endDate"
//                    },
                    {
                        "mDataProp": "creatorName"
                    },
//                    {
//                        "mDataProp": "patrolerId"
//                    },
//                    {
//                        "mDataProp": "patrolerName"
//                    }
                ],
                "aoColumnDefs": [
                    {
                        'bVisible': false,
                        'aTargets': [0]
                    },
                    {
                        'bSortable': false,
                        'aTargets': [1, 2, 3, 4]
                    }
                ]
            });

        }
//        initOpt:function(){
//
//        }

    };

}();