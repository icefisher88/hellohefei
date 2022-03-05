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
    <title>文件上传</title>
    <%@ include file="../Template_base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
    <script src="${ctx}/js/jquery.dataTables.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>

<div class="container">
    <h3 class="header smaller bolder blue" style="border-bottom-color: #4a759a;">上传Excel文件：</h3>
    <div class="col-sm-6" >
<%--        <form class="form-inline" method="post" action="<%=request.getContextPath()%>/file/Upload"--%>
    <form class="form-inline" method="post" action=""
              enctype="multipart/form-data">
                    <div class="form-group col-sm-12">
                        <label>请选择上传文件：</label>&nbsp;
                        <h3></h3>
                            <input type="file" name="uploadFile" id="uploadFile" accept="application/vnd.ms-excel">
                    </div>

<%--                <span class="help-block" contenteditable="true">下载Excel上传模板....</span>--%>
<%--                <input class="btn" value="上传" type="submit"/>--%>
                <div class=" col-sm-12 space-8"></div>
                <div class="col-sm-12">
                <button class="btn btn-app btn-purple btn-xs">
                    <i class="ace-icon fa fa-cloud-upload bigger-200"></i>
                    上传
                </button>
                </div>
        </form>
    </div>
    <div class="col-sm-6">
        <div class="widget-box">
            <div class="widget-header widget-header-flat">
                <h4 class="widget-title">上传提示</h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div class="row">
                        <div class="col-xs-12">
                            <ul class="list-unstyled spaced">
                                <li>
                                    <i class="ace-icon fa fa-bell-o bigger-110 purple"></i>
                                   上传文件类型要求为.xls格式;
                                </li>

                                <li>
                                    <i class="ace-icon fa fa-times bigger-110 red"></i>
                                    上传文件大小不得超过2M;
                                </li>
                                <li>
                                    <ul class="list-inline">
                                    <i class="ace-icon fa fa-check bigger-110 green"></i>
                                    上传文件需与模板格式一致。
                                </li>
                                <a href="#" class="btn btn-link" style="font-size: 12px">
                                    <i class="ace-icon glyphicon glyphicon-file" ></i>
                                    下载模板文件...
                                </a>
                                    </ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- /.col -->
    <div class="col-sm-12">
        <h3 class="header smaller bolder blue" style="border-bottom-color: #4a759a;"></h3>
    </div>
<%--    <table class="col-sm-12">--%>
<%--        <tr>--%>
<%--            <td>序号</td>--%>
<%--            <td>新文件名</td>--%>
<%--            <td>原文件名</td>--%>
<%--            <td>上传时间</td>--%>
<%--            <td>文件大小</td>--%>
<%--        </tr>--%>
<%--    <c:forEach items="${files}" var="file" varStatus="status">--%>
<%--        <tr>--%>
<%--            <td>status.count</td>--%>
<%--            <td>${file.newname}</td>--%>
<%--            <td>${file.originname}</td>--%>
<%--            <td>${file.uploaddate}</td>--%>
<%--            <td>${file.size}</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </table>--%>
<table id="uploadFileList" class="table table-bordered table-condensed">
    <thead>
    <tr>
        <th class="text-center"><input type="checkbox" name="allChecked"/></th>
        <th>序号</th>
        <th>原文件名</th>
        <th>新文件名</th>
        <th>上传日期</th>
        <th>操作</th>
    </tr>
    </thead>
</table>

</div>

<script type="text/javascript">
    jQuery(function($) {
        // alert('ok');
        //if(${result!=null})
       // {
       //     alert('${result}');
       // }
        var table=$("#uploadFileList").DataTable({

            "aLengthMenu":[10,20,40,60],
            "searching":true,
            "ajax":{
                "type":"POST",
                "url":"${ctx}/showFileList"

            },
            "aoColumns":[
                {
                    "mData":"id",
                    "orderable":false,
                    "sWidth":"10%", "render": function (data, type, full, meta) {
                        return data = '<div class="text-center"><input type="checkbox"></input></div>';
                    }
                },
                {
                    "mData":"id",
                    "orderable":false,
                    "sWidth":"10%"
                },
                {
                "mData":"originname",
                "orderable":false,
                "sWidth":"20%"
            },{
                "mData":"newname",
                "orderable":false,
                "sWidth":"20%"
            },{
                "mData":"uploaddate",
                "orderable":true,
                "sWidth":"20%",
                "render":function(data,type,full,meta){
                    if(data!=null&&data!=""){
                        var date=new Date(data);
                        return date.toLocaleString();
                    }else{
                        return data;
                    }
                }
            }, {
                    "mData": "id",
                    "orderable": false, // 禁用排序
                    "sDefaultContent": '',
                    "sWidth": "20%",
                    "render": function (data, type, full, meta) {
                        return data = '<div class="col-xs-6"><button id="deleteRow" class="btn btn-danger btn-sm" data-id=' + data + '>删 除</button></div><div class="col-xs-6"><button id="browseRow" class="btn btn-info btn-sm" data-id=' + data + '>详情</button></div>';
                    }
                }
            ]

        });
        table.on('click','button#deleteRow',function(){
            if(confirm('确定要删除这条记录吗？')){
           var row=table.row($(this).parents('tr'));
           var data=row.data();
           $.ajax({
               type:"get",
               url:'${ctx}/upload/delRow',
               data:{rid:data.id,fileName:data.newname},
               dataType:'json',
               cache:false,
               success:function(data){
                   if(data.result=="success") {
                       alert('删除成功');
                       row.remove().draw(false);
                   }
                   else{
                       alert('删除出现错误');
                   }
               }
           });

           // alert(data);
            }
        });
        table.on('click','button#browseRow',function(){
                var row=table.row($(this).parents('tr'));
                var data=row.data();
                alert('这里可以跳出详情窗口');
               // row.remove().draw(false);
                // alert(data);
        });
        $('#uploadFile').ace_file_input({
            no_file:'未选择文件 ...',
            btn_choose:'选择',
            btn_change:'更改',
            droppable:false,
            onchange:null,
            thumbnail:false,//| true | large
            whitelist:'xls'
        });
    });
</script>
</body>
</html>
