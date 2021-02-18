package com.lamb.util.sys;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.sys.entity.EmpUser;
import com.lamb.util.UserKit;

import java.util.List;

/**
 * 基础的实体类，所有实体集成此实体
 * @author gexu
 * @date 2020/3/5 20:44
 */
@Table(columns = {
   }
)
public class SheBaseEntity<T extends SheBaseEntity<?>> extends DataEntity<T> {

    /**
     * 需要保存/删除的 附件 ids
     */
    public String[] saveFileIds;
    public String[] deleteFileIds;

    /**
     * 需要保存/删除的 签名 ids
     */
    public String[] saveAutographIds;
    public String[] deleteAutographIds;

    /**
     * 回显是的文件list。一般用于view展示 时
     */


    /**
     * 当前用户
     */
    private EmpUser curUser;


    public SheBaseEntity() {
    }

    public SheBaseEntity(String id) {
        super(id);
    }
    @Override
    public void preInsert() {
        super.preInsert();
        super.createBy = UserKit.getCreateBy(super.createBy);
        super.updateBy = UserKit.getCreateBy(super.updateBy);
    }

    @Override
    public void preUpdate(){
        super.preUpdate();
        super.updateBy = UserKit.getCreateBy(super.updateBy);
    }


    /**
     * 将path转换为url
     * @return
     */



    public String[] getSaveFileIds() {
        return saveFileIds;
    }

    public void setSaveFileIds(String[] saveFileIds) {
        this.saveFileIds = saveFileIds;
    }

    public String[] getDeleteFileIds() {
        return deleteFileIds;
    }

    public void setDeleteFileIds(String[] deleteFileIds) {
        this.deleteFileIds = deleteFileIds;
    }

    public String[] getSaveAutographIds() {
        return saveAutographIds;
    }

    public void setSaveAutographIds(String[] saveAutographIds) {
        this.saveAutographIds = saveAutographIds;
    }

    public String[] getDeleteAutographIds() {
        return deleteAutographIds;
    }

    public void setDeleteAutographIds(String[] deleteAutographIds) {
        this.deleteAutographIds = deleteAutographIds;
    }


    public EmpUser getCurUser() {
        return curUser;
    }

    public void setCurUser(EmpUser curUser) {
        this.curUser = curUser;
    }

}
