<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap-theme.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css" />


<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>

<!--[if lte IE 9]>
<link rel="stylesheet" href="${ctx}/css/ace-part2.min.css" class="ace-main-stylesheet"/>
<link rel="stylesheet" href="${ctx}/css/ace-ie.min.css"/>
<![endif]-->
<link rel="stylesheet" href="${ctx}/css/ace-skins.min.css"/>
<link rel="stylesheet" href="${ctx}/css/ace-rtl.min.css"/>

<!--[if !IE]> -->
<script src="${ctx}/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <![endif]-->

<!--[if lte IE 8]>
<script src="${ctx}/js/html5shiv.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/respond.min.js" type="text/javascript" charset="utf-8"></script>
<![endif]-->
<!--[if IE]>
<script src="${ctx}/js/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<![endif]-->
<script src="${ctx}/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
<!-- ace scripts -->
<script src="${ctx}/js/ace-elements.min.js"></script>
<script src="${ctx}/js/ace.min.js"></script>
