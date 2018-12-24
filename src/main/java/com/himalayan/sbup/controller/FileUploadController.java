package com.himalayan.sbup.controller;

import com.himalayan.sbup.util.FileNameUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request) {
        String ctx = request.getServletContext().getContextPath();
        System.out.println(ctx);
        System.out.println(request.getServletContext().getRealPath("/"));
        return "Hello World !";
    }

    @ModelAttribute
    public void test(String pre) {
        pre += "1";
        System.out.println("@ModelAttribute方法 => pre: " + pre);
    }

    @RequestMapping("/upload")
    public Map<String, Object> upload(String pre, MultipartFile file, HttpServletResponse response) {
        System.out.print("开始上传文件...");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8083"); // 解决跨域
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
        System.out.println("上传结束 !");
        return map;
    }
}
