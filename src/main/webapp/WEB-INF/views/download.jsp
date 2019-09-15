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
<div class="btn-group">
    <button type="button" class="btn btn-default">按钮 1</button>
    <button type="button" class="btn btn-default">按钮 2</button>
    <button type="button" class="btn btn-default">按钮 3</button>
</div>

<div class="container">
    <form action="/message/downloadFile" class="form-horizontal"  role="form" method="post">
        <fieldset>
            <legend>Test</legend>
            <div class="form-group">
                <label for="dtp_input1" class="col-md-2 control-label">DateTime Picking</label>
                <div class="input-group date form_datetime col-md-5" data-date="1979-09-16T05:25:07Z" data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input1">
                    <input class="form-control" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
                <input type="hidden" id="dtp_input1" value="" /><br/>
            </div>
            <div class="form-group">
                <label for="dtp_input2" class="col-md-2 control-label">Date Picking</label>
                <div class="input-group date form_date col-md-5" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
                <input type="hidden" id="dtp_input2" value="" /><br/>
            </div>
            <div class="form-group">
                <label for="dtp_input3" class="col-md-2 control-label">Time Picking</label>
                <div class="input-group date form_time col-md-5" data-date="" data-date-format="yyyy-mm-dd hh:ii" data-link-field="dtp_input3" data-link-format="yyyy-mm-dd hh:ii">
                    <input class="form-control" name="fileName" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                </div>
                <input type="hidden" id="dtp_input3" value="" /><br/>

            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">开始日期：</label>
                <div class="input-group col-md-5">
                    <input class="form-control" value="" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-remove"></span>
                    </span>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-time"></span>
                    </span>
                </div>

            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">截止时间：</label>
                <div class="input-group col-md-5">
                    <input class="form-control" value="" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-remove"></span>
                    </span>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-time"></span>
                    </span>
                </div>

            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">下载类型：</label>
                <div>
                    <label class="checkbox-inline">
                        <input  name="empChoose" type="checkbox" id="inlineCheckbox1" value="option1"> 人员
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox2" value="option2"> 组织
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox3" value="option3"> 外购件
                    </label>
                </div>

            </div>
            <div class="form-group">
                <div class="col-sm-10 col-sm-offset-4">
                    <button type="submit" class="btn btn-primary" >提交</button>
                </div>
            </div>
        </fieldset>
    </form>

</div>


<script type="text/javascript">
    function download()
    {
        var form = $("<form>");
        form.attr("style","display:none");
        form.attr("target","");
        form.attr("method","post");
        form.attr("action","/message/downloadFile");
        var input1 = $("<input>");
        input1.attr("type","hidden");
        input1.attr("name","fileName");
        input1.attr("value","test");
        $("body").append(form);
        form.append(input1);
        form.submit();
        form.remove();
    }
    $('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1
    });
    $('.form_date').datetimepicker({
        language:  'fr',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
    $('.form_time').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 1,
        maxView: 2,
        forceParse: 0
    });
</script>
</body>
</html>
