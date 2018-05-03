package com.wutong.backmanage.core.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.wutong.backmanage.core.util.ToolUtil;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	public void initOther() {

		groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
		groupTemplate.registerFunctionPackage("tool", new ToolUtil());

	}

}
