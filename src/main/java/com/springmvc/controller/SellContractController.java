package com.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.common.Singleton;
import com.springmvc.dao.SellContractMapper;
import com.springmvc.entity.SellContract;
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
public class SellContractController {
    @Autowired
    private SellContractMapper mapper=null;

    @RequestMapping("/uploadSellContract")
    public ModelAndView uploadSellContract(){
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("hello,sellcontract");
        return modelAndView;
    }

    //查询购合同接口
    @RequestMapping("/showSellContractList")
    @ResponseBody
    public Map<String,Object> showSellContractList(@RequestParam(name="queryType") int queryType,
                                                   @RequestParam(name="queryContractType") String queryContractType){
        System.out.println("querytype = "+queryType+";queryContractType = "+queryContractType);
        List<SellContract> list= mapper.getAllSellContractByType(queryType,queryContractType);
        Map<String,Object> map2Json=new HashMap<String,Object>();
        map2Json.put("aaData",list);
        System.out.println("销售合同list"+list);
        return map2Json;
    }

    //上传销售合同
    @RequestMapping(value="/uploadSellContractList",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String uploadSellContractList(@RequestParam(name="fileIDList") String[] datas){
        List<Object> failArray = new ArrayList<>();
        String failStr = "失败：";
        String jsonStr = null;
        ObjectMapper objmapper = new ObjectMapper();
        //设置转换json字符串时间格式
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");
        objmapper.setDateFormat(smt);
        try {
            for (int i = 0; i < datas.length; i++) {
                SellContract contract = mapper.selectByPrimaryKey(Integer.parseInt(datas[i]));
                String contractJson = objmapper.writeValueAsString(contract).toString();
                //执行获取token的get请求
                String token = getToken();
                //执行post合同上传请求
                int responseCode  = postContractList(token,contractJson);
                //根据上传结果更新该条合同upload_flag
                if (responseCode == 200)
                {contract.setUploadFlag(1);}
                else
                {contract.setUploadFlag(2);
                    failArray.add(contract);
                    failStr = failStr +"合同名称："+ contract.getContractName()+",";
                }
//                int updateCode = mapper.updateSCUploadFlagByPrimaryKey(contract);
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
            return  failStr +"共"+failArray.size()+ "条合同推送失败";
        }
    }

    //请求token
    public String getToken(){
        //创建get请求
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(Singleton.getInstance().uploadURL+"/LesCont/token/getToken?key=20dd1883e16c4a1bb4e248a278bdeead");
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

    //上传销售合同数据
    public int postContractList(String token,String jsonStr){
        int responseCode = 0;
        //创建POST请求
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Singleton.getInstance().uploadURL+"/LesCont/api/sellcont/insert");
        //设置请求头
        method.setRequestHeader("ContentType","application/json");
        method.setRequestHeader("token",token);
        try {
            System.out.println("post json = " + jsonStr);
            //设置请求参数
            RequestEntity se = new StringRequestEntity(jsonStr,"application/json","UTF-8");
            method.setRequestEntity(se);
            //设置请求重试配置
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
            //执行post请求
            client.executeMethod(method);
            //获取请求返回结果
            responseCode = method.getStatusCode();
            String responseStr = method.getResponseBodyAsString();
            System.out.println("POSTresponseStr = " + responseStr);
        }catch (Exception e){
            System.err.println("Exception: " + e.getMessage());
        }finally {
            method.releaseConnection();
        }
        return responseCode;
    }

    //删除销售合同接口
    @RequestMapping(value="/delete/sellContract",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
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
