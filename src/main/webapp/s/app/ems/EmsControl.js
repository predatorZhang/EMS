function EmsControl(map, imgUrl, callback, offset,alt) {
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = offset;
    //TODO LIST：判断是否支持百度Map控件
    this.map = map;
    this.imgUrl = imgUrl;
    this.onClickCallback = callback;
    this.alt = alt;
}
EmsControl.prototype = new BMap.Control();
EmsControl.prototype.initialize = function (map) {
    //创建一个dom对象
    var div = document.createElement("div");
    var p = document.createElement('p');//创建一个p标签
    var imgContent = '<img src="' + this.imgUrl + '"' + ' width="50" height="54" />';
    p.innerHTML = imgContent;//loading图片
    div.style.cursor = "pointer";
    div.appendChild(p);//添加p到div中
    div.onclick = this.onClickCallback;
    var childDiv = document.createElement("div");
    childDiv.innerHTML =this.alt;
    childDiv.setAttribute("hidden","hidden");
    div.appendChild(childDiv);
    div.onmouseover = function () {
         $(childDiv).css("display","block");
    };

    $(childDiv).css({
        position:"relative",
        left:"40px",
        bottom:"60px",
        color:"#330000",
        backgroundColor:"#CCFFFF",
        display: "none"
    });
    div.onmouseout = function () {
//        childDiv.removeAttribute("hidden");
        $(childDiv).css("display","none");
    };

    this.map.getContainer().appendChild(div);
    return div;
};