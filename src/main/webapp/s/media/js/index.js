var Index = function () {

    var onCheck = function (e, treeId, treeNode) {

        if(treeNode.level==0){

            for(var i=0;i<treeNode.children.length;i++){

                var layer = Gis.getGlobalControl().Globe.Layers.getLayerByCaption(treeNode.children[i].name);

                if (layer) {

                    if (treeNode.checked) {

                        layer.Visible = true;

                    } else {

                        layer.Visible = false;

                    }

                }

            }

        }else{

            var layer = Gis.getGlobalControl().Globe.Layers.getLayerByCaption(treeNode.name);

            if (layer) {

                if (treeNode.checked) {

                    layer.Visible = true;

                } else {

                    layer.Visible = false;

                }

            }

        }

    }

    return {

        init : function(){

        },

        initGisToolbar: function () {

            $('#btnNavgate').bind('click',function(e){
               Gis.setAction(0);
            });

            $("#btnDistance").bind('click', function (e) {
                Gis.setAction(2);
            });

            $('#btnArea').bind('click', function (e) {
                Gis.setAction(3);
            });

            $('#btnHeigh').bind('click', function (e) {
                Gis.setAction(4);
            })

            $('#btnSelect').bind('click', function (e) {
                Gis.setAction(5);
            });

            $('#btnMove').bind('click',function(e){
                Gis.getGlobalControl().Globe.MouseRoamingEnable =  false;
                Gis.setAction(6);
            });

            $('#btnZoom').bind('click',function(e){
                Gis.getGlobalControl().Globe.MouseRoamingEnable =  false;
                Gis.setAction(7);
            })

            $('#btnClear').bind('click', function (e) {
                Gis.getGlobalControl().Globe.MouseRoamingEnable =  true;
                Gis.ClearMeasure();
            });

            $('#btnAddFlag').bind('click',function(e){
               Gis.addTextFlag();
            });

            $('#btnDelFlag').bind('click',function(e){
                Gis.delTextFlag();
            })

        },

        initLayerTree: function () {

            var root = {name: '图层管理', checked: true, open: true, children: []};

            var layers = Gis.getGlobalControl().Globe.Layers;

            for (var i = 0; i < layers.count; i++) {

                var child = {name: layers.Item(i).Caption, checked: true};

                root.children.push(child);

            }

            var treeData = [root];

            $.fn.zTree.init($("#layer_tree"), {

                callback: {

                    onCheck: onCheck

                }, check: {

                    enable: true,

                    chkboxType: {"Y": "s", "N": "s"}

                }

            }, treeData);

        }

    };

}();