<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page import="com.wutong.backmanage.common.ueditor.UeditorOSSUtil" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );

	out.write( new ActionEnter( request, rootPath ).exec() );
//	out.write(UeditorOSSUtil.changeOSSFunction(request, new ActionEnter( request, rootPath ).exec()));
%>