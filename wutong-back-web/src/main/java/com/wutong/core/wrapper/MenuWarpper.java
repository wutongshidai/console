package com.wutong.core.wrapper;

import java.util.List;
import java.util.Map;

import com.wutong.backmanage.common.constant.dictmap.states.IsMenu;
import com.wutong.backmanage.core.factory.ConstantFactory;
import com.wutong.backmanage.core.wrapper.BaseControllerWarpper;

/**
 * 菜单列表的包装类
 *
 */
public class MenuWarpper extends BaseControllerWarpper {

    public MenuWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }

}
