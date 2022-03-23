package com.springmvc.controller;

import com.alibaba.fastjson.JSONArray;
import com.springmvc.common.ContractUtil;
import com.springmvc.dao.PurchaseContractMapper;
import com.springmvc.dao.SellContractMapper;
import com.springmvc.dao.UploadfileMapper;
import com.springmvc.entity.PurchaseContract;
import com.springmvc.entity.SellContract;
import com.springmvc.entity.Uploadfile;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FileController {
    //    @RequestMapping("/upload")
//    public String goUpload() {
//        return "upload";
//    }
    @Autowired
    private UploadfileMapper mapper=null;
    @Autowired
    private PurchaseContractMapper purMapper=null;
    @Autowired
    private SellContractMapper sellMapper=null;

    @RequestMapping("/upload")
    public ModelAndView upload()
    {
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("hello,upload");
//        List<Uploadfile> files=mapper.getAllUploadFiles();
//        modelAndView.addObject("files",files);
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
        {
            System.out.println("hello,"+SecurityContextHolder.getContext().getAuthentication().getName());
        }
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

    @RequestMapping(value="/upload/delSelect",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delMultiRow(@RequestParam(name="fileIDList") String[] requestBody)
    {
        Map<String,String> result=new HashMap<String,String>();
//        System.out.println(requestBody);
        //这里解析ID数组，然后调用单个文件删除方法
        result.put("result","success");
        return result;
    }



    @RequestMapping(value="/upload/delRow",method= RequestMethod.GET)
    @ResponseBody
    public Map<String, String> delRow(@RequestParam("rid") String rid,HttpServletRequest request) {
        String fileName=request.getParameter("fileName");
        String targetDir=request.getSession().getServletContext().getRealPath("uploads");
        File targetFile=new File(targetDir,fileName);
        Map<String, String> delResult = new HashMap<String, String>();
        if(targetFile.exists()) //删除文件
        {
            System.out.println("begin del..."+rid+","+fileName);
            FileUtils.deleteQuietly(targetFile);
            delResult.put("result", "success");
        }
        int delOps=mapper.deleteByPrimaryKey(Integer.parseInt(rid));//删除数据库记录
        System.out.println("the del tag is:"+delOps);

        if(delOps<1)
        {
            delResult.put("result","failure");
        }
        else{
            delResult.put("result", "success");
        }
        return delResult;
    }
    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public ModelAndView upLoadFile(@RequestParam("uploadFile") MultipartFile tmpFile, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
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
    @RequestMapping(value = "/importContract",method = RequestMethod.GET)
//    public ModelAndView importContract(){
//        ModelAndView modelAndView=new ModelAndView();
//        return modelAndView;
//    }
    public String importContract(){
        return "importContract";
    }
    @RequestMapping(value="/importContract",method= RequestMethod.POST)
    public ModelAndView upLoadContractFile(@RequestParam("uploadFile") MultipartFile tmpFile, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        ModelMap modelResult=new ModelMap();
        String targetDir=request.getSession().getServletContext().getRealPath("uploads");
        String docType=request.getParameter("queryContractType");
        String tmpFileName=tmpFile.getOriginalFilename();
        int dot=tmpFileName.lastIndexOf(".");
        String ext="";
        if((dot>-1)&&(dot<(tmpFileName.length()-1)))
        {
            ext=tmpFileName.substring(dot+1);
        }
        if("xlsx".equalsIgnoreCase(ext)){
            String newFileName=renameFileName(tmpFileName);
            File targetFile=new File(targetDir,newFileName);
            try {
                FileUtils.copyInputStreamToFile(tmpFile.getInputStream(),targetFile);
                System.out.println("the upload file is:"+targetFile.getAbsolutePath());
                if(docType==null)//上传采购合同
                {
                    List<PurchaseContract> contractList = ContractUtil.importPurchaseContractFromExcel(targetFile);
                    for(int i=0;i<contractList.size();i++)
                    {
                        PurchaseContract contract=contractList.get(i);
                        System.out.println(JSONArray.toJSON(contract).toString());
                        //purMapper.insertSelective(contract);
                    }
                }
                else
                {
                    List<SellContract> contractList = ContractUtil.importSellContractFromExcel(targetFile, docType);
                    for(int i=0;i<contractList.size();i++)
                    {
                        SellContract contract=contractList.get(i);
                        System.out.println(JSONArray.toJSON(contract).toString());
//                        sellMapper.insertSelective(contract);
                    }

                }
            } catch (IOException e) {
                modelAndView.addObject("result","上传文件失败！");
            } catch (InvalidFormatException e) {
                modelAndView.addObject("result","解析Excel文件失败！");
            }
            finally {
                if(targetFile!=null){
                    System.out.println("begin delete tempfile:"+targetFile.getAbsolutePath());
                    FileUtils.deleteQuietly(targetFile);
                }

            }
        }
        else
        {
            modelAndView.addObject("result","上传文件类型不正确！");
        }
        if(modelAndView.isEmpty())
        {
            modelAndView.addObject("result","上传文件成功！");
        }
        if(docType==null)
        {
            modelAndView.addObject("checkIndex","1");
        }
        else{
            modelAndView.addObject("checkIndex","2");
        }
        System.out.println("importFile finish....");
        return modelAndView;
    }

}
