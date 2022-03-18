package com.springmvc.common;

public enum ContractEnum {
    /*集团指标字典*/
    JPHT("JPHT", "军品合同"),
    MPHT("MPHT", "民品合同"),
    GJHCJ("GJHCJ", "国际化成交合同"),
    GJHSX("GJHSX", "国际化生效合同"),
    KJCX("KJCX", "科技创新合同"),
    QTYW("QTYW", "其他业务合同");

    public final String type;
    public final String value;

    ContractEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
