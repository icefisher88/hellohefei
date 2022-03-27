<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/3/27
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>主页</title>
    <%@ include file="Template/resource_base.jsp" %>
</head>
<body>
<div class="no-skin">
    <%@ include file="Template/logo.jsp" %>
    <div class="main-container ace-save-state" id="main-container">
        <script type="text/javascript">
            try {
                ace.settings.loadState('main-container')
            } catch (e) {
            }
        </script>
        <%@ include file="Template/navmenu.jsp" %>
        <div class="main-content" style="min-height: 80%">
            <div class="main-content-inner">
                <div class="col-md-6 text-center">kkk</div>
                <div class="col-md-6 text-center">sss</div>
            </div>
        </div>
        <%@ include file="Template/footer.jsp" %>
    </div>
</div>
<script>
    $().ready(
        function() {
            $('#myManage2').attr("class", "active");
            // $('.nav-list>li').eq(0)
        }
    )
</script>
</body>
</html>
