<%--
  Created by IntelliJ IDEA.
  User: P50
  Date: 2019/9/12
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="../Template_base.jsp"%>
</head>
<body>

<div class="container">
    <div >
        <form class="form-inline" method="post" action="<%=request.getContextPath()%>/file/Upload"  enctype="multipart/form-data">
            <fieldset>
                <legend contenteditable="true">上传Excel文件：</legend>
                <label contenteditable="true">请选择上传文件：</label>&nbsp;<input type="file" name="uploadFile" accept="application/vnd.ms-excel" /> <span
                    class="help-block" contenteditable="true">下载Excel上传模板....</span>
                <input class="btn" value="上传" type="submit"/>
            </fieldset>
        </form>
    </div>

</div>


</body>
</html>
