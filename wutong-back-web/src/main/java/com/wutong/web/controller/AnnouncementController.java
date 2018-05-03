package com.wutong.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.parasol.core.announcement.Announcement;
import com.parasol.core.service.AnnouncementService;
import com.wutong.backmanage.common.file.OSSObjectUtils;
import com.wutong.backmanage.core.page.PageInfoBT;
import com.wutong.backmanage.core.shiro.ShiroKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/business")
public class AnnouncementController {

    @Value("${wutong.oss.prefix}")
    private String ossPrefix;


    private static final String PREFIX = "/business/notice/";

    @Reference
    private AnnouncementService announcementService;

    @GetMapping("/notice")
    public String index() {
        return PREFIX + "index.html";
    }

    @PostMapping("/notice/list")
    @ResponseBody
    public PageInfoBT list(@RequestParam(name = "columnId", defaultValue = "-1") int columnId, @RequestParam(name = "status", defaultValue = "-1") int status,@RequestParam(name="roleId", defaultValue = "-1") int roleId, @RequestParam(name = "offset", defaultValue = "1") int offset, @RequestParam(name = "limit", defaultValue = "10") int pageSize) {
        int pageNumber = 1;
        if (offset != 0) {
            pageNumber = offset / pageSize + 1;
        }
        PageInfo<Announcement> pageInfo = announcementService.list(columnId, status, roleId, pageNumber, pageSize);
        PageInfoBT pageInfoBT = new PageInfoBT(pageInfo.getList());
        pageInfoBT.setTotal(pageInfo.getTotal());
        pageInfoBT.setPageNumber(pageInfo.getPageNum());
        pageInfoBT.setPageSize(pageInfo.getPageSize());
        return pageInfoBT;
    }


    @PostMapping("/notice/add")
    @ResponseBody
    public Object add(String title, String content, int columnId) {
        title = HtmlUtils.htmlUnescape(title);
        content = HtmlUtils.htmlUnescape(content.replaceAll("& ", "&"));
        Announcement announcement = new Announcement();
        announcement.setAuthorId(ShiroKit.getUser().getId());
        announcement.setColumnId(columnId);
        announcement.setTitle(title);
        announcement.setContent(content);
        int announcementId = announcementService.create(announcement);
        return "SUCCESS";
    }

    @PostMapping("/notice/upstatus")
    @ResponseBody
    public Object updateStatus(int announcementId, int status) {
        boolean result = announcementService.updateStatus(announcementId, status);
        return "SUCCESS";
    }

    @PostMapping("/notice/delete")
    @ResponseBody
    public Object delete(int announcementId) {
        boolean result = announcementService.delete(announcementId);
        return "SUCCESS";
    }

    @GetMapping("/notice/openAdd")
    public String openAdd() {
        return PREFIX + "ue_add.html";
    }


    @RequestMapping(path = "/notice/uploadFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, String> uploadFile(@RequestParam("upfile")MultipartFile upfile) {
        Map<String, String> result = new HashMap<>();
        String fileName = OSSObjectUtils.uploadMultipartFile(upfile, "", "wut-publicity");
        if (fileName != null) {
            result.put("state", "SUCCESS");
            result.put("url", fileName);
        } else {
            result.put("state", "ERROR");
        }
        return result;
    }

    /**
     * 
     * @param upfile
     * @return
     */
    @RequestMapping(path = "/notice/upload", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("upfile")MultipartFile upfile) {
        Map<String, Object> result = new HashMap<>();
        result.put("errno", 1);
        List<String> data = new ArrayList<>();
        result.put("data", data);
        String fileName = OSSObjectUtils.uploadMultipartFile(upfile, "", "wut-publicity");
        if (fileName != null) {
            result.put("errno", 0);
            data.add(ossPrefix + "/" + fileName);
//            result.put("fileName", fileName);
        }
        return result;
    }
}
