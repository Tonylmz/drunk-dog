package com.seu.drunkdog.controller;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@Service
@CrossOrigin(origins = "http://localhost:8081",maxAge = 36000)
public class TestController {
//    @RequestMapping("/test")
//    public void test() throws Exception{
//        String pyPath3 = "python E:\\Study\\intern\\hmm.py --text 我喜欢张艺谋的电影";
//
//        System.out.println(pyPath3);
//        pyPath3 = new String(pyPath3.getBytes(StandardCharsets.UTF_8), "GBK");
//        System.out.println(pyPath3);
////        Process proc = Runtime.getRuntime().exec(pyPath3);
//
//
//    }
    @RequestMapping("/test")
    public void test() throws Exception{
        String pyPath = "main.py";
        String stateStr = "--user-id 16";
        String[] pyPath2 = {"python","E:\\Study\\vscodeProject\\pythonProject\\test.py"};
        String pyPath3 = "python E:\\Study\\intern\\hmm.py --text 我喜欢张艺谋的电影";
        String pyPath4 = "python E:\\Study\\intern\\main.py --user-id 16 --movie-id 1";
        String[] args1 = new String[] {"cmd.exe","ssh root@118.31.14.231","Ai2022!!","python main.py --user-id 16"};
//        String[] args1 = new String[] {"ssh root@118.31.14.231","Ai2022!!","python", pyPath, stateStr};
        String arg2 = "ssh root@118.31.14.231";

        String[] arg3 = {"python","E:\\Study\\intern\\hmm.py",""};

        System.out.println(pyPath3);
        pyPath3 = new String(pyPath3.getBytes(StandardCharsets.UTF_8), "GBK");
        System.out.println(pyPath3);
        Process proc = Runtime.getRuntime().exec(pyPath3);


        Thread.sleep(1000);
        BufferedReader in = new BufferedReader(new InputStreamReader( proc.getInputStream(), "GBK"));
        String line;
        while((line = in.readLine()) != null){
//            line = new String(line.getBytes("GB2312"), StandardCharsets.UTF_8);
            System.out.println(line);
        }
//        String actionStr = in.readLine();
//        System.out.println(actionStr);
//        if (actionStr != null)
//            System.out.println(actionStr);


        in.close();
        proc.waitFor();

    }
    //判断字符串的编码格式
    @RequestMapping("/getEncoding")
    public static String getEncoding(@RequestParam("str") String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }

        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }

        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }

        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    //将不同编码格式转为UTF-8显示出来
    public static String toChinese(String strvalue, String encodeFormat) {
        try {
            if (strvalue == null) {
                return "";
            } else {
                strvalue = new String(strvalue.getBytes(encodeFormat), "UTF-8").trim();					return strvalue;
            }
        } catch (Exception e) {
            return "";
        }
    }


}
