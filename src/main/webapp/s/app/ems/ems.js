/**
 * Created by lenovo on 2016/4/13.
 */
var Ems = function () {
    var map = null;
    var markerData = [];

    function requestMarkers() {
        if (markerData.length == 0) {
            $.ajax({
                type: "get",
                url: $('#context').val() + "/marker/get-markers-data.do",
                dataType: "json",
                cache: false,
                success: function (r) {
                    if (r.data) {//请求成功，将获取到的数据加载到地图上
                        //1.解析返回的结果,.设置全局变量data的值
                         markerData = [];//清空原来的数据
                        for (var t = 0; t < r.data.length; t++) {
                            var markerBusinessDto = r.data[t];
                            markerData[t] = new MarkerInfo(markerBusinessDto);
                        }
                        //2.增加marker
                        Ems.addMarker(markerData);
                    }
                    map.removeEventListener("tilesloaded", requestMarkers);
                },
                error: function (d) {//请求出错
                    alert(d.responseText);
                    map.removeEventListener("tilesloaded", requestMarkers);
                }
            });
        } else {
            Ems.addMarker(markerData);
        }
    }

    function pointCollection(map,points,type,status){
        if(!points||points.length==0) return;
        var options = {
            size: BMAP_POINT_SIZE_NORMAL,
            shape: BMAP_POINT_SHAPE_WATERDROP,
            color: '#009acd'
        };
        if(type==2) {//附属物
            options.shape = BMAP_POINT_SHAPE_CIRCLE;
            switch (status) {
                case "合格":
                    options.color = "green";
                    break;
                case "不合格":
                    options.color = "red";
                    break;
                case "过保护":
                    options.color = "yellow";
                    break;
                case "无法检测":
                    options.color = "#ff00ff";
                    break;
                default :
                    options.color = "blue";
                    break;
            }
        }else if(type ==3){
            options.shape = BMAP_POINT_SHAPE_STAR;//BMAP_POINT_SHAPE_CIRCLE;
        }else if(type == 4){
            options.shape = BMAP_POINT_SHAPE_SQUARE;//BMAP_POINT_SHAPE_SQUARE;
        }else  if(type == 5){
            options.shape = BMAP_POINT_SHAPE_RHOMBUS;//BMAP_POINT_SHAPE_RHOMBUS;
        }
        var pointCollection = new BMap.PointCollection(points, options);  // 初始化PointCollection
        pointCollection.addEventListener('click', function (e) {
            var emsBean = e.point.Tag;
            var searchInfoWindow = null;
            var msg = "";
            var height = 0;
            var titleName ="";
            if (emsBean.recordType == 1) {
                msg = msg + "对象种类:" + emsBean.markerObjectType + "<br/>" +
                    "权属单位:" + emsBean.ownerComp + "<br/>" +
                    "所属区域:" + emsBean.area + "<br/>" +
                    "管网编号:" + emsBean.line + "<br/>" +
                    "压力级制:" + emsBean.pressLevel + "<br/>" +
                    "管径:" + emsBean.pipeDiameter + "<br/>" +
                    "材质:" + emsBean.pipeMaterial + "<br/>" +
                    "所属道路:" + emsBean.road + "<br/>" +
                    "建设时间:" + emsBean.constructTime + "<br/>" +
                    "X坐标:" + emsBean.x_cord + "<br/>" +
                    "Y坐标:" + emsBean.y_cord + "<br/>" ;
                height = 220;
                titleName = "管线"
            } else if (emsBean.recordType == 2) {
                msg = msg + "附属物名称:" + emsBean.markerObjectType + "<br/>"+
                    "权属单位:" + emsBean.ownerComp + "<br/>" +
                    "所属区域:" + emsBean.area + "<br/>" +
                    "管网编号:" + emsBean.line + "<br/>" +
                    "所属道路:" + emsBean.road + "<br/>" +
                    "测试桩名称:" + emsBean.testPile + "<br/>" +
                    "X坐标:" + emsBean.x_cord + "<br/>" +
                    "Y坐标:" + emsBean.y_cord + "<br/>" +
                    "备注:" + emsBean.memo + "<br/>" +
                    "保护电位(-V):" + emsBean.protectivePotential + "<br/>" ;

                height = 160;
                titleName = "管线附属物"
            } else if (emsBean.recordType == 3) {
                msg = msg + "特征管点:" + emsBean.markerObjectType + "<br/>" +
                    "权属单位:" + emsBean.ownerComp + "<br/>" +
                    "所属区域:" + emsBean.area + "<br/>" +
                    "管网编号:" + emsBean.line + "<br/>" +
                    "压力级制:" + emsBean.pressLevel + "<br/>" +
                    "管径:" + emsBean.pipeDiameter + "<br/>" +
                    "材质:" + emsBean.pipeMaterial + "<br/>" +
                    "所属道路:" + emsBean.road + "<br/>" +
                    "建设时间:" + emsBean.constructTime + "<br/>" +
                    "X坐标:" + emsBean.x_cord + "<br/>" +
                    "Y坐标:" + emsBean.y_cord + "<br/>" ;
                height = 220;
                titleName = "管线特征管点"
            } else  if (emsBean.recordType == 4)  {
                msg = msg + "对象种类:" + emsBean.markerObjectType + "<br/>" +
                    "权属单位:" + emsBean.ownerComp + "<br/>" +
                    "所属区域:" + emsBean.area + "<br/>" +
                    "管网编号:" + emsBean.line + "<br/>" +
                    "漏气原因:" + emsBean.reason + "<br/>" +
                    "修复方式:" + emsBean.repairMethod + "<br/>" +
                    "地面类型:" + emsBean.groundType + "<br/>" +
                    "抢修时间:" + emsBean.repairTime + "<br/>" +
                    "X坐标:" + emsBean.x_cord + "<br/>" +
                    "Y坐标:" + emsBean.y_cord + "<br/>" ;
                height = 200;
                titleName = "抢修点"
            }else{
                msg = msg + "对象种类:" + emsBean.markerObjectType + "<br/>" +
                    "权属单位:" + emsBean.ownerComp + "<br/>" +
                    "所属区域:" + emsBean.area + "<br/>" +
                    "管网编号:" + emsBean.line + "<br/>" +
                    "作业编号:" + emsBean.jobNo + "<br/>" +
                    "作业内容:" + emsBean.jobContent + "<br/>" +
                    "压力级制:" + emsBean.pressLevel + "<br/>" +
                    "X坐标:" + emsBean.x_cord + "<br/>" +
                    "Y坐标:" + emsBean.y_cord + "<br/>" ;
                height = 220;
                titleName = "接切线点"
            }
            var imgUrl = $('#context').val() + "/images/ems.jpg";
            var content = '<div style="margin:0;line-height:20px;padding:2px;overflow: auto;">' +
                '<img src=' + '"' + imgUrl + '"' + ' alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:100px;margin-left:3px;"/>' +
                msg +
                '</div>';
            searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
                title: titleName,      //标题
                width: 290,             //宽度
//                        height: height,              //高度
                panel: "panel",         //检索结果面板
                enableAutoPan: true    //自动平移
            });
            searchInfoWindow.open(e.point);
            $(".BMapLib_search_text").css("padding", "0");
            $(".BMapLib_search_text").css("margin-bottom", "0");

            $(".BMapLib_trans_text").css("padding", "0");
            $(".BMapLib_trans_text").css("margin-bottom", "0");

        });
        map.addOverlay(pointCollection);  // 添加Overlay

    }
    return {

        emsUploadCallBack: function () {
            $('#uploadModal').modal('show')
        },

        emsExportCallBack: function () {
            location.href = $('#context').val() + "/marker/export.do";
        },

        initMap: function () {

            map = new BMap.Map("container");          // 创建地图实例

            var myFun = function (result) {//根据城市名进行定位
                var cityName = result.name;
                map.setCenter(cityName);
            }

            var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
            map.centerAndZoom(point, 17);                 // 初始化地图，设置中心点坐标和地图级别
            var myCity = new BMap.LocalCity();
            myCity.get(myFun);
            map.enableScrollWheelZoom(); //启用地图滚轮放大缩小
            var ctrl_ove = new BMap.OverviewMapControl({ anchor: BMAP_ANCHOR_BOTTOM_RIGHT });
            map.addControl(ctrl_ove);
            //向地图中添加比例尺控件
            var ctrl_sca = new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT });
            map.addControl(ctrl_sca);

            //批量导入标识器数据,弹出对应的对话框
            var uploadImage = $('#context').val() + "/images/ems/emsUpload.png";
            var offset2 = new BMap.Size(10, 100);
            var emsUploadControl = new EmsControl(map, uploadImage, Ems.emsUploadCallBack, offset2, "批量导入");
            map.addControl(emsUploadControl);

            //批量导出标识器数据,弹出对应的对话框
            var exportImage = $('#context').val() + "/images/ems/emsExport.png";
            var offset3 = new BMap.Size(10, 170);
            var emsExportControl = new EmsControl(map, exportImage, Ems.emsExportCallBack, offset3, "导出Excel");
            map.addControl(emsExportControl);

            var offset5 = new BMap.Size(10, 10);
            var emsEditControl = new EmsEditControl(map, offset5);
            map.addControl(emsEditControl);

//加载完毕请求标识点信息
            map.addEventListener("tilesloaded", requestMarkers);
        },

        addMarker: function (data) {
            if (document.createElement('canvas').getContext) {  // 判断当前浏览器是否支持绘制海量点
                var points = [[],[],[],[],[]];  // 添加海量点数据
                for (var i = 0; i < data.length; i++) {
                    var bdPoint = gpsBdUtils.gcj2bd(data[i].latitude, data[i].longitude);
                    var point = new BMap.Point(bdPoint.longitude, bdPoint.latitude);
                    point.Tag = data[i];
                    if(data[i].recordType==1) points[0].push(point);
                    else if(data[i].recordType==2) {
                        if(!data[i].protectivePotential) {
                            if(!points[1]["无法检测"])  points[1]["无法检测"] = [];
                            points[1]["无法检测"].push(point);
                        }
                        else if(data[i].protectivePotential >1.2){
                            if(!points[1]["过保护"])  points[1]["过保护"] = [];
                            points[1]["过保护"].push(point);
                        }
                        else if(data[i].protectivePotential >=0.85){
                            if(!points[1]["合格"])  points[1]["合格"] = [];
                            points[1]["合格"].push(point);
                        }
                        else if(data[i].protectivePotential >0){
                            if(!points[1]["不合格"])  points[1]["不合格"] = [];
                            points[1]["不合格"].push(point);
                        }
                    }
                    else if(data[i].recordType==3) points[2].push(point);
                    else if(data[i].recordType==4) points[3].push(point);
                    else if(data[i].recordType==5) points[4].push(point);
//                    points.push(point);
                }
                for(var i = 0;i<5;i++){
                    if(i==1){
                        pointCollection(map,points[1]["合格"],2,"合格");
                        pointCollection(map,points[1]["不合格"],2,"不合格");
                        pointCollection(map,points[1]["过保护"],2,"过保护");
                        pointCollection(map,points[1]["无法检测"],2,"无法检测");
                    }
                   else pointCollection(map,points[i],i+1);
                }
            } else {
                alert('请在chrome、safari、IE8+以上浏览器查看本示例');
            }
        }


    };
}();