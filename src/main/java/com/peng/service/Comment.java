package com.peng.service;


import com.alibaba.fastjson.JSON;
import com.peng.config.Config;
import com.peng.entity.ReplyEntity;
import com.peng.entity.ResponseEntity;
import com.peng.util.HttpClientUtil;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
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

    /**
     * 对文件进行分词
     * @param fileName
     * @return
     */
    public static Map<Integer, Map<String, Integer>> splitCommentsInFile(String fileName) {
        //分词操作
        BufferedReader reader;
        Map<String, Integer> wordsFrenMaps = new HashMap<>();
        Map<Integer, Map<String, Integer>> splitCommentMap = new HashMap<Integer, Map<String, Integer>>();
        IKSegmentation ikSegmenter;
        Lexeme lexeme;
        String line;
        try {
            int i = 0;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            while ((line = reader.readLine()) != null) {
                ikSegmenter = new IKSegmentation(new StringReader(line), true);
                while ((lexeme = ikSegmenter.next()) != null) {
                    if (lexeme.getLexemeText().length() <= 1) {
                        continue;
                    }
                    if (wordsFrenMaps.containsKey(lexeme.getLexemeText())) {
                        wordsFrenMaps.put(lexeme.getLexemeText(), wordsFrenMaps.get(lexeme.getLexemeText()) + 1);
                    } else {
                        wordsFrenMaps.put(lexeme.getLexemeText(), 1);
                    }
                }
                splitCommentMap.put(i, wordsFrenMaps);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitCommentMap;
    }

    /**
     * 进行词频统计排序
     *
     * @param wordsFrenMaps
     * @return
     */
    public static List<Entry<String, Integer>> sortSegmentResult(Map<String, Integer> wordsFrenMaps) {
        //这里将map.entrySet()转换成list
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(wordsFrenMaps.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            //升序排序
            @Override
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }


    public static void main(String[] args) {
//        for (long i = 524313454205L;i < 624313454205L;i++){
//            System.out.println(i);
//            String userId = getUserId(String.valueOf(524313454206L));
////            if (StringUtils.isBlank(userId)){
////                continue;
////            }
//            getComment(String.valueOf(524313454206L), 0,userId);
//        }
        splitCommentsInFile("./file/524313454205.txt");
    }

}
