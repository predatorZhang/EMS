var MarkerInfoList = function () {

    var oTable;
    var Marker = {};
    var typeValue = 1;
    var aoColums;
    var aoColumnDefs;
    var handleDatePickers = function () {

        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL()
            });
        }
    }

    return {
        delMarker:function(id){
        if (confirm("确认要删除该操作 ?") == false) {
            return;
        }
        $.ajax({
            type: "POST",
            url: $('#context').val() + "/marker/delete-marker.do",
            dataType: "json",
            data: {
                id: id
            },
            success: function (data) {

                var jData = eval(data);
                if (jData.success == true) {
                    oTable.fnDraw();
                }
                alert(jData.message);
            },
            error: function (request) {
                alert("删除失败");
            }
        })
    },
        editMarker:function(id){
            location.href = $("#context").val() + "/marker/edit-marker.do?id=" + id;
        },
        potentialDetail:function(testPile){
            if(!testPile) {
                alert("阴保桩名称为空");
                return;
            }
            testPile=encodeURI(testPile);
            location.href = $("#context").val() + "/content/pipe/test-pile-list.jsp?testPile=" +testPile;
        },
    // 初始化表头以及列表中需要展示的列和列宽设置
        tableTitle: function (type) {
            aoColumnDefs = [
                {
                    "aTargets": [-2],//编辑
                    "mRender": function(data,type,full){return '<a href="#" onclick="MarkerInfoList.editMarker('+data+')">编辑</a>';}
                },
                {
                    "aTargets": [-1],//删除
                    "mRender": function(data,type,full){return "<a href='#' onclick='MarkerInfoList.delMarker("+data+")'>删除</a>";}
                },
                {
                    aTargets: ["_all"],
                    'bSortable': false
                },
                {
                    'bVisible': false,
                    'aTargets': [0]
                }
            ];
            if(type==2){
                aoColumnDefs.push({
                    "aTargets": [-3],//编辑
                    "mRender": function(data,type,full){
                        return "<a href='#'>电位信息</a>";
                    }
                })
            }
            if (type == 1) {
                aoColums = [
                    {"mDataProp": "markerDto.id" },
                    {"mDataProp": "markerDto.markerId" },
                    {"mDataProp": "markerDto.ownerComp" },
                    {"mDataProp": "markerDto.area" },
                    {"mDataProp": "markerDto.antiCorrodedNo"},
                    {"mDataProp": "markerDto.projectName"},
                    {"mDataProp": "markerDto.projectNo"},
                    {"mDataProp": "markerDto.line" },
                    {"mDataProp": "markerDto.pressLevel"},
                    {"mDataProp": "markerDto.pipeDiameter" },
                    {"mDataProp": "markerDto.pipeMaterial"},
                    {"mDataProp": "markerDto.depth"},
                    {"mDataProp": "markerDto.antiCorrodedType"},
                    {"mDataProp": "markerDto.designThickness" },
                    {"mDataProp": "markerDto.road"},
                    {"mDataProp": "markerDto.constructTime"},
                    {"mDataProp": "markerDto.x_cord"},
                    {"mDataProp": "markerDto.y_cord"},
                    {"mDataProp": "markerDto.memo"},
                    {"sTitle":"编辑","mDataProp":"markerDto.id","sClass":"center"},
                    {"sTitle":"删除","mDataProp":"markerDto.id","sClass":"center"}
                ];
                return  "<thead> <tr>"
                    + "<th hidden='hidden'>id</th>"
                    + "<th>标识器ID</th>"
                    + "   <th>权属单位</th>"
                    + "   <th>所属区域</th>"
                    + "   <th>防腐工号</th>"
                    + "   <th>工程名称</th>"
                    + "   <th>工程编号（图纸）</th>"
                    + "   <th>管网编号</th>"
                    + "   <th>压力级制</th>"
                    + "   <th>管径</th>"
                    + "   <th>材质</th>"
                    + "   <th>设计埋深</th>"
                    + "   <th>防腐层种类</th>"
                    + "   <th>设计壁厚</th>"
                    + "   <th>所属道路</th>"
                    + "   <th>建设时间</th>"
                    + "   <th>X坐标</th>"
                    + "  <th>Y坐标</th>"
                    + "  <th>备注</th>"
                    + "  <th>编辑</th>"
                    + "  <th>删除</th>"
                    + "</tr>"
                    + "</thead> ";
            } else if (type == 2) {
                aoColums = [
                    {"mDataProp": "markerDto.id"},
                    {"mDataProp": "markerDto.markerId"},
                    {"mDataProp": "markerDto.markerObjectType"},
                    {"mDataProp": "markerDto.ownerComp"},
                    {"mDataProp": "markerDto.area"},
                    {"mDataProp": "markerDto.line"},
                    {"mDataProp": "markerDto.road"},
                    {"mDataProp": "markerDto.testPile"},
                    {"mDataProp": "markerDto.devCode" },
                    {"mDataProp": "markerDto.testPileBuildComp" },
                    {"mDataProp": "markerDto.x_cord"},
                    {"mDataProp": "markerDto.y_cord" },
                    {"mDataProp": "markerDto.memo" },
                    {"sTitle":"电位","mDataProp":"markerDto.testPile","sClass":"center"},
                    {"sTitle":"编辑","mDataProp":"markerDto.id","sClass":"center"},
                    {"sTitle":"删除","mDataProp":"markerDto.id","sClass":"center"}
                ];
                return  "<thead> <tr>"
                    + "<th hidden='hidden'>id</th>"
                    + "<th>标识器ID</th>"
                    + "   <th>附属物名称</th>"
                    + "   <th>权属单位</th>"
                    + "   <th>所属区域</th>"
                    + "   <th>管网编号</th>"
                    + "   <th>所属道路</th>"
                    + "   <th>测试桩名称</th>"
                    + "   <th>设备编码</th>"
                    + "   <th>测试桩建设单位</th>"
                    + "   <th>X坐标</th>"
                    + "   <th>Y坐标</th>"
                    + "   <th>备注</th>"
                    + "   <th>电位</th>"
                    + "  <th>编辑</th>"
                    + "  <th>删除</th>"
                    + "</tr>"
                    + "</thead> ";
            } else if (type == 3) {
                aoColums = [
                    {"mDataProp": "markerDto.id" },
                    {"mDataProp": "markerDto.markerId" },
                    {"mDataProp": "markerDto.markerObjectType" },
                    {"mDataProp": "markerDto.ownerComp" },
                    {"mDataProp": "markerDto.area" },
                    {"mDataProp": "markerDto.antiCorrodedNo"},
                    {"mDataProp": "markerDto.projectName"},
                    {"mDataProp": "markerDto.projectNo"},
                    {"mDataProp": "markerDto.line" },
                    {"mDataProp": "markerDto.pressLevel"},
                    {"mDataProp": "markerDto.pipeDiameter" },
                    {"mDataProp": "markerDto.pipeMaterial"},
                    {"mDataProp": "markerDto.depth"},
                    {"mDataProp": "markerDto.antiCorrodedType"},
                    {"mDataProp": "markerDto.designThickness" },
                    {"mDataProp": "markerDto.road"},
                    {"mDataProp": "markerDto.constructTime"},
                    {"mDataProp": "markerDto.x_cord"},
                    {"mDataProp": "markerDto.y_cord"},
                    {"mDataProp": "markerDto.memo"},
                    {"sTitle":"编辑","mDataProp":"markerDto.id","sClass":"center"},
                    {"sTitle":"删除","mDataProp":"markerDto.id","sClass":"center"}
                ];
                return  "<thead> <tr>"
                    + "<th hidden='hidden'>id</th>"
                    + "<th>标识器ID</th>"
                    + "<th>特征管点</th>"
                    + "   <th>权属单位</th>"
                    + "   <th>所属区域</th>"
                    + "   <th>防腐工号</th>"
                    + "   <th>工程名称</th>"
                    + "   <th>工程编号（图纸）</th>"
                    + "   <th>管网编号</th>"
                    + "   <th>压力级制</th>"
                    + "   <th>管径</th>"
                    + "   <th>材质</th>"
                    + "   <th>设计埋深</th>"
                    + "   <th>防腐层种类</th>"
                    + "   <th>设计壁厚</th>"
                    + "   <th>所属道路</th>"
                    + "   <th>建设时间</th>"
                    + "   <th>X坐标</th>"
                    + "  <th>Y坐标</th>"
                    + "  <th>备注</th>"
                    + "  <th>编辑</th>"
                    + "  <th>删除</th>"
                    + "</tr>"
                    + "</thead> ";
            } else if(type == 4){
                aoColums = [
                    {"mDataProp": "markerDto.id"},
                    {"mDataProp": "markerDto.markerId"},
                    {"mDataProp": "markerDto.ownerComp"},
                    {"mDataProp": "markerDto.area"},
                    {"mDataProp": "markerDto.line"},
                    {"mDataProp": "markerDto.pipeLength"},
                    {"mDataProp": "markerDto.pipeDiameter"},
                    {"mDataProp": "markerDto.constructTime" },
                    {"mDataProp": "markerDto.pressLevel" },
                    {"mDataProp": "repairPointDto.leftThickness" },
                    {"mDataProp": "markerDto.antiCorrodedType" },
                    {"mDataProp": "repairPointDto.reason" },
                    {"mDataProp": "repairPointDto.repairMethod" },
                    {"mDataProp": "repairPointDto.groundType" },
                    {"mDataProp": "repairPointDto.repairTime" },
                    {"mDataProp": "markerDto.x_cord"},
                    {"mDataProp": "markerDto.y_cord" },
                    {"mDataProp": "markerDto.memo" },
                    {"sTitle":"编辑","mDataProp":"markerDto.id","sClass":"center"},
                    {"sTitle":"删除","mDataProp":"markerDto.id","sClass":"center"}
                ];
                return  "<thead> <tr>"
                    + "<th hidden='hidden'>id</th>"
                    + "<th>标识器ID</th>"
                    + "   <th>权属单位</th>"
                    + "   <th>所属区域</th>"
                    + "   <th>管网编号</th>"
                    + "   <th>管线长度</th>"
                    + "   <th>管径</th>"
                    + "   <th>建设时间</th>"
                    + "   <th>压力级制</th>"
                    + "   <th>剩余壁厚</th>"
                    + "   <th>防腐层种类</th>"
                    + "   <th>漏气原因</th>"
                    + "   <th>修复方式</th>"
                    + "   <th>地面类型</th>"
                    + "   <th>抢修时间</th>"
                    + "   <th>X坐标</th>"
                    + "   <th>Y坐标</th>"
                    + "   <th>备注</th>"
                    + "  <th>编辑</th>"
                    + "  <th>删除</th>"
                    + "</tr>"
                    + "</thead> ";
            }
            else if(type == 5){
                aoColums = [
                    {"mDataProp": "markerDto.id"},
                    {"mDataProp": "markerDto.markerId"},
                    {"mDataProp": "markerDto.ownerComp"},
                    {"mDataProp": "markerDto.area"},
                    {"mDataProp": "markerDto.line"},
                    {"mDataProp": "markerDto.jobNo"},
                    {"mDataProp": "markerDto.jobContent"},
                    {"mDataProp": "markerDto.pressLevel" },
                    {"mDataProp": "markerDto.x_cord"},
                    {"mDataProp": "markerDto.y_cord" },
                    {"mDataProp": "markerDto.pipeDiameter" },
                    {"mDataProp": "markerDto.pipeMaterial" },
                    {"mDataProp": "markerDto.designThickness" },
                    {"mDataProp": "markerDto.antiCorrodedType" },
                    {"mDataProp": "markerDto.constructTime" },
                    {"mDataProp": "markerDto.newLineNo" },
                    {"mDataProp": "markerDto.newPipeDiameter" },
                    {"mDataProp": "markerDto.newPipeMaterial" },
                    {"mDataProp": "markerDto.newDesignThickness" },
                    {"mDataProp": "markerDto.newAntiCorrodedType" },
                    {"mDataProp": "markerDto.newConstructTime" },
                    {"mDataProp": "markerDto.memo" },
                    {"sTitle":"编辑","mDataProp":"markerDto.id","sClass":"center"},
                    {"sTitle":"删除","mDataProp":"markerDto.id","sClass":"center"}
                ];
                return  "<thead> <tr>"
                    + "<th hidden='hidden'>id</th>"
                    + "<th>标识器ID</th>"
                    + "   <th>权属单位</th>"
                    + "   <th>所属区域</th>"
                    + "   <th>管网编号</th>"
                    + "   <th>作业编号</th>"
                    + "   <th>作业内容</th>"
                    + "   <th>压力级制</th>"
                    + "   <th>X坐标</th>"
                    + "   <th>Y坐标</th>"
                    + "   <th>管径</th>"
                    + "   <th>管材</th>"
                    + "   <th>管壁厚度</th>"
                    + "   <th>涂层种类</th>"
                    + "   <th>建设时间</th>"
                    + "   <th>新管编号</th>"
                    + "   <th>新管管径</th>"
                    + "   <th>新管管材</th>"
                    + "   <th>新管壁厚</th>"
                    + "   <th>新管涂层种类</th>"
                    + "   <th>新管建设时间</th>"
                    + "   <th>备注</th>"
                    + "  <th>编辑</th>"
                    + "  <th>删除</th>"
                    + "</tr>"
                    + "</thead> ";
            }
        },


        init: function () {
            if( $("#record-type").val()) typeValue = $("#record-type").val();
            //监听类型下拉列表变化事件，
            $("#record-type").live("change", function (e) {
                typeValue = $("#record-type").val();
                $("#table_marker").children().remove();
                Marker.recordType = typeValue;
                var title = MarkerInfoList.tableTitle(typeValue);
                oTable = $('#table_marker').dataTable({
                    // set the initial value
                    "iDisplayLength": 5,
                    "bAutoWidth": false,
                    "bDestroy": true,
                    "bLengthChange": false,
                    "bFilter": false,
                    "bServerSide": true,
                    "bPaginate": true,
                    "sDom":"<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                    "sPaginationType": "bootstrap",
                    "sAjaxSource": $('#context').val() + "/marker/page-query-markers.do", //TODO LIST:修改成对应的后台Controller地址
                    "fnServerData": retrieveData,
                    "oLanguage": {
                        "sSearch": "标识器型号:",
                        "sLengthMenu": "每页显示 _MENU_ 条记录",
                        "sZeroRecords": "抱歉， 没有找到",
                        "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                        "sInfoFiltered": "",
                        "sInfoEmpty": "没有数据",
                        "oPaginate": {
                            "sPrevious": "上一页",
                            "sNext": "下一页",
                            "sLast": "末页",
                            "sFirst": "首页"
                        }
                    },
                    "aoColumns": aoColums,
                    "aoColumnDefs": aoColumnDefs
                });
                $("#table_marker thead").children().remove();
                $("#table_marker thead").remove();
                $("#table_marker").append(title);
            });

            $("table tr td:contains('电位信息')").die().live("click",function(){//点击电位信息，由于mRender中超链接中文参数无效，不得已写这段代码
//                var tmp = $(this).parent().children("td").eq(6).text();
                MarkerInfoList.potentialDetail($(this).parent().children("td").eq(6).text())
            });
            Marker.recordType = typeValue;
            var title = MarkerInfoList.tableTitle(typeValue);
            $("#table_marker").children().remove();
            $("#table_marker").append(title);

            handleDatePickers();

            function retrieveData(sSource, aoData, fnCallback) {

                $.ajax({
                    type: "POST",
                    url: sSource,
                    dataType: "json",
                    //TODO LIST：按条件查询服务器数据
                    data: {
                        jsonParam: JSON.stringify(aoData),
                        markerDto: JSON.stringify(Marker)

                    },
                    success: function (data) {
                        fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式
                    }
                });
            }

            oTable = $('#table_marker').dataTable({
                // set the initial value
                "iDisplayLength": 5,
                "bAutoWidth": false,
                "bDestroy": true,
                "bLengthChange": false,
                "bFilter": false,
                "bServerSide": true,
                "bPaginate": true,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "sAjaxSource": $('#context').val() + "/marker/page-query-markers.do", //TODO LIST:修改成对应的后台Controller地址
                "fnServerData": retrieveData,
                "oLanguage": {
                    "sSearch": "标识器型号:",
                    "sLengthMenu": "每页显示 _MENU_ 条记录",
                    "sZeroRecords": "抱歉， 没有找到",
                    "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                    "sInfoFiltered": "",
                    "sInfoEmpty": "没有数据",
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页",
                        "sLast": "末页",
                        "sFirst": "首页"
                    }
                },
                "aoColumns": aoColums,
                "aoColumnDefs": aoColumnDefs
            });


            $('#searchMarker').bind('click', function (e) {
                var  beginDate=  $("#txt_begin_day").val();
                var endDate =  $("#txt_end_day").val();
                if(beginDate>endDate){
                    alert("开始日期不能大于结束日期");
                    return;
                }
                if(isNaN($("#x_cord").val())||isNaN($("#y_cord").val())){
                    alert("X坐标和Y坐标必须为数字");
                    return;
                }
                Marker = {};
                Marker.recordType = typeValue;
                Marker.markerObjectType = $("#marker-object-name").val();
                Marker.ownerComp = $("#belong-comp").val();
                Marker.area = $("#area").val();
                Marker.pressLevel = $("#press-level").val();
                Marker.devCode = $("#dev-code").val();
                Marker.testPile = $("#test-pile").val();
                Marker.line = $("#line").val();
                if($("#x_cord").val()) Marker.x_cord = $("#x_cord").val();
                if($("#y_cord").val()) Marker.y_cord = $("#y_cord").val();
                Marker.sDate = $("#txt_begin_day").val();
                Marker.eDate = $("#txt_end_day").val();
                Marker.memo = $("#memo").val();
                oTable.fnDraw(true);

            });
            $('#exp').live('click', function (e) {
                var  beginDate=  $("#txt_begin_day").val();
                var endDate =  $("#txt_end_day").val();
                if(beginDate>endDate){
                    alert("开始日期不能大于结束日期");
                    return;
                }
                if(isNaN($("#x_cord").val())||isNaN($("#y_cord").val())){
                    alert("X坐标和Y坐标必须为数字");
                    return;
                }
                Marker = {};
                Marker.recordType = typeValue;
                Marker.markerObjectType = $("#marker-object-name").val();
                Marker.ownerComp = $("#belong-comp").val();
                Marker.area = $("#area").val();
                Marker.pressLevel = $("#press-level").val();
                Marker.devCode = $("#dev-code").val();
                Marker.testPile = $("#test-pile").val();
                Marker.line = $("#line").val();
                if($("#x_cord").val()) Marker.x_cord = $("#x_cord").val();
                if($("#y_cord").val()) Marker.y_cord = $("#y_cord").val();
                Marker.sDate = $("#txt_begin_day").val();
                Marker.eDate = $("#txt_end_day").val();
                Marker.memo = $("#memo").val();
                location.href = $('#context').val() + "/marker/export.do?strMarkerDto=" + JSON.stringify(Marker);

            });

        }
    };
}();