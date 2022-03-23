<%--
  Created by IntelliJ IDEA.
  User: 梅明
  Date: 2022/3/15
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <%@ include file="../Template_base.jsp"%>
  <link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
  <script src="${ctx}/js/jquery.dataTables.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<%--<div class="breadcrumbs ace-save-state" id="breadcrumbs">--%>
<%--  <ul class="breadcrumb">--%>
<%--    <li class="ace-icon fa fa-home home-icon">销售合同</li>--%>
<%--  </ul>--%>
<%--</div>--%>
<div class="page-content">
  <div class="row">
    <div>
      <label>合同类型</label>
      <br>
      <select id="queryContractType">
        <option value="JPHT">军品合同</option>
        <option value="MPHT">民品合同</option>
        <option value="GJHCJ">国际化成交合同</option>
        <option value="GJHSX">国际化生效合同</option>
        <option value="KJCX">科技创新合同</option>
        <option value="QTYW">其他业务合同</option>
      </select>
    </div>
    <div>
      <label >推送状态</label>
      <br>
      <select id="queryType">
        <option value="0">未推送</option>
        <option value="1">已推送</option>
        <option value="2">推送失败</option>
      </select>
    </div>
    <button class="btn btn-info" id="gritter-sticky" onclick="requestList()">查询销售合同</button>
    <button class="btn btn-info" id="gritter-sticky" onclick="deleteSelected()">删除选定合同</button>
    <button class="btn btn-info" id="gritter-sticky" onclick="uploadContractList()">推送销售合同</button>
  </div>
  <div class="hr hr-16 hr-dotted"></div>
  <div class="row">
    <table id="sellContractListTable" class="table table-bordered table-condensed" width="100%">
      <thead>
      <tr>
        <th class="text-center"><input type="checkbox" name="allChecked" id="allChecked"/></th>
        <th>合同序号</th>
        <th>合同名称</th>
        <th>合同编号</th>
        <th>合同类型</th>
        <th>推送状态</th>
        <th>入库时间</th>
        <th>推送时间</th>
      </tr>
      </thead>
    </table>
  </div>
</div>

</body>
</html>

<script type="text/javascript">

  jQuery(function($) {
    requestList();
  });

  //更新展示列表
  function requestList(){
    // alert("点击查询");
    var queryType = $("#queryType").find("option:selected").val();
    var queryContractType = $("#queryContractType").find("option:selected").val();
    // alert("查询销售合同");
    var table = $("#sellContractListTable");
    var dttable = table.dataTable();
    dttable.fnClearTable(); //清空一下table
    dttable.fnDestroy();//还原初始化了的datatable
    table.DataTable({
      "aLengthMenu":[10,20,40,60],
      "searching":true,
      "ajax":{
        "type":"POST",
        "url":"${ctx}/showSellContractList",
        "data":{"queryType":queryType,"queryContractType":queryContractType}
      },
            "oLanguage" : {
      "sProcessing" : "正在加载中......",
              "sLengthMenu" : "每页显示_MENU_条记录",
              "sZeroRecords" : "对不起，查询不到相关数据！",
              "sEmptyTable" : "无数据存在！",
              "sInfo" : "当前显示_START_到_END_条，共_TOTAL_条记录",
              "sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
              "sSearch" : "查询",
              "oPaginate" : {
        "sFirst" : "首页",
                "sPrevious" : "上一页",
                "sNext" : "下一页",
                "sLast" : "末页"
      }
    },
      "aoColumns":[
        {
          "mData":"id",
          "orderable":false,
          "sWidth":"3%", "render": function (data, type, full, meta) {
            return data = '<div class="text-center"><input type="checkbox" id="singlecheck" name="singlecheck"/></div>';
          }
        },{//合同id
          "mData":"contractid",
          "orderable":false,
          "sWidth":"7%"
        },{   //合同名称
          "mData":"contractName",
          "orderable":false,
          "sWidth":"20%"
        },{   //合同编号
          "mData":"contractCode",
          "orderable":false,
          "sWidth":"20%"
        },{   //合同类型
          "mData":"theirBusiness",
          "orderable":false,
          "sWidth":"10%",
          "render":function(data,type,full,meta){
            if (data == "JPHT"){return"军品合同";}
            else if (data == "MPHT"){return "民品合同";}
            else if (data == "GJHCJ"){return "国际化成交合同";}
            else if (data == "GJHSX"){return "国际化生效合同";}
            else if (data == "KJCX"){return "科技创新合同";}
            else if (data == "QTYW"){return "其他业务";}
          }
        }, {   //推送状态
          "mData": "uploadFlag",
          "orderable": false,
          "sWidth": "10%",
          "render": function (data, type, full, meta) {
            if (data == 0) {return "未推送";}
            else if (data == 1) {return "已推送";}
            else if (data == 2){return "推送失败";}
          }
        },{   //入库时间
          "mData":"insertTime",
          "orderable":false,
          "sWidth":"15%",
          "render":function(data,type,full,meta){
            if (!data ){return " ";}
            return changeDate(data);
          }
        },{   //推送时间
          "mData":"uploadTime",
          "orderable":false,
          "sWidth":"15%",
          "render":function(data,type,full,meta){
            if (!data ){return " ";}
            return changeDate(data);
          }
        }
      ]
    });
  }

  function changeDate(datetimes){
    let date = new Date(datetimes);
    let Y = date.getFullYear()+'-';
    let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    let D = date.getDate() + ' ';
    let h = date.getHours() + ':';
    let m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes());
    // let s = date.getSeconds();
//      年 月 日 时 分 秒
    return Y+M+D+h+m;
  }

  //全选按钮点击事件
  $('#allChecked').bind("click", function () {
    if (this.checked) {
      $(this).prop('checked',true);
      $("input[name='singlecheck']").prop('checked',true);
    }
    else{
      $(this).prop('checked',false);
      $("input[name='singlecheck']").prop('checked',false);
    }
  });

  //遍历table，统计已选行
  function  tableSelectList() {
    var fileIDList=[];
    for(var i=0;i<$('#sellContractListTable').DataTable().rows().count();i++)
    {
      var curnode=$($('#sellContractListTable').DataTable().row(i).node());
      if(curnode.find("input[name='singlecheck']").prop('checked'))
      {
        fileIDList.push($('#sellContractListTable').DataTable().row(i).data().contractid);
      }
    }
    return fileIDList;
  }
  //删除已选项
  function deleteSelected(){
    var fileIDList = tableSelectList();
    if (fileIDList.length == 0){
      alert("未选择合同");
      return;
    }
    if (!confirm("是否删除选中合同")){return;}


    $.ajax({
      type: "POST",
      url: "${ctx}/delete/sellContract",
      data:{"fileIDList":fileIDList.toString()},
      success:function (data) {
        alert(data);
        //删除合同数据后更新页面列表
        requestList();
      },
      error:function (data) {
        alert(data);
      }
    })
  }

  //销售合同上传按钮点击事件
  function uploadContractList(){
    var fileIDList = tableSelectList();
    if (fileIDList.length == 0){
      alert("未选择合同");
      return;
    }
    if (!confirm("是否推送选中合同")){return;}
    alert("点击推送销售合同");


    $.ajax({
      type: "POST",
      url: "${ctx}/uploadSellContractList",
      data:{"fileIDList":fileIDList.toString()},
      success:function (data) {
        alert(data);
        //上传成功后更新页面列表
        requestList();
      },
      error:function (data) {
        alert("上传失败");
      }
    })
  }
</script>
