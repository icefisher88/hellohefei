<%--
  Created by IntelliJ IDEA.
  User: P50
  Date: 2019/10/5
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改密码</title>
    <%@ include file="../Template_base.jsp"%>
    <style type="text/css">
        .has-feedback label ~ .form-control-feedback{
            top:0;
        }
    </style>
</head>
<body>
<div class="container">
    <form action="" class="form-horizontal" role="form" method="post" >
        <fieldset>
            <legend>修改密码</legend>
            <div class="form-group">
                <label class="col-md-2 control-label">原有密码：</label>
                <div class="input-group col-md-5">
                    <input class="form-control" type="password" id="originPassword" name="originPassword" />
                    <span class="input-group-addon" style="cursor:pointer">
                        <span class="fa fa-eye-slash" onclick="changedisplay(this);" ></span>
                    </span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">请输入新密码：</label>
                <div class="input-group col-md-5">
                    <input class="form-control" type="password" id="newPassword" name="newPassword" />
                    <span class="input-group-addon"  style="cursor:pointer">
                        <span class="fa fa-eye-slash" onclick="changedisplay(this);"></span>
                    </span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">请再次输入新密码：</label>
                <div class="input-group col-md-5">
                    <input class="form-control" type="password" id="newRepeatPassword" name="newRepeatPassword" />
                    <span class="input-group-addon"  style="cursor:pointer">
                        <span class="fa fa-eye-slash" onclick="changedisplay(this);"></span>
                    </span>
                </div>
            </div>
            <div class="form-group" id="tip"></div>
            <div class="form-group">
                <div class="col-sm-10 col-sm-offset-4">
                    <button onclick="modifyPassword()" type="submit" class="btn btn-primary" >提交</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<script type="text/javascript">
    $(function(){
        $('form').bootstrapValidator({
            container:'#tip',
            message:'This value is not valid',
            feedbackIcons:{
                valid:'glyphicon glyphicon-ok',
                invalid:'glyphicon glyphicon-remove',
                validating:'glyphicon glyphicon-refresh'
            },
            fields:{
                originPassword:{
                    message:'原有密码验证失败',
                    validators:{
                        notEmpty:{message:'密码不得为空'}
                    }
                },
                newPassword:{
                    message:'新密码验证失败',
                    validators:{
                        notEmpty:{message:'新密码不得为空'},
                        identical:{field:'newRepeatPassword',message:'两次输入密码不一致'},
                        regexp: {
                            regexp: /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,20}$/,
                            message: '新密码中必须包含字母、数字、特殊字符，长度在8-20之间'
                        }
                    }
                },
                newRepeatPassword:{
                    message:'新密码验证失败',
                    validators:{
                        notEmpty:{message:'新密码不得为空'},
                        identical:{field:'newPassword',message:'两次输入密码不一致'}
                    }
                }
            }
        });
    });

    function modifyPassword()
    {
        $('form').data('bootstrapValidator').validate();
        var flag=$('form').data('bootstrapValidator').isValid();
        if(flag)
        {
            $.ajax({
                url:"/authority/modifypassword",
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                data:{
                    newPassword:$('#newPassword').val(),
                    originPassword:$('#originPassword').val(),
                },
                success:function (result,status,req) {
                    alert(result.result);
                    $('form').data('bootstrapValidator').resetForm();
                },
                error:function(result,status,reason){
                    alert(reason);
                    alert(status);
                }
            })
        }
        else
        {
            alert('表单验证未通过，请检查......');
        }
       //return false;



    }
    function changedisplay(object)
    {
        var iconClass=$(object).attr('class');
        var inputType=$(object).parent().prev().attr('type');
        if(iconClass=="fa fa-eye-slash")
        {
            $(object).attr('class',"fa fa-eye");
            var xx=$(object).parent().prev().get(0);
            xx.type="text";
            // var xx=$(object).parent().prev();
            // var txtValue=xx.prop("outerHTML").replace(/password/,"text");
            // var root=xx.parent();
            // xx.remove();
            // root.prepend(txtValue);
            //root.prepend('<input class="form-control" type="text" value="'+txtValue+'" id="'+txtid+'" name="'+txtid+'" />');
        }
        else
        {
            $(object).attr('class',"fa fa-eye-slash");
            var xx=$(object).parent().prev().get(0);
           xx.type="password";
           //  var xx=$(object).parent().prev();
           //  var txtValue=xx.prop("outerHTML").replace(/text/,"password");
           //  var root=xx.parent();
           //  xx.remove();
           //  root.prepend(txtValue);
            // root.prepend('<input class="form-control" type="password" value="'+txtValue+'" id="'+txtid+'" name="'+txtid+'" />');
        }
    }
</script>
</body>
</html>
