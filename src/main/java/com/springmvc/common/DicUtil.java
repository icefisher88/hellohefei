package com.springmvc.common;

import com.springmvc.dao.ContractDicMapper;
import com.springmvc.dao.ContractUserMapper;
import com.springmvc.entity.ContractDic;
import com.springmvc.entity.ContractUser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Component
public  class DicUtil {

    @Autowired
    private ContractDicMapper mapper;
    @Autowired
    private ContractUserMapper mapper2;
    static Logger logger = Logger.getLogger(DicUtil.class.getName ()) ;
    private static List<ContractDic> dics;
    private static List<ContractUser> users;
    private static Properties props;
    public static  DicUtil dicUtil;
    @PostConstruct
    public void init(){
        dicUtil=this;
        dics=dicUtil.mapper.selectAllDics();
        users=dicUtil.mapper2.getAllContractUserInfo();

       logger.info("begin initalize contract_dic data.....");
    }
    static {
        loadProps();
    }

    synchronized private static  void loadProps(){
        logger.info("开始加载properties文件内容.......");
        props=new Properties();
        InputStream in=null;
        try{
            in=DicUtil.class.getClassLoader().getResourceAsStream("systemConfig.properties");
            props.load(in);
        } catch (IOException e) {
            logger.error("加载properties文件内容失败");
            e.printStackTrace();
        }
        finally {
            if(null!=in)
            {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("jdbc.properties文件流关闭出现异常");
                }
            }
        }
    }

    public static String getProperty(String key){
        if(null==props)
        {
            logger.warn("未初始化properties 文件，开始初始化......");
            loadProps();
        }
        return props.getProperty(key);
    }
    public static ContractUser getUserInfoByCode(String code)
    {
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getUserCode().equals(code))
            {
                return users.get(i);
            }
        }
        return null;
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
