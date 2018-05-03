package com.wutong.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wutong.backmanage.common.annotation.log.BussinessLog;
import com.wutong.backmanage.common.constant.Dict;
import com.wutong.backmanage.core.controller.BaseController;
import com.wutong.backmanage.core.exception.BizExceptionEnum;
import com.wutong.backmanage.core.exception.BussinessException;
import com.wutong.backmanage.core.factory.ConstantFactory;
import com.wutong.backmanage.core.log.LogObjectHolder;
import com.wutong.backmanage.core.shiro.ShiroKit;
import com.wutong.backmanage.core.util.ToolUtil;
import com.wutong.backmanage.dao.NoticeMapper;
import com.wutong.backmanage.pojo.Notice;
import com.wutong.core.wrapper.NoticeWrapper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    private String PREFIX = "/system/notice/";

    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * 跳转到修改通知
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Integer noticeId, Model model) {
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        model.addAttribute("notice",notice);
        LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 跳转到首页通知
     */
    @RequestMapping("/hello")
    public String hello() {
        List<Map<String, Object>> notices = noticeMapper.list(null);
        super.setAttr("noticeList",notices);
        return "/blackboard.html";
    }

    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = noticeMapper.list(condition);
        return super.warpObject(new NoticeWrapper(list));
    }

    /**
     * 新增通知
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增通知",key = "title",dict = Dict.NoticeMap)
    public Object add(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        notice.setCreater(ShiroKit.getUser().getId());
        notice.setCreatetime(new Date());
        noticeMapper.insert(notice);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除通知
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知",key = "noticeId",dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer noticeId) {
        //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));
        noticeMapper.deleteByPrimaryKey(noticeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知",key = "title",dict = Dict.NoticeMap)
    public Object update(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getId(), notice.getTitle(), notice.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Notice old = noticeMapper.selectByPrimaryKey(notice.getId());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        noticeMapper.updateByPrimaryKey(old);
        return super.SUCCESS_TIP;
    }

}
