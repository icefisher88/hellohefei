<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar  responsive  ace-save-state"  id="sidebar">
    <script type="text/javascript">
        try{ace.settings.loadState('sidebar')}catch(e){}
    </script>
    <div class="nav nav-list" style="border:1px solid gray;margin-top: 3px">
        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-tachometer bigger-180"></i>
                <span class="menu-text bigger-115" >  工 作 台 </span>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <li style="font-size: 14px;font-weight: bolder">
                    <a href="#">  <i class="menu-icon fa fa-caret-right"></i><span><i class="menu-icon fa fa-dollar"></i> 我管理的部门</span></a>
                </li>
                <li id="myManage1"  style="font-size: 14px;font-weight: bolder">
                    <a href="./main" >  <i class="menu-icon fa fa-caret-right"></i><span> 我管理的项目</span></a>
                </li>
                <li id="myManage2" style="font-size: 14px;font-weight: bolder">
                    <a href="./main2">  <i class="menu-icon fa fa-caret-right"></i><span> 我参与的项目</span></a>
                </li>
            </ul>
        </li>

    </div>
    <div>
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>

</div>