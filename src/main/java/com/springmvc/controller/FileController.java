package com.springmvc.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FileController {
    @RequestMapping("/upload")
    public String goUpload() {
        return "upload";
    }
    @RequestMapping(value="/file/Upload",method= RequestMethod.POST)
    public ModelAndView upLoadFile(@RequestParam("uploadFile") MultipartFile tmpFile, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("upload");
        ModelMap modelResult=new ModelMap();
        String targetDir=request.getSession().getServletContext().getRealPath("uploads");
        System.out.println("the targetDir is:"+targetDir);
        String tmpFileName=tmpFile.getOriginalFilename();
        int dot=tmpFileName.lastIndexOf(".");
        String ext="";
        if((dot>-1)&&(dot<(tmpFileName.length()-1)))
        {
            ext=tmpFileName.substring(dot+1);
        }
        if("xls".equalsIgnoreCase(ext)){
            String newFileName=renameFileName(tmpFileName);
            File target=new File(targetDir,newFileName);
            try{
                FileUtils.copyInputStreamToFile(tmpFile.getInputStream(),target);
                modelResult.addAttribute("result","success");
                modelResult.addAttribute("info",newFileName);
                modelAndView.addAllObjects(modelResult);
                System.out.println("upload success! ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return modelAndView;
    }

    public static String renameFileName(String fileName){
        String formatDate=new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        String extension=fileName.substring(fileName.lastIndexOf("."));
        return formatDate+extension;
    }


}
