package com.wutong.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.parasol.core.bid.BidPublicity;
import com.parasol.core.service.BidPublicityService;
import com.wutong.backmanage.common.file.OSSObjectUtils;
import com.wutong.backmanage.config.properties.WutongBackmanageProperties;
import com.wutong.backmanage.core.page.PageInfoBT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/business")
public class BidPublicityController {

    private static final String PREFIX = "/business/publicity/";

    @Autowired
    private WutongBackmanageProperties wutongBackmanageProperties;

    @Reference
    public BidPublicityService bidPublicityService;

    @GetMapping("/publicity")
    public String index() {
        return PREFIX + "index.html";
    }

    @RequestMapping(path = "/publicity/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public PageInfoBT list(@RequestParam(name = "industry", defaultValue = "-1") int industry, @RequestParam(name = "publicityStatus", defaultValue = "-1") int publicityStatus, @RequestParam(name = "offset", defaultValue = "1") int offset, @RequestParam(name = "limit", defaultValue = "10") int pageSize) {
        int pageNumber = 1;
        if (offset != 0) {
            pageNumber = offset / pageSize + 1;
        }
        PageInfo<BidPublicity> pageInfo = bidPublicityService.list(industry, publicityStatus, pageNumber, pageSize);
        PageInfoBT pageInfoBT = new PageInfoBT(pageInfo.getList());
        pageInfoBT.setTotal(pageInfo.getTotal());
        pageInfoBT.setPageNumber(pageInfo.getPageNum());
        pageInfoBT.setPageSize(pageInfo.getPageSize());
        return pageInfoBT;
    }

    @PostMapping(path = "/publicity/update")
    @ResponseBody
    public Object update(int id, String projectCode, String projectName, int industry, String companyName, String firstCandidate, String secondCandidate, String thirdCandidate, String winCompany, String amount, java.util.Date winDate, String attachment, int publicityStatus ,String owner) {
        BidPublicity bidPublicity = bidPublicityService.findById(id);

        bidPublicity.setAmount(Double.valueOf(amount));
        bidPublicity.setAttachment(attachment);
        bidPublicity.setCompanyName(companyName);
        bidPublicity.setFirstCandidate(firstCandidate);
        bidPublicity.setSecondCandidate(secondCandidate);
        bidPublicity.setThirdCandidate(thirdCandidate);
        bidPublicity.setIndustry(industry);
        bidPublicity.setProjectCode(projectCode);
        bidPublicity.setProjectName(projectName);
        bidPublicity.setWinDate(winDate);
        bidPublicity.setOwner(owner);
        bidPublicity.setWinCompany(winCompany);
        boolean result = bidPublicityService.update(bidPublicity);
        return result ? "SUCCESS" : "ERROR";
    }

    @PostMapping("/publicity/upstatus")
    @ResponseBody
    public Object updateStatus(int id, int status) {
        BidPublicity bidPublicity = bidPublicityService.findById(id);
        bidPublicity.setPublicityStatus(status);
        boolean result = bidPublicityService.update(bidPublicity);
        return result ? "SUCCESS" : "ERROR";
    }

    @PostMapping("/publicity/delete")
    @ResponseBody
    public Object delete(int id) {
        boolean result = bidPublicityService.delete(id);
        return "SUCCESS";
    }

    @GetMapping("/publicity/openAdd")
    public String openAdd() {
        return PREFIX + "add.html";
    }

    @PostMapping("/publicity/uploadFile")
    @ResponseBody
    public Map<String, String> uploadFile(@RequestParam("attachmentFile")MultipartFile attachment) {
        Map<String, String> result = new HashMap<>();
        String fileName = OSSObjectUtils.uploadMultipartFile(attachment, "", "wut-publicity");
        if (fileName != null) {
            result.put("result", "SUCCESS");
            result.put("fileName", fileName);
        } else {
            result.put("result", "ERROR");
        }
        return result;
    }

    @RequestMapping("/publicity/add")
    @ResponseBody
    public String add(String projectCode, String projectName, int industry, String companyName, String firstCandidate, String secondCandidate, String thirdCandidate, String winCompany, String amount, java.util.Date winDate, String attachment ,String owner) {
        BidPublicity bidPublicity = new BidPublicity();
        bidPublicity.setAmount(Double.valueOf(amount));
        bidPublicity.setAttachment(attachment);
        bidPublicity.setCompanyName(companyName);
        bidPublicity.setFirstCandidate(firstCandidate);
        bidPublicity.setSecondCandidate(secondCandidate);
        bidPublicity.setThirdCandidate(thirdCandidate);
        bidPublicity.setIndustry(industry);
        bidPublicity.setProjectCode(projectCode);
        bidPublicity.setProjectName(projectName);
        bidPublicity.setWinDate(winDate);
        bidPublicity.setOwner(owner);
        bidPublicity.setWinCompany(winCompany);
        int id = bidPublicityService.create(bidPublicity);
        if (id > 0) {
            return "SUCCESS";
        }
        return "ERROR";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest webRequest) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping(path = "/publicity/openChange")
    public String openChange(int id, Model model) {
        BidPublicity bidPublicity = bidPublicityService.findById(id);
        model.addAttribute(bidPublicity);
        return PREFIX + "edit.html";
    }
}
