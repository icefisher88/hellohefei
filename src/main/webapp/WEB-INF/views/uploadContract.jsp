<%--
  Created by IntelliJ IDEA.
  User: 梅明
  Date: 2022/3/18
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>推送合同数据</title>
    <%@ include file="../Template_base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
    <script src="${ctx}/js/jquery.dataTables.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<%--    <div class="breadcrumbs ace-save-state" id="breadcrumbs">--%>
<%--        <ul class="breadcrumb">--%>
<%--            <li class="ace-icon fa ">推送合同</li>--%>
<%--        </ul>--%>
<%--    </div>--%>
<div class="page-header" style="background-color: white;">
    <h1>推送合同</h1>
</div>
    <div class="tabbable">
        <ul class="nav nav-tabs padding-18 tab-size-bigger" id="myTab">
            <li class="active">
                <a data-toggle="tab" href="#faq-tab-1">采购合同</a>
            </li>
            <li >
                <a data-toggle="tab" href="#faq-tab-2">销售合同</a>
            </li>
        </ul>
    </div>
    <div class="tab-content no-border padding-24" width="100%" height="100%" border="0px">
        <div id="faq-tab-1" class="tab-pane fade in active" border="0px">
            <iframe src="./uploadPurchaseContract" width="100%" height="100%"  frameborder="0" border="0px"></iframe>
        </div>
        <div id="faq-tab-2" class="tab-pane fade" border="0px">
            <iframe src="./uploadSellContract" width="100%" height="100%" frameborder="0" border="0px"></iframe>
        </div>
    </div>
</body>
</html>
<script>

</script>
