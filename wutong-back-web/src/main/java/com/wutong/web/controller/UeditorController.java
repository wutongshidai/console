package com.wutong.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.wutong.backmanage.common.ueditor.UeditorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/ueditor")
public class UeditorController implements ServletConfigAware, ServletContextAware {

    @Autowired
    private UeditorProperties ueditorProperties;

    @RequestMapping(path = "/invoke")
    @ResponseBody
    public Object invoke(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding( "utf-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Type" , "text/html");

        String rootPath = ueditorProperties.getRootPath();
        return JSONObject.parse(new ActionEnter( request, rootPath, ueditorProperties.getPath() ).exec());
//        return JSONObject.stringToValue(new ActionEnter( request, rootPath, ueditorProperties.getPath() ).exec());
    }

    private ServletContext servletContext;

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletContext = servletContext;
    }

    private ServletConfig servletConfig;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletConfig = servletConfig;
    }
}
