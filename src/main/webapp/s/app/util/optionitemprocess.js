/**
 * Created by lenovo on 2016/12/6.
 */
var OptionItemProcess = new function(){

    return{
        GetListByType:function(arr,type){
            var result = [];
            for(var item in arr){
                if(item.type&&item.type==type){
                    result.push(item.name);
                }
            }
            return result;
        },
        GetDataFromDb:function(){
            $.ajax({
                type: "get",
                url: $('#context').val() + "/optionItems/allItems.do",
                dataType: "json",
                cache: true,
                async:false,
                success: function (r) {
                    if (r.success) {//请求成功
                       for(var i=0;i< r.data.length;i++){
                           var optionItems = r.data[i];
                           if(!window.optionItemList) window.optionItemList = new Array();
                           if(!window.optionItemList[optionItems.type+""]){
                               window.optionItemList[optionItems.type+""] = new Array();//是一个二维数组，type为下标
                           }
                           window.optionItemList[optionItems.type+""].push(optionItems.name);
                       }

                    }
                },
                error: function (d) {//请求出错
                    alert("请求出错");
                }
            });
        },

        GetOptionStrByArr:function(arr){
            var result = "";
            for(var s in arr){
                result+="<option value='"+arr[s]+"'>";
            }
            return result;
        },
        initPipeTypeList:function(type) {
            if(type>4) return;//该字段的type定义不大于3
            if(type==4) type=1;
            var arr = window.optionItemList[type+""];
            var optionStr = this.GetOptionStrByArr(arr);
            $("#pipeTypeList").children().remove();
            $("#pipeTypeList").append(optionStr);
        },
        initBelowPipeTypeList:function(){
            var arr = window.optionItemList["1"];
            var optionStr = this.GetOptionStrByArr(arr);
            $("#belowPipeTypeList").children().remove();
            $("#belowPipeTypeList").append(optionStr);
        },
        initMaterialList:function(){
            var arr = window.optionItemList["MaterialType"];
            var optionStr = this.GetOptionStrByArr(arr);
            $("#belowPipeMaterialList").children().remove();
            $("#belowPipeMaterialList").append(optionStr);
            $("#pipeMaterialList").children().remove();
            $("#pipeMaterialList").append(optionStr);
        },
        initLayStyleList:function(){
            var arr = window.optionItemList["layStyleType"];
            var optionStr = this.GetOptionStrByArr(arr);
            $("#layStyleList").children().remove();
            $("#layStyleList").append(optionStr);
        },
        initAreaList:function(){
        var arr = window.optionItemList["areaItem"];
        var optionStr = this.GetOptionStrByArr(arr);
        $("#areaList").children().remove();
        $("#areaList").append(optionStr);
        }


    }
}