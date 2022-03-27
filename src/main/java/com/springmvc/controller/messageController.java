package com.springmvc.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class messageController {
    @RequestMapping("/message/download")
    public String goDownload() {
        return "download";
    }

    @RequestMapping("/main")
    public String goMain(){return "main";}
    @RequestMapping("/main2")
    public String goMain2(){return "main2";}

    @RequestMapping("/message/downloadFile")
    public ResponseEntity export(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        File file = new File("D:\\soft\\Notepad++.rar");

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

//        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
//                headers, HttpStatus.CREATED);
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity("<html><body onload=\"alert('ok');\"</body></html>",header, HttpStatus.OK);
//        response.setContentType("text/html;charset=utf8");
//        PrintWriter out=response.getWriter();
//        out.print("<script language=\"javascript\">alert('ok');</script>");
//        Map<String,Object> map = new HashMap<>();
//        map.put("message", "Hello Wrold");
//        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
}
