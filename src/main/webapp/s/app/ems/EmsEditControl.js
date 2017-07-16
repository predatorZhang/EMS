function EmsEditControl(map,offset) {
    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
    this.defaultOffset = offset;
    //TODO LIST：判断是否支持百度Map控件
    this.map = map;
}
EmsEditControl.prototype = new BMap.Control();
EmsEditControl.prototype.initialize = function(map) {
    //创建一个dom对象
     var div = document.createElement("div");
     var p = document.createElement('p');//创建一个p标签

    //TODO LIST:修改为获取百度tmpl模板数据进行数据渲染
    var template = "../../tmpl/query/findPanel.html";
    $.ajax({
        url: template,
        dataType: "html",
        success: function (val) {
          //  $("#list").html(baidu.template(val, data));
          /*  var operationalLayers = {
                [url: "http://127.0.0.1:6080/arcgis/rest/services/test/geishui/MapServer/0"]
            };*/
            var content = baidu.template(val, {});
            p.innerHTML = content;
            div.appendChild(p);
            var vectorMarkers = [];
            $(".searchbox-content-  ").css("margin", 0);
            $(".searchbox-content-button").click(function () {
                $("#route-searchbox-content").show();
            });

            $(".searchbox-content-button.cancel-button").click(function () {
                $("#route-searchbox-content").hide();
            });
            $(".routebox-input input").css("border-right", 0);
            $(".routebox-input input").css("width", "50%");
            $(".date-picker input").css("width", "80%");
            $("#search-button").click(function (e) {
                var Marker = {};
                if (vectorMarkers.length != 0) {
                    for (var i = 0; i < vectorMarkers.length; i++)
                        map.removeOverlay(vectorMarkers[i]);
                }
                 vectorMarkers = [];
                var recordType = "";
                if($("#record-type").val()) recordType = $("#record-type").val();
                switch (recordType){
                    case "管线":
                        Marker.recordType = 1;
                        break;
                    case "管线附属物":
                        Marker.recordType = 2;
                        break;
                    case "管线特征管点":
                        Marker.recordType = 3;
                        break;
                    case "抢修点":
                        Marker.recordType = 4;
                        break;
                    case "接切线点":
                        Marker.recordType = 5;
                        break;
                }
                Marker.markerObjectType = $("#marker-object-name").val();
                Marker.ownerComp = $("#belong-comp").val();
                Marker.area = $("#area").val();
                Marker.pressLevel = $("#press-level").val();
                Marker.devCode = $("#dev-code").val();
                Marker.testPile = $("#test-pile").val();
                Marker.line = $("#line").val();
                if($("#x_cord").val()) Marker.x_cord = $("#x_cord").val();
                if($("#y_cord").val()) Marker.y_cord = $("#y_cord").val();
                Marker.memo = $("#memo").val();
                Marker.sDate = $("#txt_begin_day").val();
                Marker.eDate = $("#txt_end_day").val();
                //请求后台的id列表，然后遍历marker列表，修改marker的颜色
                $.ajax({
                    type: "POST",
                    url: $('#context').val() + "/marker/get-markers-data.do",
                    data: {"strMarkerDto": JSON.stringify(Marker)},
                    dataType: "json",
                    success: function (r) {
                        if (r.data) {
                            var pointArray = new Array();
                            if (r.data.length > 1000) {
                                alert("有超过一千个点符合筛选条件，会造成浏览器卡顿，请重新选择");
                            } else {
                                if (r.data.length == 0) {
                                    alert("没有搜到符合条件的标识器");
                                    return;
                                }
                                for (var i in r.data) {//遍历列表，添加marker
                                    var markerDto = new MarkerInfo(r.data[i]);
                                    var bdpoint = gpsBdUtils.gcj2bd(markerDto.latitude, markerDto.longitude);
                                    var point = new BMap.Point(bdpoint.longitude, bdpoint.latitude);
                                    var vectorMarker = new BMap.Marker(new BMap.Point(point.lng, point.lat), {
                                        // 指定Marker的icon属性为Symbol
                                        icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
                                            scale: 1.2,//图标缩放大小
                                            fillColor: "green",//填充颜色
                                            fillOpacity: 0.8//填充透明度
                                        })
                                    });
                                    vectorMarkers.push(vectorMarker);
                                    map.addOverlay(vectorMarker);
                                    pointArray.push(point);
                                }
                                map.setViewport(pointArray);
                            }

                        }
                    },
                    error: function (request) {
                        //提示错误信息
                        alert("请求失败");
                    }
                });

            });

            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                autoclose: true,
                forceParse: false,
                language: 'zh-CN'
            });

        }
    });

    this.map.getContainer().appendChild(div);
    return div;

};