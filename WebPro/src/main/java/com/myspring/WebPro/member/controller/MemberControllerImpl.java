package com.myspring.WebPro.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.WebPro.board.service.BoardService;
import com.myspring.WebPro.member.service.MemberService;
import com.myspring.WebPro.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	

	@Autowired
	private MemberVO memberVO;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private com.myspring.WebPro.Gboard.service.BoardService gboardService;
	
	@Autowired
	private com.myspring.WebPro.Hboard.service.BoardService hboardService;
	
	@Autowired
	private com.myspring.WebPro.Pboard.service.BoardService pboardService;
	
	//�̰� �ٱ� ��Ű���� �־ ��, WebPro/ ���� ��û�� �̰� ���εǸ�, �� �������� �����ϴ� ���� /
@RequestMapping(value= {"/","/main.do"},method= RequestMethod.GET)
private ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String viewName = (String) request.getAttribute("viewName");
	Map<String,Integer> page = new HashMap<String, Integer>();
	page.put("section", 1);
	page.put("pageNum", 1);
	Map garticlesMap = gboardService.listArticles(page);
	Map articlesMap = boardService.listArticles(page);
	Map harticlesMap = hboardService.listArticles(page);
	Map particlesMap = pboardService.listArticles(page);
			
	ModelAndView mav = new ModelAndView();
	mav.setViewName(viewName);
	mav.addObject("garticlesMap",garticlesMap);
	mav.addObject("harticlesMap",harticlesMap);
	mav.addObject("particlesMap",particlesMap);
	mav.addObject("articlesMap",articlesMap);
	return mav;
	
}
@Override
@RequestMapping(value="/member/listMembers.do",method = RequestMethod.GET)
public ModelAndView listMembers(RedirectAttributes rAttr,HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	memberVO = (MemberVO)session.getAttribute("member");
	ModelAndView mav;
//	System.out.println(session);
//	System.out.println(session.getAttribute("member"));
//	System.out.println(session.getAttribute("isLogOn"));
	//ù��° ���ǿ��� false�̱� ������ �ٷ� �ؿ� else �������� �̵�
	if(memberVO != null && memberVO.getId().equals("wnsgud14")) {
		String viewName = (String)request.getAttribute("viewName");
		List membersList = memberService.listMembers();
		mav = new ModelAndView(viewName);
		mav.addObject("membersList",membersList);
		mav.addObject("isManager","true");
	}else {
		rAttr.addFlashAttribute("isManager","false");
		mav = new ModelAndView("redirect:/main.do");
	}
	return mav;
}

@Override
@RequestMapping(value="/member/addMember.do",method = RequestMethod.POST)
public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.setCharacterEncoding("utf-8");
	int result =memberService.addMember(member);
	ModelAndView mav = new ModelAndView("redirect:/main.do");
	return mav;
}

@Override
@ResponseBody
@RequestMapping(value="/member/removeMember.do",method = RequestMethod.GET)
public ResponseEntity removeMember(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.setCharacterEncoding("utf-8");
	ResponseEntity resEnt = null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	
	try {
		MemberVO member = (MemberVO)request.getSession().getAttribute("member");
		if(member==null||!member.getId().equals("wnsgud14")) throw new Exception("�߸��� ���� �õ�");
		else if(id.equals("wnsgud14")) throw new RuntimeException("������ ���� �õ�");
		
	memberService.removeMember(id);
	
	String message = "<script>"
			+ "alert('������ �Ϸ��߽��ϴ�.');"
			+ "location.href='"+request.getContextPath()+"/main.do';"
					+ "</script>";
	resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.OK);
	}catch(RuntimeException e) {
		String message = "<script>"
				+ "alert('�����ڴ� ���� �� �� �����ϴ�.');"
				+ "location.href='"+request.getContextPath()+"/main.do';"
						+ "</script>";
		resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
		e.printStackTrace();
	}catch(Exception e) {
		String message = "<script>"
				+ "alert('������ �����Ͽ����ϴ�..');"
				+ "location.href='"+request.getContextPath()+"/main.do';"
						+ "</script>";
		resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
		e.printStackTrace();
	}
	return resEnt;
}

@Override
@RequestMapping(value="/member/login.do",method = RequestMethod.POST)
public ModelAndView login(@ModelAttribute("member") MemberVO member,RedirectAttributes rAttr,HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member);
	if(memberVO !=null) {
		HttpSession session = request.getSession();
		session.setAttribute("member", memberVO);
		session.setAttribute("isLogOn", true);
		String action = (String)session.getAttribute("action");
		session.removeAttribute("action");
		if(action!=null) {
			mav.setViewName("redirect:"+action);
		}else {
			String previous = (String)session.getAttribute("previous");
			mav.setViewName("redirect:"+previous);
		}
	}else {
		rAttr.addFlashAttribute("result","loginFailed");
		mav.setViewName("redirect:/member/loginForm.do");
	}
	return mav;
}


@Override
@RequestMapping(value="/member/logout.do",method = RequestMethod.GET)
public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		mav.setViewName("redirect:/main.do");	
	return mav;
}

@Override
@RequestMapping(value="/member/modMember.do",method=RequestMethod.GET)
public ModelAndView modMember(RedirectAttributes rAttribute, @RequestParam("id") String id, HttpServletRequest request) throws Exception{
	String viewName = (String)request.getAttribute("viewName");	
	HttpSession session = request.getSession();
	ModelAndView mav;
	//�α����� �ȵǾ�������
	if(session.getAttribute("member")==null) {
		mav = new ModelAndView("redirect:/main.do");
	}else if( ((MemberVO)session.getAttribute("member")).getId().equals(id) || ((MemberVO)session.getAttribute("member")).getId().equals("wnsgud14") ) { //�������� �����϶�
		//���� ���� ���̵�� ��û������� ���ǿ��ִ� member ��ü�� ����ȭ�� �����
		MemberVO memberVO = memberService.selectMember(id);
		 mav = new ModelAndView(viewName);
		 mav.addObject("member",memberVO);
	}else {//�α����� �Ǿ������� �ڱ� ���������� �ƴ� �ٸ� ���� �������� ���������� �����϶�(url�� ����)
		rAttribute.addFlashAttribute("abnormalConnect","true");
		mav = new ModelAndView("redirect:/main.do");
	}
	
	return mav;
}

@Override
@RequestMapping(value="/member/modify.do",method=RequestMethod.POST)
@ResponseBody
public ResponseEntity modify(@ModelAttribute("member") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception {
	ResponseEntity resEnt = null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	String id = request.getParameter("id");
	memberVO.setId(id);
	
	try {
	memberService.modifyMember(memberVO);
	request.getSession().setAttribute("member",memberVO);
	String message = "<script>"
			+ "alert('������ �Ϸ��߽��ϴ�.');"
			+ "location.href='"+request.getContextPath()+"/main.do';"
					+ "</script>";
	resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.OK);
	}catch(Exception e) {
		String message = "<script>"
				+ "alert('������ ������ �߻��߽��ϴ�.');"
				+ "location.href='"+request.getContextPath()+"/main.do';"
						+ "</script>";
		resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	return resEnt;
}

@Override
@RequestMapping(value="/member/*Form.do")
public ModelAndView form(@RequestParam(value="action", required = false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
	String viewName = (String)request.getAttribute("viewName");
	HttpSession session = request.getSession();
	session.setAttribute("action", action);

	ModelAndView mav = new ModelAndView(viewName);
	return mav;
}


}
