package com.wutong.backmanage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wutong.backmanage.core.util.Convert;
import com.wutong.backmanage.dao.RelationMapper;
import com.wutong.backmanage.dao.RoleMapper;
import com.wutong.backmanage.pojo.Relation;
import com.wutong.backmanage.service.IRoleService;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteByPrimaryKey(roleId);

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
    }

}
