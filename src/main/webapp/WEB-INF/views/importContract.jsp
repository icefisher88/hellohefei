<%--
  Created by IntelliJ IDEA.
  User: 梅明
  Date: 2022/3/19
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>导入合同数据</title>
    <%@ include file="../Template_base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
    <script src="${ctx}/js/jquery.dataTables.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>

<div class="page-header" style="background-color: white;">
    <h1>导入合同</h1>
</div>
<div class="tabbable">

    <ul class="nav nav-tabs padding-18 tab-size-bigger" id="myTab">
        <li class="active" id="tab1">
            <a data-toggle="tab" href="#faq-tab-1">采购合同</a>
        </li>
        <li id="tab2">
            <a data-toggle="tab" href="#faq-tab-2">销售合同</a>
        </li>
    </ul>
</div>
<div class="tab-content no-border padding-24" width="100%" height="100%" border="0px">

    <div id="faq-tab-1" class="tab-pane fade in active"border="0px">
        <h3 class="header smaller bolder blue" style="border-bottom-color: #4a759a;">导入Excel文件：</h3>
        <div class="col-sm-6" >
            <form class="form-inline" id="importPurchase" name="importPurchase" method="post" action=""
                  enctype="multipart/form-data">
                <div class="form-group col-sm-12">
                    <label>请选择导入文件：</label>&nbsp;
                    <h3></h3>
                    <input type="file" name="uploadFile" id="importPurchaseContractExcel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                </div>
                <div class=" col-sm-12 space-8"></div>
                <div class="col-sm-12">
                    <button class="btn btn-app btn-purple btn-xs" type="submit" id="purchaseContractSubmit">
                        <i class="ace-icon fa fa-cloud-upload bigger-200"></i>
                        导入
                    </button>
                </div>
            </form>
        </div>
    </div>
<%--    销售合同导入--%>
    <div id="faq-tab-2" class="tab-pane fade" >
        <h3 class="header smaller bolder blue" style="border-bottom-color: #4a759a;">导入Excel文件：</h3>
        <div class="col-sm-6" >
            <form class="form-inline" id="importSell" method="post" action=""
                  enctype="multipart/form-data">
                <div class="form-group col-sm-12">
                    <label>请选择导入销售合同类型：</label><br>
                    <select id="queryContractType" name="queryContractType">
                        <option value="JPHT">军品合同</option>
                        <option value="MPHT">民品合同</option>
                        <option value="GJHCJ">国际化成交合同</option>
                        <option value="GJHSX">国际化生效合同</option>
                        <option value="KJCX">科技创新合同</option>
                        <option value="QTYW">其他业务合同</option>
                    </select><br>
                    <label>请选择导入文件：</label>&nbsp;
                    <h3></h3>
                    <input type="file" name="uploadFile" id="importSellContractExcel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                </div>
                <div class=" col-sm-12 space-8"></div>
                <div class="col-sm-12">
                    <button class="btn btn-app btn-purple btn-xs" type="submit" id="sellContractSubmit">
                        <i class="ace-icon fa fa-cloud-upload bigger-200"></i>
                        导入
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<script>

    jQuery(function($) {
        //设置采购合同上传按钮
        $('#importPurchaseContractExcel').ace_file_input({
            no_file:'未选择文件 ...',
            btn_choose:'选择',
            btn_change:'更改',
            droppable:false,
            onchange:null,
            thumbnail:false,//| true | large
            whitelist:'xlsx'
        });

        //设置销售合同上传按钮
        $('#importSellContractExcel').ace_file_input({
            no_file:'未选择文件 ...',
            btn_choose:'选择',
            btn_change:'更改',
            droppable:false,
            onchange:null,
            thumbnail:false,//| true | large
            whitelist:'xlsx'
        });
    });

    //导入采购合同按钮点击
    $("#purchaseContractSubmit").click(function () {
        // alert("导入采购");
        if ($('#importPurchaseContractExcel').val()==""){
            alert("请选择文件再导入");
            return false;
        }
        else if (!confirm("是否导入选中文件?")){return false;}
        // else
        // $("#purchaseContractSubmit").submit();
    });

    //导入销售合同按钮点击
    $("#sellContractSubmit").click(function () {
        // alert("导入销售合同");
        if ($('#importSellContractExcel').val()==""){
            alert("请选择文件再导入");
            return false;
        }
        else if (!confirm("是否导入选中文件?")){return false;}
        // else
        // $("#sellContractSubmit").submit();
    });

    if('${result}'!=null&&'${result}'!="")
    {
        alert('${result}');
    }
    if('${checkIndex}'==='2')
    {
        $("#tab2").attr("class","active");
        $("#tab1").attr("class","");
    }
</script>
