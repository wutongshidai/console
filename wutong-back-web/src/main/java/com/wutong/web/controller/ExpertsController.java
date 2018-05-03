package com.wutong.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.parasol.core.experts.Expertindex;
import com.parasol.core.service.ExpertsService;

@Controller
@RequestMapping("/business")
public class ExpertsController {

	private String PREFIX = "/business/experts/";
	
	@Reference
	private ExpertsService expertsService;
	
	@RequestMapping("/experts")
	public String index() {
		return PREFIX + "experts.html";
	}
	
	@RequestMapping("/experts/list")
	@ResponseBody
	public List<Expertindex> list(String expertName, Model model) {
		List<Expertindex> experts = expertsService.selectExpertindex();
		return experts;
	}
}
