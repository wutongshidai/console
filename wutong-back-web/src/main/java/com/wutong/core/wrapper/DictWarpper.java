package com.wutong.core.wrapper;


import java.util.List;
import java.util.Map;

import com.wutong.backmanage.core.factory.ConstantFactory;
import com.wutong.backmanage.core.util.ToolUtil;
import com.wutong.backmanage.core.wrapper.BaseControllerWarpper;
import com.wutong.backmanage.pojo.Dict;

/**
 * 字典列表的包装
 *
 */
public class DictWarpper extends BaseControllerWarpper {

    public DictWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        Integer id = (Integer) map.get("id");
        List<Dict> dicts = ConstantFactory.me().findInDict(id);
        if(dicts != null){
            for (Dict dict : dicts) {
                detail.append(dict.getNum() + ":" +dict.getName() + ",");
            }
            map.put("detail", ToolUtil.removeSuffix(detail.toString(),","));
        }
    }

}
