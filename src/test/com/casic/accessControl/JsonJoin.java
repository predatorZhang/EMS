package com.casic.accessControl;

/**
 * Created by lenovo on 2017/3/30.
 */
public class JsonJoin {
    public static void main(String [] args){
        String s = "管线种类 管线直径 管线材质 埋设方式 管线埋深 所属区域 所属线路 所属道路 建设年代 权属单位 标示对象ID 标识器ID 标识器类型 标识器埋深 安装部门 经度   纬度  最后修改时间 备注 下层管种类  下层管埋深 下层管直径  下层管材料  标识对象类别  测试桩名称 X Y 保护电位 开路电位 发射电流 参比电极校准";
        System.out.println(s.replaceAll(" ","  "));
    }
}
