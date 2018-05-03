package com.wutong.backmanage.core.factory;

import com.wutong.backmanage.common.constant.dictmap.base.AbstractDictMap;
import com.wutong.backmanage.common.constant.dictmap.base.SystemDict;
import com.wutong.backmanage.core.exception.BizExceptionEnum;
import com.wutong.backmanage.core.exception.BussinessException;

/**
 * 字典的创建工厂
 *
 */
public class DictMapFactory {

    private static final String basePath = "com.wutong.backmanage.common.constant.dictmap.";

    /**
     * 通过类名创建具体的字典类
     */
    public static AbstractDictMap createDictMap(String className) {
        if("SystemDict".equals(className)){
            return new SystemDict();
        }else{
            try {
                Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(basePath + className);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.ERROR_CREATE_DICT);
            }
        }
    }
}
