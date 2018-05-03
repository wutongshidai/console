package com.wutong.backmanage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wutong.backmanage.dao.DeptMapper;
import com.wutong.backmanage.pojo.Dept;
import com.wutong.backmanage.service.IDeptService;

import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements IDeptService {

    @Resource
    DeptMapper deptMapper;

    @Override
    public void deleteDept(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        Example example = new Example(Dept.class);
        example.createCriteria().andLike("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectByExample(example);
        for (Dept temp : subDepts) {
            deptMapper.deleteByPrimaryKey(temp);
        }
        deptMapper.deleteByPrimaryKey(deptId);
    }
}
