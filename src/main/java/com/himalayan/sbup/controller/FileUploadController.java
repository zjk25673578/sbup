package com.himalayan.sbup.controller;

import com.himalayan.sbup.util.AESUtil;
import com.himalayan.sbup.util.FileNameUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@RestController
public class FileUploadController {

    @ModelAttribute
    public String valid(String code) {
        System.out.println("ModelAttribute: " + code);
        if (code == null) {
            return null;
        }
        try {
            return AESUtil.Encrypt(code, AESUtil.CDKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/test")
    public String hello(String param) {
        return "Hello World !" + param;
    }

    @RequestMapping("/server/upload")
    public Map<String, Object> upload(@ModelAttribute String code, String pre, MultipartFile file, HttpServletResponse response) {
        System.out.println("code: " + code);
        System.out.println("pre: " + pre);
        if (file == null) {
            return null;
        }
        // response.setHeader("Access-Control-Allow-Origin", "http://localhost:8083"); // 解决跨域
        response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域
        Map<String, Object> map = new HashMap<>();
        String fileName = file.getOriginalFilename();

        if (pre == null) {
            pre = "";
        }
        File parent = new File("files/" + pre);
        boolean flag;
        if (!parent.exists()) {
            flag = parent.mkdirs();
        } else {
            flag = parent.exists();
        }
        File saveFile = null;
        String newFileName = FileNameUtil.getFileName(fileName);
        if (flag) {
            saveFile = new File(parent.getAbsolutePath() + File.separator + newFileName);
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("savePath", saveFile != null ? saveFile.getAbsolutePath() : "");
        return map;
    }
}
