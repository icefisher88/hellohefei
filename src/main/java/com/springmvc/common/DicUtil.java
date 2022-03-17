package com.springmvc.common;

import com.springmvc.dao.ContractDicMapper;
import com.springmvc.entity.ContractDic;
import com.springmvc.entity.PurchaseContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public  class DicUtil {
    @Autowired
    private ContractDicMapper mapper;
    private static List<ContractDic> dics;
    public static  DicUtil dicUtil;
    @PostConstruct
    public void init(){
        dicUtil=this;
        dics=dicUtil.mapper.selectAllDics();
        System.out.println("begin initalize contract_dic data.....");
    }
    public static  List<ContractDic> getContractDics(){
       return  dicUtil.mapper.selectAllDics();
    }
    public static String  getItemCode(MenuCodeEnum checkItem,String compareText,String separator){
        if(StringUtils.isNotEmpty(compareText))
            compareText=compareText.trim();
        String pid=getMatchingInfo(checkItem.type, checkItem.value);
        if(StringUtils.isEmpty(pid))
            return null;
        HashMap<String,String>  compareList=new HashMap<>();
        getMatchingItem(dics,"",pid,compareList,separator);
        if(!compareList.isEmpty()){
            String tarCode=compareList.get(compareText);
            return tarCode;
        }
        return null;
    }
    /**
     * 获取某个条目Code、Value对应的ID值
     * @param code
     * @param value
     * @return
     */
    static String getMatchingInfo(String code,String value){
        String result=null;
        for(int i=0;i<dics.size();i++) {
            String dic_code = dics.get(i).getCode().trim();
            String dic_value = dics.get(i).getValue().trim();
//            System.out.println(dic_code+","+dic_value);
            if (dic_code.equals(code) && dic_value.equals(value)) {
                result=dics.get(i).getId();
                break;
            }
        }
        return result;
    }

    /**递归获取某个条目下的所有子节点
     *
     * @param dics          查询集合
     * @param ptext        父节点value
     * @param pid         父节点id
     * @param result     结果集：键：自顶向下的Value字符串，值：对应条目的Code
     * @param separator 分割符
     */
    static void getMatchingItem(List<ContractDic> dics, String ptext, String pid, HashMap<String,String> result, String separator){
        for(int i=0;i<dics.size();i++)
        {
            String dic_pid=dics.get(i).getPid();
            if(dic_pid.equals(pid))
            {
                String newText;
                if(ptext.equals(""))
                    newText=dics.get(i).getValue();
                else
                    newText=ptext+separator+dics.get(i).getValue();
                result.put(newText,dics.get(i).getCode());
                getMatchingItem(dics,newText,dics.get(i).getId(),result,separator);
            }
        }
    }

    /*备用：检查所要匹配的Value值在字典表里是否是唯一存在，若是则返回对应的Code，否则返回null，startCode为起始Code匹配符*/
   static String getOnlyMatchingItem(String compareValue, String startCode) {
        String result = null;
        int count = 0;
        for (int i = 0; i < dics.size(); i++) {
            String dic_value = dics.get(i).getValue();
            String dic_code= dics.get(i).getCode();
            if (dic_value.equals(compareValue)) {
                if(startCode!=null&&!dic_code.startsWith(startCode))//指定代码起始开始匹配字符，缩小匹配范围
                    continue;
                result = dics.get(i).getCode();
                count++;
            }
        }
        if (count != 1)
            result = null;
        return result;
    }
}
