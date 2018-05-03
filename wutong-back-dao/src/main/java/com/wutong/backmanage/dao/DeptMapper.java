package com.wutong.backmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.wutong.backmanage.node.ZTreeNode;
import com.wutong.backmanage.pojo.Dept;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 */
public interface DeptMapper extends Mapper<Dept> {
    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);
}