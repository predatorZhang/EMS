var TaskInfoList = function () {

    return {

        init: function () {

            function retrieveData(sSource, aoData, fnCallback) {
                $.ajax({
                    type: "POST",
                    url: sSource,
                    dataType: "json",
                    //TODO LIST：按条件查询服务器数据
                    data: "jsonParam=" + JSON.stringify(aoData),
                    success: function (data) {
                        //$("#url_sortdata").val(data.aaData);
                        fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式
                    }
                });
            }

            var oTable = $('#table_task').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "bServerSide": true,
                "bPaginate": true,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "sAjaxSource": $('#context').val() + "/task/task-info-list.do", //TODO LIST:修改成对应的后台Controller地址
                "fnServerData": retrieveData,
                "oLanguage": {
                    "sSearch": "工单编号:",
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
                "aoColumns": [{
                    "mDataProp": "id"
                },{
                    "mDataProp": "taskCode"
                },{
                    "mDataProp": "deployDate"
                },{
                    "mDataProp": "beginDate"
                },{
                    "mDataProp": "status"
                },{
                    "mDataProp": "endDate"
                },{
                    "mDataProp": "creatorName"
                },{
                    "mDataProp": "patrolerId"
                },{
                    "mDataProp": "patrolerName"
                },{
                    "mDataProp": "btnEdit"
                }
                ],
                "aoColumnDefs": [{
                    'aTargets': ['_all'],
                    sDefaultContent: ''
                },{
                    'bVisible': false,
                    'aTargets': [0,7]
                },{
                    'bSortable': false,
                    'aTargets': [1, 2, 3, 4, 5, 6, 7, 8, 9]
                }
                ]
            });

//            jQuery('#table_marker_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
//            jQuery('#table_marker_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
//            jQuery('#table_marker_wrapper .dataTables_length select').select2({
//                showSearchInput : false //hide search box with special css class
//            });

            //TODO LIST:删除资源n操作
            $('#table_task a.red').live('click', function (e) {
                e.preventDefault();

                if (confirm("确认要删除该操作 ?") == false) {
                    return;
                }
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var id = aData.id;
                $.ajax({
                    type: "POST",
                    url: $('#context').val() + "/marker1/marker1-info-delete.do",
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
                });

            });

            //TODO LIST:编辑操作
            $('#table_task a.blue').live('click', function (e) {
                e.preventDefault();
                //TODO LIST：提交userId，跳转到指定的do文件.
                var nRow = $(this).parents('tr')[0];
                var aData = oTable.fnGetData(nRow);
                var id = aData.id;
                location.href = $("#context").val() + "/marker1/marker1-info-edit.do?id=" + id;

            });

            $('#new_task').live('click', function (e) {
                //TODO LIST：提交到相应的.do url，导入到url连接
                location.href = $('#context').val()+"/task/task-info-edit.do";

            });


            function getPropertyIndex(excelSheet, propertyName) {
                var index = 1;
                while (true) {
                    var value = excelSheet.Cells(1, index).value;
                    if (!value) {
                        return -1;
                    } else if (value == propertyName) {
                        return index;
                    }
                    index++;
                }
            }

            function getNotNullValue(inValue) {
                var outValue = "";
                if (typeof inValue == "string") {
                    inValue = inValue.replace(/\r\n/, "");
                }
                if (null != inValue) {
                    outValue = inValue;
                }
                return outValue;
            }

        }
    };
}();