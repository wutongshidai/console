package com.wutong.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.parasol.core.adPositionId.AdPositionId;
import com.parasol.core.announcement.Announcement;
import com.wutong.backmanage.common.file.OSSObjectUtils;
import com.parasol.core.service.AdPositionIdService;
import com.wutong.backmanage.core.exception.BizExceptionEnum;
import com.wutong.backmanage.core.exception.BussinessException;
import com.wutong.backmanage.core.log.LogObjectHolder;
import com.wutong.backmanage.core.page.PageInfoBT;
import com.wutong.backmanage.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/business")
public class adPositionIdController {

    private static final String PREFIX = "/business/adPositionId/";

    @Reference
    AdPositionIdService adPositionIdService;

    @GetMapping("/adPositionId")
    public String index() {
        return PREFIX + "adPositionId.html";
    }

    @PostMapping("/adPositionId/list")
    @ResponseBody
    public PageInfoBT getlist(@RequestParam(name = "offset", defaultValue = "1") int offset,Integer adId, String levels,   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        int pageNo = 1;
        if (offset != 0) {
            pageNo = offset / pageSize + 1;
        }
        PageInfo<AdPositionId> pageInfo = adPositionIdService.list(adId,levels,pageNo,pageSize);
        PageInfoBT pageInfoBT = new PageInfoBT(pageInfo.getList());
        pageInfoBT.setTotal(pageInfo.getTotal());
        pageInfoBT.setPageNumber(pageInfo.getPageNum());
        pageInfoBT.setPageSize(pageInfo.getPageSize());
        return pageInfoBT;
    }

    @PostMapping("/adPositionId/addAd")
    @ResponseBody
    public Map addAd(AdPositionId adPositionId) {
        Map map = adPositionIdService.addAd(adPositionId);
        return map;
    }

    @GetMapping("/adPositionId/openAdd")
    public String openAdd() {
        return PREFIX + "add.html";
    }

    @RequestMapping(path = "/adPositionId/uploadFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, String> uploadFile(@RequestParam("ggwFile")MultipartFile ggwFile) {
        Map<String, String> result = new HashMap<>();
        String fileName = OSSObjectUtils.uploadMultipartFile(ggwFile, "", "wut-ggw");
        if (fileName != null) {
            result.put("state", "SUCCESS");
            result.put("url", fileName);
        } else {
            result.put("state", "ERROR");
        }
        return result;
    }
    @RequestMapping("/adPositionId/ad_edit/{adId}")
    public String adUpdate(@PathVariable Integer adId, Model model) {
        PageInfo<AdPositionId> list = adPositionIdService.list(adId, null, null, null);
        AdPositionId adPositionId = list.getList().get(0);
        model.addAttribute("adPositionId",adPositionId);
        LogObjectHolder.me().set(adPositionId);
        return PREFIX + "ad_edit.html";
    }

    @PostMapping("/adPositionId/upAd")
    @ResponseBody
    public Boolean upAd(AdPositionId adPositionId) {
        boolean b = adPositionIdService.upAd(adPositionId);
        return b;
    }
}
