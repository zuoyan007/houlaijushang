package com.lamb.risk.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.risk.entity.RiskPoint;

/**
 * risk_pointDAO接口
 * @author 狄张杰
 * @version 2020-11-30
 */
@MyBatisDao
public interface RiskPointDao extends CrudDao<RiskPoint> {
	
}