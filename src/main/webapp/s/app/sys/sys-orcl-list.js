/**
 * Created by Administrator on 2015/7/26.
 */
var SysOrclList = function () {

    var oTable;

    var handleDatePickers = function () {

        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl : App.isRTL()
            });
        }
    }

    return {

        init: function () {

            handleDatePickers();

            function retrieveData( sSource, aoData, fnCallback ) {
                $.ajax( {
                    type: "POST",
                    url: sSource,
                    dataType:"json",
                    //TODO LIST：按条件查询服务器数据
                    data: {
                        jsonParam : JSON.stringify(aoData),
                        logType : $('#log-type').val(),
                        beginDay : $('#begin-day').val(),
                        endDay : $('#end-day').val()
                    },
                    success: function(data) {
                        //$("#url_sortdata").val(data.aaData);
                        fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式
                    }
                });
            }

            oTable =  $('#table_log').dataTable({
                "bFilter":false,
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                "iDisplayLength": 5,
                "bServerSide": true,
                "bPaginate": true,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "sAjaxSource": $('#context').val()+"/sys/orcl-info-list.do", //TODO LIST:修改成对应的后台Controller地址
                "fnServerData":retrieveData,
                "oLanguage": {
                    "sLengthMenu": "每页显示 _MENU_ 条记录",
                    "sZeroRecords": "抱歉， 没有找到",
                    "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                    "sInfoEmpty": "没有数据",
                    "oPaginate": {
                        "sPrevious":"上一页",
                        "sNext": "下一页",
                        "sLast": "末页",
                        "sFirst": "首页"
                    }
                },
                "aoColumns" : [  {
                    "mDataProp" : "id"
                },  {
                    "mDataProp" : "fileName",
                    "sWidth" : "180px"
                }, {
                    "mDataProp" : "bkDay",
                    "sWidth" : "80px"
                },  {
                    "mDataProp" : "memo"
                },  {
                    "mDataProp" : "userName",
                    "sWidth" : "100px"
                }, {
                    "mDataProp" : "btnRestor",
                    "sWidth" : "60px"
                }, {
                    "mDataProp" : "btnDelete",
                    "sWidth" : "60px"
                } ],
                "aoColumnDefs": [{
                    'bVisible': false,
                    'aTargets': [0]
                },{
                    'bSortable': false,
                    'aTargets': [1,2,3,4,5,6]
                }]
            });

            jQuery('#table_log_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#table_log_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            jQuery('#table_log_wrapper .dataTables_length select').select2({
                showSearchInput : false //hide search box with special css class
            });

            //TODO LIST:数据还原
            $('#table_log a.blue').live('click', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var id = aData.id;

                $.ajax( {
                    type: "POST",
                    url: $("#context").val() + "/sys/orcl-info-restore.do",
                    //  dataType:'json',
                    data: {
                        id : id
                    },
                    success: function(data) {

                        var jData = eval(data);
                        if(jData.success==true) {
                            alert("恢复成功！")
                        }
                        else{
                            alert("恢复失败！");
                        }

                        target.loadingOverlay('remove');
                    },
                    error:function(request){
                        alert("恢复失败！");
                    }
                });
            });

            //TODO LIST:备份文件删除
            $('#table_log a.red').live('click', function (e) {
                e.preventDefault();
                if (confirm("确认要删除此备份文件吗 ?") == false) {
                    return;
                }
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var id = aData.id;

                $.ajax( {
                    type: "POST",
                    url: $("#context").val() + "/sys/orcl-info-delete.do",
                    //  dataType:'json',
                    data: {
                        id : id
                    },
                    success: function(data) {

                        var jData = eval(data);
                        if(jData.success==true) {
                            alert("删除成功！")
                        }
                        else{
                            alert("删除失败！");
                        }

                        oTable.fnDraw();
                    },
                    error:function(request){
                        alert("恢复失败！");
                    }
                });
            });

            $('#app_query').click('click',function(e){
                oTable.fnDraw();
            });

            $('#app_backup').bind('click',function(e){
                location.href = $("#context").val() + "/content/sys/orcl-info-edit.jsp"
            });
        }
    };
}();
