<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head
<%@ include file="WEB-INF/resource_base.jsp" %>
</head>
<body>
<div class="page-header" style="background-color: white;">
    <h1>合同上报系统</h1>
</div>
<button class="btn btn-primary" id="gritter-sticky2" onclick="window.location.href='./importContract'">
    <span class="glyphicon glyphicon-import"></span>导入合同数据</button>
<button class="btn btn-primary" id="gritter-sticky1" onclick="window.location.href='./uploadContract'">
    <span class="glyphicon glyphicon-send"></span> 推送合同数据</button>
</body>
</html>