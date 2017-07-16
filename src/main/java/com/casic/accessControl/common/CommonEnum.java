package com.casic.accessControl.common;

/**
 * Created by lenovo on 2016/5/17.
 */
public class CommonEnum {
    /**
     * 组织结构类型，区分点线片
     */
    public enum FEATURE_TYPE {
        AREA("区域", 1), LINE("线路", 2);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private FEATURE_TYPE(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // 覆盖方法
        @Override
        public String toString() {
            return this.index + "_" + this.name;
        }
    }

    /**
     * 用户类型，管理员Or普通用户
     */
    public enum ROLE_TYPE {
        ADMIN("管理员", 0), COMMON("普通用户", 1);
        private String name;
        private int type;

        private ROLE_TYPE(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
    public enum OBJECT_TYPE{//四种管线类型
        PIPE("管线",1),PIPE_APPENDAGES("管线附属物",2),PIPE_FEATURE("管线特征点",3),CROSS_POINT("交叉穿越点",4);
        private String name;
        private int type;
        private OBJECT_TYPE(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
