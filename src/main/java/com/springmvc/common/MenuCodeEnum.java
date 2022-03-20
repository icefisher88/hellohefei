package com.springmvc.common;

public enum MenuCodeEnum {
    /*集团指标字典*/
    SF("SF", "是否"),
    HTMJ("HTMJ", "合同密级"),
    JFFL("JFFL", "甲方分类"),
    BZ("BZ", "币种"),
    SDBK("SDBK", "四大板块"),
    HXYWFL("HXYWFL", "核心业务分类"),
    SSYW("SSYW", "所属业务"),
    HTZT("HTZT", "合同状态"),
    HYFL("HYFL", "行业分类"),
    QD("QD", "渠道"),
    JCKFS("JCKFS", "进出口方式"),
    GJHFL1("GJHFL1", "国际化分类（一）"),
    GJHFL2("GJHFL2", "国际化分类（二）"),
    GJHFL3("GJHFL3", "国际化分类（三）"),
    GB("GB", "国别"),
    KJCXFL("KJCXFL", "科技创新分类"),
    HTXZFL("HTXZFL", "合同性质分类"),
    JFFLJP1("JFFLJP1", "合同甲方分类"),
    JFFLJP2("JFFLJP2", "甲方分类（二）"),
    ZGJFJG("ZGJFJG", "主管军方机关"),
    CPLX("CPLX", "产品类型"),
    ZZLX("ZZLX", "制造类型"),
    ZYLY("ZYLY", "专业领域"),
    SFZDZX("SFZDZX", "是否重大专项"),
    JLDW("JLDW", "计量单位"),
    LVPJ("LVPJ", "履约评价"),
    LVZT("LVZT", "履约状态"),
    JJFS("JJFS", "计价方式"),
    FKFS("FKFS", "付款方式"),
    CGFS("CGFS", "采购方式"),
    CGZZLX("CGZZLX", "采购组织形式"),
    GFXZ("GFXZ", "卖方性质"),
    ZJLY("ZJLY", "资金来源"),
    LYDD("LYDD", "履约地点"),
    CGDXFL("CGDXFL", "采购对象分类"),
    XMLX("XMLX", "项目类型"),
    XMMJ("XMMJ", "项目密级"),
    YWFX("YWFX", "业务方向"),
    XMJZ("XMJZ", "项目进展"),
    GSBM("GSBM", "公司编码"),
    YSLX("YSLX", "预算类型"),
    PB_PJFF("PB_PJFF", "评标_评价办法"),
    LYDB("LYDB", "履约担保"),
    LYJD("LYJD", "履约阶段"),
    CPSX("CPSX", "产品属性"),
    YT("YT", "用途"),
    JSSY("JSSY", "技术收益"),
    JSSY2("JSSY2", "技术收益（二）"),
    HTZT_CG("HTZT_CG", "合同状态_采购"),
    GFLX("GFLX", "供应商类型"),
    JZHT("JZHT", "结转合同"),

    /*合同业务字典*/
    SBZT("SBZT", "上报状态"),
    YWZT("YWZT", "合同业务状态"),
    SJLY("SJLY", "数据来源"),

    /*合同业务模块字典*/
    XSLX("XSLX", "销售合同类型"),
    CGLX("CGLX", "采购合同类型");

    public final String type;
    public final String value;

    MenuCodeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
