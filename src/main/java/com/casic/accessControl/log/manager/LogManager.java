package com.casic.accessControl.log.manager;

import com.casic.accessControl.core.hibernate.HibernateBasicDao;
import com.casic.accessControl.log.domain.LogInfo;

import java.util.List;

/**
 * Created by lenovo on 2016/5/16.
 */
public class LogManager extends HibernateBasicDao{
    //操作日志增查

    /**
     * 根据查询操作日志分页列表
     * @param beginIndex
     * @param pageSize
     * @return
     */
    public List<LogInfo> pageQueryLogs(Integer beginIndex,Integer pageSize){
        return null;
    }

    public void saveLog(LogInfo logInfo){

    }

}
