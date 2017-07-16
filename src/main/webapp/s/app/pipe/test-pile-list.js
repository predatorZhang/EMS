/**
 * Created by lenovo on 2017/6/23.
 */
var TestPile = function(){
    var beginDate,endDate,testPile;
    testPile =  $("#testPile").val();
    beginDate=  $("#txt_begin_day").val();
    endDate =  $("#txt_end_day").val();
    var oTable = $('#table_test_pile').dataTable({
        "iDisplayLength": 5,
        "bAutoWidth": false,
        "bDestroy": true,
        "bLengthChange": false,
        "bFilter": false,
        "bServerSide": true,
        "bPaginate": true,
        "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
        "sPaginationType": "bootstrap",
        "sAjaxSource": $('#context').val() + "/tp/page-query-test-pile.do",
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
        "aoColumns": [
            {"mDataProp": "testPile" },
            {"mDataProp": "checkTime" },
            {"mDataProp": "protectivePotential" },
            {"mDataProp": "openCircuitPotential" },
            {"mDataProp": "emissionCurrent" },
            {"mDataProp": "refEleCalibration"}
           ],
        "aoColumnDefs": [ {
            aTargets: ["_all"],
            'bSortable': false
        }]
    });

    function retrieveData(sSource, aoData, fnCallback) {
        $.ajax({
            type: "POST",
            url: sSource,
            dataType: "json",
            //TODO LIST：按条件查询服务器数据
            data: {
                jsonParam: JSON.stringify(aoData),
                beginDate:beginDate,
                endDate:endDate,
                testPile:testPile
            },
            success: function (data) {
                fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式
            }
        });
    }
    function reset(){
        $("#testPile").val("");
        $("#txt_begin_day").val("");
        $("#txt_end_day").val("");
    }
    return {
        init:function(){
            $('#searchTestPile').click(function(){
                 testPile =  $("#testPile").val();
                 beginDate=  $("#txt_begin_day").val();
                 endDate =  $("#txt_end_day").val();
                if(beginDate>endDate){
                    alert("开始日期不能大于结束日期");
                    return;
                }
                oTable.fnDraw(true);
            });

            $('#exp').click(function(){
                testPile =  $("#testPile").val();
                var  beginDate=  $("#txt_begin_day").val();
                var endDate =  $("#txt_end_day").val();
                if(beginDate>endDate){
                    alert("开始日期不能大于结束日期");
                    return;
                }
                location.href = $('#context').val() + "/tp/export-tp.do?beginDate=" +beginDate+"&endDate="+endDate+"&testPile="+testPile ;
            });
        }

    }
}();