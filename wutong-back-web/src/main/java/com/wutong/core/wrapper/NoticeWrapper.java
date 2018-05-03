package com.wutong.core.wrapper;


import java.util.Map;

import com.wutong.backmanage.core.factory.ConstantFactory;
import com.wutong.backmanage.core.wrapper.BaseControllerWarpper;

/**
 * 部门列表的包装
 *
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer creater = (Integer) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
