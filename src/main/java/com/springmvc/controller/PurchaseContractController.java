package com.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.common.DicUtil;
import com.springmvc.dao.PurchaseContractMapper;
import com.springmvc.entity.PurchaseContract;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PurchaseContractController {

    @Autowired
    private PurchaseContractMapper mapper=null;
    @RequestMapping("/uploadPurchaseContract")
    public ModelAndView uploadPurchaseContract(){
        ModelAndView modelAndView=new ModelAndView();
//        System.out.println("hello,purchaseFile and singleton"+ Singleton.getInstance().uploadURL);
        return modelAndView;
    }

    @RequestMapping("/uploadContract")
    public ModelAndView uploadContract(){
        ModelAndView modelAndView=new ModelAndView();
//        System.out.println("hello,purchaseFile");
        return modelAndView;
    }
    //查询采购合同接口
    @RequestMapping("/showPurchaseContractList")
    @ResponseBody
    public Map<String,Object> showPurchaseContractList(@RequestParam(name="queryType") int queryType){
        System.out.println("sendFileController:showPurchaseConractList");
        System.out.println("query type = " + queryType);
        List<PurchaseContract> list = new ArrayList<>();
        //根据采购合同推送状态查询采购合同
        list = mapper.getAllPurchaseContractByType(queryType);
        Map<String,Object> map2Json=new HashMap<String,Object>();
        map2Json.put("aaData",list);
        System.out.println("hello"+list);
        return map2Json;
    }


    //上传采购合同接口
    @RequestMapping(value="/uploadPurchaseContractList",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String uploadPurchaseContractList(@RequestParam(name="fileIDList") String[] datas){
        List<Object> failArray = new ArrayList<>();
        String failStr = "失败：";
        String jsonStr = null;
        ObjectMapper objmapper = new ObjectMapper();
        //设置转换json字符串时间格式
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");
        objmapper.setDateFormat(smt);
        try {
            for (int i = 0; i < datas.length; i++) {
                PurchaseContract contract = mapper.selectByPrimaryKey(Integer.parseInt(datas[i]));
                 String contractJson = objmapper.writeValueAsString(contract).toString();
                //执行获取token的get请求
                String token = getToken();
                //执行post合同上传请求
                int responseCode  = postContractList(token,contractJson);
                //根据上传结果更新该条合同upload_flag
                if (responseCode == 200)   //上传成功
                {contract.setUploadFlag(1);}
                else   //上传失败
                {contract.setUploadFlag(2);
                 failArray.add(contract);
                 failStr = failStr +"合同名称："+ contract.getContractName()+",";
                }
//                int updateCode = mapper.updatePCUploadFlagByPrimaryKey(contract);
                //更新推送时间
                contract.setUploadTime(new Timestamp(new Date().getTime()));
                int updateCode = mapper.updateByPrimaryKey(contract);
                System.out.println("update uploadFlag code = " + updateCode);
            }
        }catch (Exception e){
            System.out.println("exception = "+e.getMessage());
        }

        //返回前端上传结果
        if (failArray.size()==0) {
            return "全部推送成功";
        }
        else {
            return  failStr +"共"+failArray.size()+ "条合同推送失败" ;
        }
    }

    //请求token
    public String getToken(){
        //创建get请求
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(DicUtil.getProperty("url") +"/LesCont/token/getToken?key="+DicUtil.getProperty("key"));
        JSONObject jsonResult = null;
        String token = null;
        try{
            //执行get请求
            int statusCode = client.executeMethod(method);
            System.out.println(statusCode);
            if (statusCode == HttpStatus.SC_OK)
            {
                //获取get请求结果
                String responseStr = method.getResponseBodyAsString();
                System.out.println("responseStr = " + responseStr);
                jsonResult = JSONObject.fromObject(responseStr);
                token = jsonResult.getString("data");
                System.out.println("token = " + token);
            }
        }catch (IOException ioe){
            System.err.println(": " + ioe.getMessage());
        }finally {
            method.releaseConnection();
        }
        return token;
    }

    //上传采购合同数据
    public int postContractList(String token,String json){
        //创建POST请求
        int responseCode = 0;
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(DicUtil.getProperty("url")+"/LesCont/api/paycont/insert");
//        PostMethod method = new PostMethod("http://192.168.66.116:8054/LesCont/api/paycont/insertList");
        //设置请求头
        method.setRequestHeader("ContentType","application/json");
        method.setRequestHeader("token",token);
        try {
//               method.setQueryString(json);
            System.out.println("POSTjson = " + json);
            //设置请求参数
            RequestEntity se = new StringRequestEntity(json,"application/json","UTF-8");
            method.setRequestEntity(se);
            //设置请求重试配置
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
            //执行post请求
            client.executeMethod(method);
            responseCode = method.getStatusCode();
            System.out.println("response code = " + responseCode);
            //获取请求返回结果
            String responseStr = method.getResponseBodyAsString();
            System.out.println("POSTresponseStr = " + responseStr);

        }catch (Exception e){
            System.err.println("Exception: " + e.getMessage());

        }finally {
            method.releaseConnection();
        }
        return responseCode;
    }

    //删除采购合同接口
    @RequestMapping(value="/delete/purchaseContract",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String deletePurchaseContract(@RequestParam(name="fileIDList") String[] datas) {
        try{
            for (int i = 0; i<datas.length; i++){
                int responCode = mapper.deleteByPrimaryKey(Integer.parseInt(datas[i]));
            }
        }catch (Exception e){
            System.out.println("deletePurchaseContract exception:"+e.getMessage());
        }
       return "删除成功";
    }


}
