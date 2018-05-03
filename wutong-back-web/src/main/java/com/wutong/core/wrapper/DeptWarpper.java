package com.wutong.core.wrapper;


import java.util.Map;

import com.wutong.backmanage.core.factory.ConstantFactory;
import com.wutong.backmanage.core.util.ToolUtil;
import com.wutong.backmanage.core.wrapper.BaseControllerWarpper;

/**
 * 部门列表的包装
 *
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
