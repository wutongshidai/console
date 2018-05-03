package com.wutong.backmanage.core.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wutong.backmanage.config.properties.WutongBackmanageProperties;

public class SwaggerConfiguration extends WebMvcConfigurerAdapter {
	
	 @Autowired
	    WutongBackmanageProperties wutongBackmanageProperties;

	    /**
	     * 增加swagger的支持
	     */
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        if(wutongBackmanageProperties.getSwaggerOpen()){
	            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	        }
	    }

}
