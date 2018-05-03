package com.wutong.web.controller;

import com.parasol.core.bid.Bid_order;
import com.wutong.backmanage.core.page.PageInfoBT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/tender")
public class BidOrderController {

    private static final String PREFIX = "/tender/order/";

    @GetMapping
    @RequestMapping(path = "/bid/index")
    public String index() {
        return PREFIX + "bid/index.html";
    }


    /**
     * 查询指定标项下的投标信息
     * @param tenderId
     * @return
     */
    @RequestMapping(path = "/bid/query")
    @ResponseBody
    public List<Bid_order> query(int tenderId) {
        return null;
    }
}
