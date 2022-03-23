<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统登录</title>
		<%@ include file="WEB-INF/resource_base.jsp"%>
		<base href="${ctx}/">
		<link HREF="css/login.css" type=text/css rel=stylesheet />
	</head>
	<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		CLASS=PageBody>
		<FORM METHOD="post" NAME="actForm" ACTION="j_spring_security_check">
			<DIV ID="CenterAreaBg" class="container">
				<DIV ID="CenterArea">
					<DIV ID="LogoImg" style="font-size: x-large;color: white">
						合同数据上传平台
					</DIV>
					<DIV ID="LoginInfo">
						<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
							<TR>
								<TD width=45 CLASS="Subject">
									<IMG BORDER="0" SRC="images/login/userId.gif" />
								</TD>
								<TD>
									<INPUT SIZE="20" CLASS="TextField" TYPE="text" tabindex="1" NAME="j_username" />
								</TD>
								<TD ROWSPAN="2" STYLE="padding-left: 10px;">
									<INPUT TYPE="image" tabindex="3"
										SRC="images/login/userLogin_button.gif" />
								</TD>
							</TR>
							<TR>
								<TD CLASS="Subject">
									<IMG BORDER="0" SRC="images/login/password.gif" />
								</TD>
								<TD>
									<INPUT SIZE="20" CLASS="TextField" TYPE="password" tabindex="2"
										NAME="j_password" />
								</TD>
							</TR>
						</TABLE>
					</DIV>
					<DIV ID="CopyRight">
						<A HREF="javascript:void(0)"style="color: white">&copy; 38所流程与信息化部 </A>
					</DIV>
				</DIV>
			</DIV>
		</FORM>
	</BODY>

</html>
