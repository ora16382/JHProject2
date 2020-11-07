package com.myspring.WebPro.Hboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.WebPro.Hboard.service.BoardService;
import com.myspring.WebPro.Hboard.vo.ArticleVO;
import com.myspring.WebPro.Hboard.vo.CommentVO;
import com.myspring.WebPro.Hboard.vo.ImageVO;
import com.myspring.WebPro.member.vo.MemberVO;

@Controller("hboardController")
public class BoardControllerImpl implements BoardController{
	private static Logger logger = LoggerFactory.getLogger(BoardControllerImpl.class);
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image\\humor";
	
	@Autowired
	BoardService hboardService;
	
	@Autowired
	ArticleVO harticleVO;
	
	@Override
	@RequestMapping(value="/hboard/listArticles.do",method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false) String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Integer> page = new HashMap<String, Integer>();
		int section = Integer.parseInt(((_section==null)?"1":_section));
		int pageNum = Integer.parseInt(((_pageNum==null)?"1":_pageNum));
		page.put("section", section); // 밑에꺼와 상동
		page.put("pageNum", pageNum); // pageNum 으로 받은 매개변수 값이 없으면 1로 초기화

		String viewName = (String)request.getAttribute("viewName");
		Map articlesMap = hboardService.listArticles(page);
		articlesMap.put("section", section);
		articlesMap.put("pageNum", pageNum);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesMap",articlesMap);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/hboard/addNewArticle.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("member");
		if(memberVO == null) return new ResponseEntity("<script>location.href='"+multipartRequest.getContextPath()+"/hboard/listArticles.do';</script>",responseHeaders,HttpStatus.BAD_REQUEST);
		
		String id = memberVO.getId();
		
		if(articleMap.get("parentNO")==null) {
			articleMap.put("parentNO", 0);
		}
		articleMap.put("id",id);
		
		List<String> fileList = upload(multipartRequest);
		List<ImageVO> imageFileList = new ArrayList<ImageVO>();
		
		if(fileList != null && fileList.size()!=0) {
			for(String fileName : fileList) {
				ImageVO imageVO = new ImageVO();
				imageVO.setImageFileName(fileName);
				imageFileList.add(imageVO);
			}
			articleMap.put("imageFileList", imageFileList);
		}
		
		
		String message;
		ResponseEntity resEnt = null;
		
		
		try {
		int articleNO = hboardService.addNewArticle(articleMap);
		if(imageFileList!=null&&imageFileList.size()!=0) {
			for(ImageVO imageVO : imageFileList) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageVO.getImageFileName());
			File destFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
			FileUtils.moveFileToDirectory(srcFile, destFile, true);
			}
		}
		message = "<script>"
				+ "location.href='"+multipartRequest.getContextPath()+"/hboard/listArticles.do';"
						+ "</script>";
		resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.CREATED);
		}catch(Exception e) {
			if(imageFileList!=null&&imageFileList.size()!=0) {
				for(ImageVO imageVO : imageFileList) {
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageVO.getImageFileName());
					srcFile.delete();
				}
			}
			
			
			message = "<script>"
					+ "alert('오류가 발생했습니다. 다시 시도해 주세요');"
					+ "location.href='"+multipartRequest.getContextPath()+"/hboard/articleForm.do';"
							+ "</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resEnt;
	}
	
	
	@RequestMapping(value="/hboard/viewArticle.do", method=RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		Map articleMap = hboardService.viewArticle(articleNO);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articleMap",articleMap);
		return mav;
	}
	
	@RequestMapping(value="/hboard/*Form.do", method = {RequestMethod.GET,RequestMethod.POST})
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	

	@RequestMapping(value="/hboard/modArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		//원래 파일 리스트, 수정된 항목에 대해서만 오리지날 파일 이름을 가져옴
		List<String> originalFileList = new ArrayList<String>();
		for(int i=1; i<4; i++) {
			String originalFileName= (String)articleMap.get("originalFileName"+i);
			if(originalFileName!=null) {
				originalFileList.add(originalFileName);
			}
		}
		
		List<String> uploadFileList = upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO loginMember = (MemberVO)session.getAttribute("member");
		String write = (String)articleMap.get("write");
		articleMap.put("originalFileList", originalFileList);
		articleMap.put("uploadFileList", uploadFileList);
		String articleNO = (String)articleMap.get("articleNO");
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		if(loginMember==null||!loginMember.getId().equals(write)) {
			message = "<script>"
					+ "alert('오류가 발생했습니다. 다시 시도해 주세요');"
					+ "location.href='"+multipartRequest.getContextPath()+(String)session.getAttribute("previous")+"';"
							+ "</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		try {
			hboardService.modArticle(articleMap);
			//새로운 파일을 첨부했을 경우에만 기존파일 삭제되어짐
			if(uploadFileList!=null&&uploadFileList.size()!=0) {
				for(String imageFileName : uploadFileList) {
		
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
					File destFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					if(new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+imageFileName).exists()) continue;
					FileUtils.moveFileToDirectory(srcFile, destFile, true);
				}
				
				for(String originalFileName:originalFileList) {
					File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
					oldFile.delete();	
				}
			}
			message = "<script>"
					+ "location.href='"+multipartRequest.getContextPath()+(String)session.getAttribute("previous")+"';"
							+ "</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.OK);
		}catch(Exception e) {
			for(String imageFileName : uploadFileList) {
		
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			}
			message = "<script>"
					+ "alert('오류가 발생했습니다. 다시 시도해 주세요');"
					+ "location.href='"+multipartRequest.getContextPath()+(String)session.getAttribute("previous")+"';"
							+ "</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resEnt;
	}

	
	@Override
	@RequestMapping(value="/hboard/removeArticle.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity removeArticle(@RequestParam("articleNO") int articleNO, @RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		String message;
		HttpSession session = request.getSession();
		
		try {
			if( !(((MemberVO)session.getAttribute("member")).getId().equals(id)||((MemberVO)session.getAttribute("member")).getId().equals("wnsgud14")) ) throw new Exception("잘못된 삭제 시도");
			hboardService.removeArticle(articleNO);
			File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
			FileUtils.deleteDirectory(destDir);
			message = "<script>"
					+ "location.href='"+request.getContextPath()+"/hboard/listArticles.do';"
							+ "</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.OK);
		}catch(Exception e) {
			message = "<script>"
					+ "alert('삭제중 오류가 발생하였습니다. 다시 시도해주세요');"
					+ "location.href='"+request.getContextPath()+"/hboard/listArticles.do';"
							+ "</script>";
			resEnt = new ResponseEntity(message,responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return resEnt;
	}
	
	//코멘트 추가
	@Override
	@RequestMapping(value="/hboard/addNewComment", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewComment(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		Map commentMap = new HashMap<String,String>();
		String body = request.getParameter("body");
		commentMap.put("body",  body);
		commentMap.put("articleNO", request.getParameter("articleNO"));
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("member")==null) {
			resEnt = new ResponseEntity("noLogin",responseHeaders,HttpStatus.OK);
			return resEnt;
		}

		String message=null;
		String id = ((MemberVO)session.getAttribute("member")).getId();
		commentMap.put("id", id);
			try {
				hboardService.addComment(commentMap);
				JSONObject obj = new JSONObject();
				obj.put("id", id);
				obj.put("body", body);
				String commentVO = obj.toJSONString();
				resEnt = new ResponseEntity(commentVO,responseHeaders,HttpStatus.OK);
			} catch (Exception e) {
				resEnt = new ResponseEntity("Failed",responseHeaders,HttpStatus.OK);
				e.printStackTrace();
			}
		return resEnt;
	}
	
	public List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames(); // file dom 들의 매개변수 이름을 가져옴
		//첨부파일 없이 전송했을 경우 아래 반복문은 실행되어지나 originalFileName 은 "" 이고, mFile 은 size가 0
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName = mFile.getOriginalFilename();
			if(!originalFileName.equals("")) {
				fileList.add(originalFileName);
				}
			File file = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+originalFileName);
			if(mFile.getSize()!=0) { // file null check
				if(!file.getParentFile().exists()) file.getParentFile().mkdirs();  // 목적 파일에 해당 파일이 없으면
				mFile.transferTo(file);
				}
		}
		return fileList;
	}
}
