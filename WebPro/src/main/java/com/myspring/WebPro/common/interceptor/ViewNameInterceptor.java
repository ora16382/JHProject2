package com.myspring.WebPro.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewNameInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String viewName = getViewName(request);
		request.setCharacterEncoding("utf-8");
		request.setAttribute("viewName",viewName);
		//������ �̵� �� ���� ���� ������(�α��� �������� �̵��������� previous page �� ��)�� ���ǿ� ����
		//��û �̸��� �α�����������, �α��ε����� �ƴҶ����� ���ǿ� ������������ ����
		if(! (viewName.equals("/member/loginForm")||viewName.equals("/member/login")||viewName.equals("/board/modArticle") ||viewName.equals("/download") || viewName.equals("/board/replyForm") || viewName.equals("/board/addNewComment") || viewName.equals("/gboard/modArticle") ||viewName.equals("/gboard/replyForm") || viewName.equals("/gboard/addNewComment") 
				|| viewName.equals("/hboard/modArticle") ||viewName.equals("/hboard/replyForm") || viewName.equals("/hboard/addNewComment") || viewName.equals("/pboard/modArticle") ||viewName.equals("/pboard/replyForm") || viewName.equals("/pboard/addNewComment") ) ) {
			request.getSession().setAttribute("previous", viewName+".do"+((request.getQueryString()==null)?"":"?"+request.getQueryString()));	
		}
		
//System.out.println(request.getAttribute("previous"));
		return true;
	}
	
	private String getViewName(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.indexOf(".")!=-1) uri = uri.substring(0,uri.lastIndexOf("."));
		if(uri.indexOf("/")!=-1) uri = uri.substring(uri.indexOf("/",1),uri.length());

		return uri;
	}
}
