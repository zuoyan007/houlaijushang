package com.lamb.risk.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.lamb.cons.Dict;
import com.lamb.risk.dao.RiskPointDao;
import com.lamb.risk.entity.RiskPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * risk_pointService
 * @author 狄张杰
 * @version 2020-11-30
 */
@Service
@Transactional(readOnly=true)
public class RiskPointService extends CrudService<RiskPointDao, RiskPoint> {

	@Autowired
	private FileUploadService fileUploadService;


	/**
	 * 获取单条数据
	 * @param riskPointId
	 * @return
	 */
	public RiskPoint view(String riskPointId) {
		RiskPoint riskPoint = super.dao.get(new RiskPoint(riskPointId));
		if(riskPoint!=null) {
			FileUpload selImage = new FileUpload();
			selImage.setBizKey(riskPointId);
			riskPoint.setImageList(fileUploadService.findList(selImage));
		}
		return riskPoint;
	}

	@Override
	public RiskPoint get(RiskPoint riskPoint) {
		return super.get(riskPoint);
	}
	
	/**
	 * 查询分页数据
	 * @param riskPoint 查询条件
	 * @param
	 * @return
	 */
	@Override
	public Page<RiskPoint> findPage(RiskPoint riskPoint) {
		return super.findPage(riskPoint);
	}

	/**
	 * 查询所有
	 * @param riskPoint
	 * @return
	 */
	@Override
	public  List<RiskPoint>  findList(RiskPoint riskPoint) {
		List<RiskPoint> list = super.findList(riskPoint);
		return list;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param riskPoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(RiskPoint riskPoint) {
		super.save(riskPoint);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(riskPoint.getId(), Dict.imgType.riskPoint.getName());
	}
	
	/**
	 * 更新状态
	 * @param riskPoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(RiskPoint riskPoint) {
		super.updateStatus(riskPoint);
	}
	
	/**
	 * 删除数据
	 * @param riskPoint
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(RiskPoint riskPoint) {
		super.delete(riskPoint);
	}
	
}