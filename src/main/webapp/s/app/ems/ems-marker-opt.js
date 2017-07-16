/**
 * Created by lenovo on 2016/5/18.
 */
var EmsMarkerOpt = function () {
    return {
        init: function () {
           $('#excel_import').click(function (e) {
                $("#excelForm").ajaxSubmit({
                    type:"post",
                    url:$("#context").val() +"/marker/import-markers.do",
                    success:function(data){
                        if(data.success){
                            alert("上传成功");
                        }else{
                            alert(data.message);
                        }

                    },
                    error:function(){
                        alert("上传失败");
                    }
                });

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

            function getNotNullNumberValue(inValue) {
                return inValue ? inValue : -1;
            }

        }
    }

}();
