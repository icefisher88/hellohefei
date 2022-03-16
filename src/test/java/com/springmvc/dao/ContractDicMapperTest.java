package com.springmvc.dao;

import com.springmvc.common.MenuCodeEnum;
import com.springmvc.entity.ContractDic;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractDicMapperTest extends TestCase {
    private ApplicationContext applicationContext;
    @Autowired
    private ContractDicMapper mapper;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        // 导入需要测试的
        mapper = applicationContext.getBean(ContractDicMapper.class);
    }

    @Test
    public void testSelectAllDics() {
        List<ContractDic> dics = mapper.selectAllDics();
        System.out.println(dics.size());
        HashMap<String,String> compareList=new HashMap<>();
        System.out.println("the begin time is:"+new Date().getTime());

//        String pid=GetMatchingInfo(dics, MenuCodeEnum.SDBK.type,MenuCodeEnum.SDBK.value);
//        GetMatchingItem(dics,MenuCodeEnum.SDBK.value,pid,compareList,">");

        String pid=GetMatchingInfo(dics, MenuCodeEnum.ZGJFJG.type, MenuCodeEnum.ZGJFJG.value);
        GetMatchingItem(dics,MenuCodeEnum.ZGJFJG.value,pid,compareList,">");
        System.out.println("the end time is:"+new Date().getTime());
        for (Map.Entry<String, String> entry : compareList.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

//        String result=GetOnlyMatchingItem(dics,"山西省忻州市",null);
//        System.out.println(result);
    }

    /**
     * 获取某个条目Code、Value对应的ID值
     * @param dics
     * @param code
     * @param value
     * @return
     */
     String GetMatchingInfo(List<ContractDic> dics,String code,String value){
        String result=null;
        for(int i=0;i<dics.size();i++) {
            String dic_code = dics.get(i).getCode();
            String dic_value = dics.get(i).getValue();
            if (dic_code.equals(code) && dic_value.equals(value)) {
               result=dics.get(i).getId();
               break;
            }
        }
        return result;
    }

    /*检查所要匹配的Value值在字典表里是否是唯一存在，若是则返回对应的Code，否则返回null，startCode为起始Code匹配符*/
    String GetOnlyMatchingItem(List<ContractDic> dics, String compareValue, String startCode) {
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

    /**递归获取某个条目下的所有子节点
     *
     * @param dics          查询集合
     * @param ptext        父节点value
     * @param pid         父节点id
     * @param result     结果集：键：自顶向下的Value字符串，值：对应条目的Code
     * @param separator 分割符
     */
     void GetMatchingItem(List<ContractDic> dics,String ptext,String pid,HashMap<String,String> result,String separator){
        for(int i=0;i<dics.size();i++)
        {
            String dic_pid=dics.get(i).getPid();
            if(dic_pid.equals(pid))
            {
                String newText=ptext+separator+dics.get(i).getValue();
                result.put(newText,dics.get(i).getCode());
                GetMatchingItem(dics,newText,dics.get(i).getId(),result,separator);
            }
        }
    }
}