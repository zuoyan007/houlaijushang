/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.EmpUser;
import com.lamb.sys.entity.SysLoginLog;

import java.util.Date;

/**
 * 登录日志DAO接口
 * @author gexu
 * @version 2019-10-17
 */
@MyBatisDao
public interface SysLoginLogDao extends CrudDao<SysLoginLog> {

    void calcDataDD(Date beginDate, Date endDate);
    void deleteDataDD(Date beginDate, Date endDate);


    void calcLoginLogDD(Date beginDate, Date endDate);
    void deleteLoginLogDD(Date beginDate, Date endDate);
    void calcLoginLogHH(Date beginDate, Date endDate);
    void deleteLoginLogHH(Date beginDate, Date endDate);
    void calcLogHH(Date beginDate, Date endDate);
    void deleteLogHH(Date beginDate, Date endDate);


    public EmpUser getCustomerUser(String userType,String userCode);

}