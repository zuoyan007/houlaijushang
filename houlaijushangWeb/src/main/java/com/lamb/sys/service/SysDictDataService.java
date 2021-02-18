/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sys.entity.DictType;
import com.lamb.sys.dao.SysDictDataDao;
import com.lamb.sys.entity.SysDictData;
import com.lamb.sys.entity.SysDictDataVO;
import com.lamb.util.sys.BizException;
import com.lamb.util.sys.PageKit;
import com.lamb.util.sys.StringKit;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据表Service
 * @author liuwei
 * @version 2019-10-30
 */
@Service
@Transactional(readOnly=true)
public class SysDictDataService extends CrudService<SysDictDataDao, SysDictData> {


    /**
     * 查询分页数据
     * @param sysDeviceRoster 查询条件
     * @param sysDeviceRoster.page 分页对象
     * @return
     */
    @Override
    public Page<SysDictData> findPage(SysDictData sysDictData) {
        return super.findPage(sysDictData);
    }


    /**
     * 查询 树状数据
     * @return
     */
    public List<SysDictDataVO> treeList(String dictType,SysDictData sysDictData) {
        List<SysDictData> list = Lists.newArrayList();
        list = this.dao.findList(sysDictData);
        int limit = 300;
        if(list.size()>limit){
            throw new BizException("msg:超过"+limit+"条数据应使用分页加载");
        }
        return SysDictDataVO.convertTree(list);
    }





    /**
     * 查询分页数据
     * @return
     */
    public Page<SysDictDataVO> findPageSyncList(SysDictData sysDictData) {
        List<SysDictData> list = this.dao.syncList(sysDictData);
        List<SysDictDataVO> beanMapList =  new ArrayList<>();
        for(SysDictData entity:list){
            SysDictDataVO dictDataVO = new SysDictDataVO();
            BeanUtils.copyProperties(entity,dictDataVO);
            if(StringKit.isNotBlank(sysDictData.getDictType())){
                dictDataVO.setDictType(null);
            }
            beanMapList.add(dictDataVO);
        }
        Page<SysDictDataVO> p= new Page<SysDictDataVO>().setList(beanMapList);
        return PageKit.copy(sysDictData.getPage(),p);
    }

    /**
     * 查询分页数据
     * @return
     */
    public Page<DictType> findPageSyncType(DictType dictType) {
        List<DictType> list = this.dao.syncType(dictType);
        dictType.getPage().setList(list);
        return dictType.getPage();
    }


    public List<SysDictData> checkPointCodeByIndustryCode(SysDictData sysDictData, String useType) {
        return this.dao.checkPointCodeByIndustryCode(sysDictData,useType);
    }

    public List<SysDictData> findSonDictData(SysDictData dictData) {
        return this.dao.checkSonDictData(dictData);
    }
}