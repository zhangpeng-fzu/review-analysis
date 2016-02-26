package com.peng.service;


import com.alibaba.fastjson.JSON;
import com.peng.common.Config;
import com.peng.entity.ReplyEntity;
import com.peng.entity.ResponseEntity;
import com.peng.common.HttpClientUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取评论和分词操作
 * @author MZhang
 * @since 2015-5-7
 */
public class Comment {

    //获取评论
    public static void getComment(String itemId, int type,String userId) {
        File file = new File(Config.FILEPATH + itemId + "_" + type + "_" + System.currentTimeMillis() + ".txt");
        ReplyEntity reply;
        List<Map<String, Object>> comments;
        //获取评论固定链接
        String url = "https://rate.taobao.com/feedRateList.htm?_ksTS=1441442217626_1472&callback=jsonp_reviews_list&userNumId=" + userId + "&auctionNumId=" + itemId +
                "&siteID=7&currentPageNum=1&rateType=" + type + "&orderType=sort_weight&showContent=1&attribute=&ua=";
        ResponseEntity response;
        FileOutputStream out;
        try {
            for (int i = 1; i < 1000; i++) {
                response = HttpClientUtil.sendGet(url);
                url = url.replace("currentPageNum=" + i, "currentPageNum=" + (i + 1));
                String content = response.getContent().replace("jsonp_reviews_list(", "").replace(")", "");
                reply = JSON.parseObject(content, ReplyEntity.class);
                comments = reply.getComments();
                if (comments == null){
                    System.out.println(content);
                    break;
                }
                if (i == 1){
                    if ( ! file.exists()) {
                        file.createNewFile();
                    } else {
                        FileWriter fw = new FileWriter(file);
                        fw.write("");
                        fw.close();
                    }
                }
                out = new FileOutputStream(file, true);
                for (Map<String, Object> comment : comments) {
                    out.write(String.valueOf(comment.get("content")).getBytes());
                    out.write("\r\n".getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户Id
     * @param itemId
     * @return
     */
    public static String getUserId(String itemId) {
        String userId = null;
        try {
            String url = "https://item.taobao.com/item.htm?spm=a219r.lm895.14.22.hQJzlA&id=" + itemId + "&ns=1&abbucket=6";
            boolean flag = false;
            ResponseEntity response = HttpClientUtil.sendGet(url);
            List<String> html = response.getContentCollection();
            Pattern pa = Pattern.compile("userid=[0-9]*");
            Matcher ma;
            for (String line : html) {
                ma = pa.matcher(line);
                while (ma.find()) {
                    userId = ma.group().replace("userid=", "");
                    flag = true;
                    break;
                }
                if (flag) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static void main(String[] args) {
        Segmentation segmentation = new Segmentation();
        segmentation.splitCommentsInFile("./file/524313454205.txt");
    }

}
