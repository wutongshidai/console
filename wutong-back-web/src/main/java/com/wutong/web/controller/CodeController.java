package com.wutong.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wutong.backmanage.common.annotation.Permission;
import com.wutong.backmanage.common.constant.Const;
import com.wutong.backmanage.core.controller.BaseController;
import com.wutong.backmanage.core.exception.BizExceptionEnum;
import com.wutong.backmanage.core.exception.BussinessException;
import com.wutong.backmanage.core.template.config.ContextConfig;
import com.wutong.backmanage.core.template.engine.SimpleTemplateEngine;
import com.wutong.backmanage.core.template.engine.base.TemplateEngine;
import com.wutong.backmanage.core.util.ToolUtil;

/**
 * 代码生成控制器
 *
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private String PREFIX = "/system/code/";

    /**
     * 跳转到代码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "code.html";
    }

    /**
     * 代码生成
     */
    @ApiOperation("生成代码")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object add(@ApiParam(value = "模块名称",required = true) @RequestParam  String moduleName,
                      @RequestParam String bizChName,
                      @RequestParam String bizEnName,
                      @RequestParam String path) {
        if (ToolUtil.isOneEmpty(bizChName, bizEnName)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName(bizChName);
        contextConfig.setBizEnName(bizEnName);
        contextConfig.setModuleName(moduleName);
        if (ToolUtil.isNotEmpty(path)) {
            contextConfig.setProjectPath(path);
        }

        TemplateEngine templateEngine = new SimpleTemplateEngine();
        templateEngine.setContextConfig(contextConfig);
        templateEngine.start();

        return super.SUCCESS_TIP;
    }
}

