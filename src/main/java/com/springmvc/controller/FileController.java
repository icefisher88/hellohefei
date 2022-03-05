package com.springmvc.controller;

import com.springmvc.dao.UploadfileMapper;
import com.springmvc.entity.Uploadfile;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {
    //    @RequestMapping("/upload")
//    public String goUpload() {
//        return "upload";
//    }
    @Autowired
    private UploadfileMapper mapper=null;
    @RequestMapping("/upload")
    public ModelAndView upload()
    {
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("hello,upload");
//        List<Uploadfile> files=mapper.getAllUploadFiles();
//        modelAndView.addObject("files",files);
        return modelAndView;
    }
    @RequestMapping("/showFileList")
    @ResponseBody
    public Map<String,Object> showFileList(){
        List<Uploadfile> files=mapper.getAllUploadFiles();
        Map<String,Object> map2Json=new HashMap<String,Object>();
        map2Json.put("aaData",files);
        System.out.println("hello");
        return map2Json;
    }
    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public ModelAndView upLoadFile(@RequestParam("uploadFile") MultipartFile tmpFile, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        ModelMap modelResult=new ModelMap();
        String targetDir=request.getSession().getServletContext().getRealPath("uploads");
//        System.out.println("the targetDir is:"+targetDir);
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
                Uploadfile upFile=new Uploadfile();
                upFile.setNewname(newFileName);
                upFile.setOriginname(tmpFileName);
                upFile.setSize(getFileSize(target));
                upFile.setUploaddate(new Date());
                mapper.insert(upFile);
                System.out.println("insert record sucess!");
                modelResult.addAttribute("result","success");
                modelResult.addAttribute("info",newFileName);
                modelAndView.addAllObjects(modelResult);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        modelAndView.setViewName("redirect:/upload");
        System.out.println("exec success! ");
        return modelAndView;

//        return "redirect:/upload";
    }

    public static String renameFileName(String fileName){
        String formatDate=new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        String extension=fileName.substring(fileName.lastIndexOf("."));
        return formatDate+extension;
    }

    public static String getFileSize(File file) {
        String FileSize = null;
        try {
            // 指定路径即可
            FileInputStream fis = null;
            FileChannel fileChannel = null;
            if(file.exists() && file.isFile()){
                fis = new FileInputStream(file);
                fileChannel = fis.getChannel();
            }
            // 保留小数点后2位
            DecimalFormat df = new DecimalFormat("#.##");
            // if((double)((double) fis.available() / 1024) > 1000) {
            // 	FileSize= df.format((double)((double) fileChannel.size() / 1024 / 1024)) + "MB";
            // } else {
            FileSize= df.format((double)((double) fis.available() / 1024)) + "KB";
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FileSize;
    }


}
