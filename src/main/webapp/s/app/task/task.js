/**
 * Created by lenovo on 2016/4/13.
 */
//定义全局变量,缓存组织结构

var Task = function () {

    var map = null;
    var taskMarkers = "";
    var drawOverlays = [];
    var drawTaskOverlays = [];
    var drawEventOverlays = [];
    var taskLists;
    var taskDetailLists =[];
    var eventBeanList = [];
    var viewPoints = [];
    var markerData = [];
    
    var options = {
        valueNames: [ 'id', 'patrolerName', 'status', 'deployDate', 'beginDate', 'endDate', 'description', {attr: 'src', name: 'image'} ],
        item: '<li onclick=""><img class="image" src="" style="float: left;width:20px;height:20px">' +
            '<a style="float: left;padding:2px 2px" ><p class="patrolerName"></p>' +
            '<p class="status"></p>' +
            '<p  class="deployDate"></p><p class="time"></p>' +
            '<p  class="beginDate"></p><p class="time"></p>' +
            '<p  class="endDate"></p><p class="time"></p>' +
            '<p class="description"></p>' +
            '<p class="id" style="display:none"></p>' +
            '</a>' +
            '<div style="clear:both"></div>' +
            ' </li>',
        page: 3,
        plugins: [ ListPagination({}) ]
    };

    function clearAll() {
        for (var i = 0; i < drawOverlays.length; i++) {
            map.removeOverlay(drawOverlays[i]);
        }
        drawOverlays.length = 0
    }

    function clearAllTaskPoint() {
        for (var i = 0; i < drawTaskOverlays.length; i++) {
            map.removeOverlay(drawTaskOverlays[i]);
        }
        drawTaskOverlays.length = 0;
        taskDetailLists.length = 0;
    }
    function clearAllEventPoint() {
        for (var i = 0; i < drawEventOverlays.length; i++) {
            map.removeOverlay(drawEventOverlays[i]);
        }
        drawEventOverlays = [];
        eventBeanList = [];
    }
    function getTaskInfo(patroler) {
        taskLists.clear();
        $.ajax({
            type: "get",
            url: $('#context').val() + "/task/get-task.do",
            dataType: "json",
            cache: false,
            data: {

            },
            success: function (r) {
                if (r.success) {//请求成功
//                    alert(r.data.length);
                    for (var data in r.data) {
                        //alert(r.data[data].id+":"+r.data[data].patrolerName);
                        var icon = $('#context').val() + "/images/accidents/worker.png";
                        var id = r.data[data].id;
                        var patrolerName = r.data[data].patrolerName;
                        if (patroler && patrolerName != patroler) {
                            continue;
                        }
                        var description = r.data[data].description;
                        var deployDate = r.data[data].deployDate;
                        var beginDate = r.data[data].beginDate;
                        var endDate = r.data[data].endDate;
                        var status = r.data[data].status;
                        var creatorName = r.data[data].creatorName;
                        var task = new TaskBean(id, icon, patrolerName, description, deployDate, beginDate, endDate, status, creatorName);

                        task.patrolerName = "巡检员：" + task.patrolerName;
                        task.description = "任务描述:" + task.description;
                        task.deployDate = "下发时间：" + task.deployDate;
                        task.beginDate = task.beginDate == null ? "开始时间：" : "开始时间：" + task.beginDate;
                        task.endDate = task.endDate == null ? "完成时间：" : "完成时间：" + task.endDate;
                        task.status = "任务状态：" + task.status;
                        taskLists.add(task);
                    }
                }
                else {
                    alert(r.message);
                }
            },
            error: function (d) {//请求出错
                alert(d.responseText);
            }
        });
    }
    function showEventInfo(e) {
        var p = e.target;
        var imgName = p.Tag.imageName;
        var images = imgName.split(",");//图片路径数组
//        alert(images[0]);
        var msg2 = "<strong>事件描述:</strong>" + p.Tag.description + "</br>" +
            "<strong>上传时间:</strong>" + p.Tag.createTime + "</br>";
        $("#eventImg").children().remove();//avatar1.jpg
        var imageDivs="<div class='row-fluid'>";
        var width = 100/images.length;//动态展示
        $("#viewHelper").children().remove();
        for(var i =0;i<images.length;i++){
            var url = $('#context').val() + "/images/events/" + images[i];
            imageDivs +="<div  class='span4'><div class='item'><a class='fancybox-button' data-rel='fancybox-button' href="+url+">" +
                                            "<div class='zoom'><img src="+ url + " />" +
                                                               "<div class='zoom-icon'></div></div></a>" +
                "</div></div>"

        }
        imageDivs+="</div>"
        $(".fancybox-close").die().live("click",function(){
            $('#eventModal').modal('show').css({width: "300px"});
        });
        $(".zoom-icon").die().live("click",function(){
            $('#eventModal').modal('hide');
        });
        $("#eventImg").append(imageDivs)
        $("#eventDescription").html(msg2);
        $('#eventModal').modal('show').css({width: "300px"});

    }

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
                        var indext = 0;
                        for (var t = 0; t < r.data.length; t++) {
                            var markerBusinessDto = r.data[t];
                            if(markerBusinessDto.markerDto.recordType==2)  markerData[indext++] = new MarkerInfo(markerBusinessDto);
                        }
                        //2.增加marker
                        Task.addMarker(markerData);
                    }
                    map.removeEventListener("tilesloaded", requestMarkers);
                },
                error: function (d) {//请求出错
                    alert(d.responseText);
                    map.removeEventListener("tilesloaded", requestMarkers);
                }
            });
        } else {
            Task.addMarker(markerData);
        }
    }
    function pointCollection(map,points,status){
        if(!points||points.length==0) return;
        var options = {
            size: BMAP_POINT_SIZE_NORMAL,
            shape: BMAP_POINT_SHAPE_CIRCLE,
            color: '#009acd'
        };
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
        var pointCollection = new BMap.PointCollection(points, options);  // 初始化PointCollection
        pointCollection.addEventListener('click', function (e) {
            var emsBean = e.point.Tag;
            var searchInfoWindow = null;
            var msg = "";
            var height = 0;
            var titleName ="";
            msg = msg + "附属物名称:" + emsBean.markerObjectType + "<br/>"+
                "权属单位:" + emsBean.ownerComp + "<br/>" +
                "所属区域:" + emsBean.area + "<br/>" +
                "管网编号:" + emsBean.line + "<br/>" +
                "所属道路:" + emsBean.road + "<br/>" +
                "测试桩名称:" + emsBean.testPile + "<br/>" +
                "X坐标:" + emsBean.x_cord + "<br/>" +
                "Y坐标:" + emsBean.y_cord + "<br/>"+
               "备注:" + emsBean.memo + "<br/>" +
            "保护电位(-V):" + emsBean.protectivePotential + "<br/>" ;
            height = 160;
            titleName = "管线附属物"
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

        //添加工单
        taskAddCallBack: function () {
            taskMarkers = "";
            $('#addTaskModal').modal('show');
        },
        //添加工单
        selectMarkerCallBack: function () {
            //绘制矩形区域:
            taskMarkers = "";
            if (drawOverlays.length == 0) {
                alert("请先绘制区域");
                return;
            }
            for (var i = 0; i < drawOverlays.length; i++) {
                var layer = drawOverlays[i];
                var type = drawOverlays[i].oQ;
                try {
                    for (var j = 0; j < markerData.length; j++) {

                        var emsBean = markerData[j];
                        var bdPoint = gpsBdUtils.gcj2bd(emsBean.latitude, emsBean.longitude);
                        var point = new BMap.Point(bdPoint.longitude, bdPoint.latitude);
//                        var point = new BMap.Point(emsBean.longitude, emsBean.latitude);
                        if (type == "Circle") {
                            if (BMapLib.GeoUtils.isPointInCircle(point, layer)) {
                                taskMarkers = taskMarkers + emsBean.id + ",";
                            }
                        } else {
                            if (BMapLib.GeoUtils.isPointInPolygon(point, layer)) {
                                taskMarkers = taskMarkers + emsBean.id + ",";
                            }
                        }
                    }
                }
                catch (e) {
                    alert(e);
                }
            }
            if (taskMarkers == "") {
                alert("请选择标识器点！");
                return;
            }
            $('#addTaskModal2').modal('show');
            clearAll();
        },

        //qingchu
        clearCallBack: function () {
            taskMarkers = "";
            clearAll();
        },

        //上传工单
        uploadTaskCallBack: function (patrolerId, description) {
            $.ajax({
                type: "get",
                url: $('#context').val() + "/task/task-Save.do",
                dataType: "json",
                cache: false,
                data: {
                    markers: taskMarkers,
                    patrolerId: patrolerId,
                    description: description
                },
                success: function (r) {
                    if (r.success) {//请求成功
                        alert(r.message);
                        Task.updateList();
                    }
                    else {
                        alert(r.message);
                    }
                },
                error: function (d) {//请求出错
                    alert(d.message);
                }
            });
        },

        initMap: function () {

            map = new BMap.Map("container");          // 创建地图实例
            var menu = new BMap.ContextMenu();
            var txtMenuItem = [
                {
                    text: '删除',
                    callback: function (e) {
                        //判断右键位置是否在覆盖物内，如果在就展示菜单，如果不在就不展示
                        for (var i = 0; i < drawOverlays.length; i++) {
                            var layer = drawOverlays[i];
                            if (BMapLib.GeoUtils.isPointInPolygon(e, layer) || BMapLib.GeoUtils.isPointInRect(e, layer)||BMapLib.GeoUtils.isPointInCircle(e, layer)) {
                                map.removeOverlay(drawOverlays[i]);
                                drawOverlays.splice(i, 1);
                            }
                        }
                    }
                }
            ];
            for (var i = 0; i < txtMenuItem.length; i++) {
                menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
            }
            map.addContextMenu(menu);

            var myFun = function (result) {//根据城市名进行定位
                var cityName = result.name;
                map.setCenter(cityName);
            }

            //声明全局变量
            if (!markerData || markerData == 0) {
                markerData = [];
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


            var offset4 = new BMap.Size(10, 10);
            var emsEditControl = new EmsEditControl(map, offset4);
            map.addControl(emsEditControl);

            //绘制区域创建工单按钮
            var addTaskImage = $('#context').val() + "/images/tasks/multi-tasks.png";
            var offset2 = new BMap.Size(10, 100);
            var taskAddControl = new EmsControl(map, addTaskImage, Task.selectMarkerCallBack, offset2, "选择多点创建工单");
            map.addControl(taskAddControl);

            //绘制清除绘制按钮
            var clearImage = $('#context').val() + "/images/tasks/clear.png";
            var offset3 = new BMap.Size(10, 170);
            var clearControl = new EmsControl(map, clearImage, Task.clearCallBack, offset3, "清空选择");
            map.addControl(clearControl);

            //添加绘制工具

            var overlaycomplete = function (e) {
                e.overlay.enableEditing();
                drawOverlays.push(e.overlay);
                drawingManager.close();
            };
            //实例化鼠标绘制工具
            var drawingManager = new BMapLib.DrawingManager(map, {
                isOpen: false, //是否开启绘制模式

                enableDrawingTool: true, //是否显示工具栏
                drawingToolOptions: {
                    anchor: BMAP_ANCHOR_TOP_LEFT, //位置
                    offset: new BMap.Size(5, 5), //偏离值
                    drawingModes: [
                        BMAP_DRAWING_CIRCLE,
                        BMAP_DRAWING_POLYGON,
                        BMAP_DRAWING_RECTANGLE
                    ]
                }
            });
            //添加鼠标绘制工具监听事件，用于获取绘制结果
            drawingManager.addEventListener('overlaycomplete', overlaycomplete);

//加载完毕请求标识点信息
            map.addEventListener("tilesloaded", requestMarkers);
        },
        initList: function () {
            taskLists = new List('worksheets', options);
            Task.updateList();
            //$('.sort').click();
            $('#btnSort').trigger("click");
            $('#btnSort').trigger("click");

            $('#btnSort').css("padding", 0);
            $('#btnSort').css("padding-left", 10);
            $('#btnSort').css("padding-right", 15);
            $('#btnSort').css("margin-bottom", 10);
            Task.bindClickEvent();
            $('#selectPatroler3').live('change', function (e) {//下拉列表更改的时候查询数据，更新列表，待优化为查询条件在后台利用
                Task.updateList($("#selectPatroler3").val());
            });
        },
        bindClickEvent: function () {
            $('#worksheets ul li').live('click', function (e) {
                viewPoints = [];
                if (null == this.getElementsByClassName("id")[0]) {
                    return;
                }
                clearAllTaskPoint();
                clearAllEventPoint();
                var taskId = this.getElementsByClassName("id")[0].innerHTML;
                var i = 0;
                //请求任务
                $.ajax({
                    type: "get",
                    url: $('#context').val() + "/task/get-task-markers.do",
                    dataType: "json",
                    async:false,
                    cache: false,
                    data: {
                        taskId: taskId
                    },
                    success: function (r) {
                        if (r.success) {//请求成功
                            for (var data in r.data) {
                                var id = r.data[data].id;
                                var markerName = r.data[data].markerIdReal;
                                var latitude = r.data[data].latitude;
                                var longitude = r.data[data].longitude;
                                var finishTime = r.data[data].finishTime == null ? "" : r.data[data].finishTime;
                                var description = r.data[data].description == null ? "" : r.data[data].description;
                                var status = r.data[data].isChecked;
                                var icon = "";
                                var isChecked = "";
                                //alert(latitude+":"+longitude);
                                if (status == 0) {
                                    icon = $('#context').val() + "/images/ems/marker_0.png";
                                    isChecked = "未巡检";
                                }
                                else {
                                    icon = $('#context').val() + "/images/ems/marker_1.png";
                                    isChecked = "已巡检";
                                }

                                var taskDetailBean = new TaskDetailBean(id, icon, markerName, latitude, longitude, isChecked, finishTime, description);
                                taskDetailLists.push(taskDetailBean);

                            }
                            Task.addTaskDetailMarker(taskDetailLists);
                        }
                        else {
                            alert(r.message);
                        }
                    },
                    error: function (d) {//请求出错
                        alert(d.message);
                    }
                });

                //请求该任务上报的事件
                $.ajax({
                    type: "get",
                    url: $('#context').val() + "/event/get-event-list.do",
                    dataType: "json",
                    cache: false,
                    async:false,
                    data: {
                        taskId: taskId
                    },
                    success: function (r) {
                        if (r.success) {//请求成功
                            for (var data in r.data) {
                                var id = r.data[data].id;
                                var imageName = r.data[data].imageName;
                                var latitude = r.data[data].latitude;
                                var longitude = r.data[data].longitude;
                                var createTime = r.data[data].createTime == null ? "" : r.data[data].createTime;
                                var description = r.data[data].description == null ? "" : r.data[data].description;
                                var status = r.data[data].status;
                                var eventBean = new EventBean(id, imageName, latitude, longitude, description, status, createTime);

                                eventBeanList.push(eventBean);

                            }
                            Task.addEventMarker(eventBeanList);
                        }
                        else {
                            alert(r.message);
                        }
                    },
                    error: function (d) {//请求出错
                        alert(d.message);
                    }
                });

                map.setViewport(viewPoints);

            });

        },

        /*
         * 增加报警信息到列表
         * */
        updateList: function (patroler) {
            getTaskInfo(patroler);
        },

        initForm: function () {
            $("#saveTask2").click(function (e) {
                //alert(taskMarkers);
                var patrolerId = $("#selectPatroler2").val();
                var description = $("#description2").val();
                Task.uploadTaskCallBack(patrolerId, description);
                $('#addTaskModal2').modal('hide');
                $("#description2").val("");
            });
        },
        //获取巡检员列表
        initPatrolerArray: function () {

            $.ajax({
                type: "POST",
                url: $('#context').val() + "/user/get-patroler.do",
                data: {},
                dataType: "json",
                success: function (r) {
                    //获取面的列表，将下拉列表初始化
                    if (r.data) {
                        for (var user in r.data) {
                            //alert(r.data[user].id+":"+r.data[user].userName);
                            $("#selectPatroler").append(" <option value=" + r.data[user].id + ">" + r.data[user].userName + "</option>");
                            $("#selectPatroler2").append(" <option value=" + r.data[user].id + ">" + r.data[user].userName + "</option>");
                            $("#selectPatroler3").append(" <option value=" + r.data[user].userName + ">" + r.data[user].userName + "</option>");
                        }
                        $("#selectPatroler").chosen();
                        $("#selectPatroler2").chosen();
                        $("#selectPatroler3").chosen();
                    }
                },
                error: function (request) {
                    //提示错误信息
                    alert(r.message);
                }
            });

        },
        initSelect: function () {
            Task.initPatrolerArray();
        },
        addMarker: function (data) {
            if (document.createElement('canvas').getContext) {  // 判断当前浏览器是否支持绘制海量点
                var points = [];  // 添加海量点数据
                for (var i = 0; i < data.length; i++) {
                    var bdPoint = gpsBdUtils.gcj2bd(data[i].latitude, data[i].longitude);
                    var point = new BMap.Point(bdPoint.longitude, bdPoint.latitude);
                    point.Tag = data[i];
                    if(data[i].recordType!=2)  continue;//只显示阴保桩
                    if(!data[i].protectivePotential) {
                        if(!points["无法检测"])  points["无法检测"] = [];
                        points["无法检测"].push(point);
                    }
                    else if(data[i].protectivePotential >1.2){
                        if(!points["过保护"])  points["过保护"] = [];
                        points["过保护"].push(point);
                    }
                    else if(data[i].protectivePotential >=0.85){
                        if(!points["合格"])  points["合格"] = [];
                        points["合格"].push(point);
                    }
                    else if(data[i].protectivePotential >0){
                        if(!points["不合格"])  points["不合格"] = [];
                        points["不合格"].push(point);
                    }
                }
                pointCollection(map,points["合格"],"合格");
                pointCollection(map,points["不合格"],"不合格");
                pointCollection(map,points["过保护"],"过保护");
                pointCollection(map,points["无法检测"],"无法检测");

            } else {
                alert('请在chrome、safari、IE8+以上浏览器查看本示例');
            }
        },
        //增加事件图片的marker
        addEventMarker: function () {
            for (var i = 0; i < eventBeanList.length; i++) {
                var bdPoint = gpsBdUtils.gcj2bd(eventBeanList[i].latitude, eventBeanList[i].longitude);
                var point = new BMap.Point(bdPoint.longitude, bdPoint.latitude);
                var iconImage = $('#context').val() + "/images/tasks/blue_icon.png";
                var myIcon = new BMap.Icon(iconImage, new BMap.Size(20, 50));//图片marker的ICON
                var msg = "事件描述:" + eventBeanList[i].description + "\r\n" +
                    "上传时间:" + eventBeanList[i].createTime + "\r\n";
                var marker = new BMap.Marker(point, {icon: myIcon, title: msg});  // 创建标注
                marker.Tag = eventBeanList[i];
                viewPoints.push(point);
                marker.addEventListener("click", showEventInfo);
                map.addOverlay(marker);              // 将标注添加到地图中
                drawEventOverlays.push(marker);

            }
        },
        addTaskDetailMarker: function (data) {
            for (var i = 0; i < data.length; i++) {
                var bdPoint = gpsBdUtils.gcj2bd(data[i].latitude, data[i].longitude);
                var point = new BMap.Point(bdPoint.longitude, bdPoint.latitude);
                var myIcon = new BMap.Icon(data[i].icon, new BMap.Size(20, 50));
                var msg = "标识器名称:" + data[i].markerName + "\r\n" +
                    "巡检状态:" + data[i].isChecked + "\r\n" +
                    "巡检时间:" + data[i].finishTime + "\r\n" +
                    "描述:" + data[i].description + "\r\n";
                var marker = new BMap.Marker(point, {icon: myIcon, title: msg});  // 创建标注

                viewPoints.push(point);
                drawTaskOverlays.push(marker);
                map.addOverlay(marker);              // 将标注添加到地图中
            }
        }

    };
}();

TaskBean = function (id, icon, patrolerName, description, deployDate, beginDate, endDate, status, creatorName) {
    this.id = id;
    this.image = icon;
    this.patrolerName = patrolerName;
    this.description = description;
    this.deployDate = deployDate;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.status = status;
    this.creatorName = creatorName;

};

TaskDetailBean = function (id, icon, markerName, latitude, longitude, isChecked, finishTime, description) {
    this.id = id;
    this.icon = icon;
    this.markerName = markerName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.isChecked = isChecked;
    this.finishTime = finishTime;
    this.description = description;
};

EventBean = function (id, imageName, latitude, longitude, description, status, createTime) {
    this.id = id;
    this.imageName = imageName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.description = description;
    this.status = status;
    this.createTime = createTime;

}