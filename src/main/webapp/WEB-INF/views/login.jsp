<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<META http-equiv=Content-Type CONTENT="text/html; charset=utf8" />
		<TITLE>登录</TITLE>
		<LINK HREF="/css/login.css" type=text/css rel=stylesheet />
	</HEAD>

	<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		CLASS=PageBody>
		<FORM METHOD="post" NAME="actForm" ACTION="j_spring_security_check">
			<DIV ID="CenterAreaBg">
				<DIV ID="CenterArea">
					<DIV ID="LogoImg">
<%--						<IMG BORDER="0" SRC="style/blue/images/logo.png" />--%>
						合同数据平台
					</DIV>
					<DIV ID="LoginInfo">
						<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
							<TR>
								<TD width=45 CLASS="Subject">
									<IMG BORDER="0" SRC="/images/login/userId.gif" />
								</TD>
								<TD>
									<INPUT SIZE="20" CLASS="TextField" TYPE="text" NAME="j_username" />
								</TD>
								<TD ROWSPAN="2" STYLE="padding-left: 10px;">
									<INPUT TYPE="image"
										SRC="/images/login/userLogin_button.gif" />
								</TD>
							</TR>
							<TR>
								<TD CLASS="Subject">
									<IMG BORDER="0" SRC="/images/login/password.gif" />
								</TD>
								<TD>
									<INPUT SIZE="20" CLASS="TextField" TYPE="password"
										NAME="j_password" />
								</TD>
							</TR>
						</TABLE>
					</DIV>
					<DIV ID="CopyRight">
						<A HREF="javascript:void(0)">&copy; 20版权所有 </A>
					</DIV>
				</DIV>
			</DIV>
		</FORM>
	</BODY>

</HTML>
