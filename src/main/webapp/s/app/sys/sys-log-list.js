/**
 * Created by Administrator on 2015/7/26.
 */
var SysLogList = function () {

    var oTable;

    var handleDatePickers = function () {

        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL()
            });
        }
    }

    return {

        init: function () {

            handleDatePickers();

            function retrieveData(sSource, aoData, fnCallback) {
                $.ajax({
                    type: "POST",
                    url: sSource,
                    dataType: "json",
                    //TODO LIST：按条件查询服务器数据
                    data: {
                        jsonParam: JSON.stringify(aoData),
                        logType: $('#log-type').val(),
                        beginDay: $('#begin-day').val(),
                        endDay: $('#end-day').val()
                    },
                    success: function (data) {
                        //$("#url_sortdata").val(data.aaData);
                        fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式
                    }
                });
            }

            oTable = $('#table_log').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                "iDisplayLength": 5,
                "sPaginationType": "bootstrap",
                "bFilter": false,
                "bServerSide": true,
                "bPaginate": true,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sAjaxSource": $('#context').val() + "/sys/log-info-list.do", //TODO LIST:修改成对应的后台Controller地址
                "fnServerData": retrieveData,
                "oLanguage": {
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
                "aoColumns": [{
                    "mDataProp": "id"
                }, {
                    "mDataProp": "logType"
                }, {
                    "mDataProp": "logDay"
                }, {
                    "mDataProp": "msg"
                }, {
                    "mDataProp": "userName"
                }, {
                    "mDataProp": "btn",
                    "sWidth": "60px"
                }],
                "aoColumnDefs": [{
                    'bVisible': false,
                    'aTargets': [0]
                }, {
                    'bSortable': false,
                    'aTargets': [1, 2, 3, 4, 5]
                }]
            });

            jQuery('#table_log_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#table_log_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            jQuery('#table_log_wrapper .dataTables_length select').select2({
                showSearchInput: false //hide search box with special css class
            });

            //TODO LIST:删除资源n操作
            $('#table_log a.blue').live('click', function (e) {
                e.preventDefault();

                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var id = aData.id;

                var iframe = document.getElementById('sysLogMsgFrame');
                var top = ($(window).height() - iframe.height) / 2;
                var left = ($(window).width() - iframe.width) / 2;
                var scrollLeft = $(document).scrollLeft();
                var scrollTop = $(document).scrollTop();
                iframe.src = $('#context').val() + "/sys/sys-log-msg.do?id=" + id;
                iframe.style.display = 'block';
                iframe.style.position = 'absolute';
                iframe.style.left = (left + scrollLeft) + "px";
                iframe.style.top = (top + scrollTop) + "px";

            });

            $('#app_query').bind('click', function (e) {
                oTable.fnDraw();
            });

            $('#app_exp').bind('click', function (e) {
                jQuery.ajax({
                    url: $('#context').val() + '/sys/exp-sys-log.do',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        logType: $('#log-type').val(),
                        beginDay: $('#begin-day').val(),
                        endDay: $('#end-day').val()
                    },
                    success: function (data) {
                        if (data.success) {
                            window.location.href = $('#context').val() + '\\content\\xls\\log.xls';
                        } else {
                            alert(data.message);
                        }
                    },
                    error: function (data) {
                        alert('日志导出请求失败！');
                    }
                });

            });

        }
    };
}();
