package com.wutong.backmanage.common.ueditor;

import com.alibaba.fastjson.JSONObject;
import com.wutong.backmanage.common.file.OSSObjectUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UeditorOSSUtil {
    public static String changeOSSFunction(HttpServletRequest request, String json) {

        if (!StringUtils.isBlank(json)) {

            @SuppressWarnings("unchecked")
            Map<String, Object> map = JSONObject.parseObject(json, Map.class);

            if (true) {
                // 点击上传图片
                String url = map.get("url") + "";

                if (!StringUtils.isBlank(url)) {

                    String fileName = url.substring(url.lastIndexOf("/") + 1);

                    upload(request, url, fileName);

                    map.put("url", "http://xxx.oss-cn-beijing.aliyuncs.com/" + fileName);
                }
            }

            if (true) {
                // 点击上传图片
                String list = map.get("list") + "";

                if (!StringUtils.isBlank(list)) {

                    @SuppressWarnings("unchecked")
                    List<Object> listINFO = JSONObject.parseObject(list, ArrayList.class);

                    for (int i = 0; i < listINFO.size(); i++) {

                        @SuppressWarnings("unchecked")
                        Map<String, Object> mapList = JSONObject.parseObject(listINFO.get(i) + "", Map.class);
                        String urllist = mapList.get("url") + "";

                        if (!StringUtils.isBlank(urllist)) {

                            String fileName = urllist.substring(urllist.lastIndexOf("/") + 1);

                            upload(request, urllist, fileName);

                            mapList.put("url", "http://xxx.oss-cn-beijing.aliyuncs.com/" + fileName);

                            listINFO.set(i, mapList);
                        }
                    }
                    map.put("list", listINFO);
                }
            }
            return JSONObject.toJSONString(map);
        }
        return json;
    }


    private static void upload(HttpServletRequest request, String urllist, String fileName) {
        try {
            String r = request.getSession().getServletContext().getRealPath("");
            // r = r.substring(0, r.lastIndexOf("[PROJECT_NAME]") - 1); // 如果编辑器获取到的真实路径和项目同级，需要加上这一句。
            String path = r + urllist;

            File file = new File(path);

            InputStream imgStream = new FileInputStream(file);
            OSSObjectUtils.uploadFile(fileName, imgStream);
            imgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}