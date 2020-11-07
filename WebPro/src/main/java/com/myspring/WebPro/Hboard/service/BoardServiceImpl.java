package com.myspring.WebPro.Hboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.WebPro.Hboard.dao.BoardDAO;
import com.myspring.WebPro.Hboard.vo.ArticleVO;
import com.myspring.WebPro.Hboard.vo.CommentVO;
import com.myspring.WebPro.Hboard.vo.ImageVO;

@Service("hboardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDAO hboardDAO;
	
	@Override
	public Map listArticles(Map map) throws Exception{
		Map articleMap = new HashMap();
		List<ArticleVO> articlesList = hboardDAO.selectAllArticlesList(map);
		List<Integer> commentCountList = hboardDAO.selectTotalComment(articlesList);
		int totArticles = hboardDAO.selectTotalArticle();
		articleMap.put("commentCountList", commentCountList);
		articleMap.put("articlesList", articlesList);
		articleMap.put("totArticles", totArticles);
		return articleMap;
	}
	
	@Override
	public Map viewArticle(int articleNO) throws Exception {
		Map articleMap = new HashMap();
		hboardDAO.viewsUpdate(articleNO);
		ArticleVO articleVO = hboardDAO.selectArticle(articleNO);
		List<ImageVO> imageFileList = hboardDAO.selectImageFileList(articleNO);
		List<CommentVO> commentList = hboardDAO.selectAllComment(articleNO);
		 articleMap.put("article", articleVO);
		 articleMap.put("imageFileList", imageFileList);
		 articleMap.put("commentList",commentList);
		return articleMap;
	}
	
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		int articleNO = hboardDAO.insertNewArticle(articleMap);
		hboardDAO.insertNewImage(articleMap);
		return articleNO;
	}
	
	@Override
	public void modArticle(Map articleMap) throws Exception {
		hboardDAO.modArticle(articleMap);
		List originalFileList = (List)articleMap.get("originalFileList");
		List uploadFileList = (List)articleMap.get("uploadFileList");
		int articleNO = Integer.parseInt((String)articleMap.get("articleNO"));
		int count =0;
		
		//�������� ��ϵ� ������ �ش� ���� ����
		if(originalFileList.size()!=0&&originalFileList!=null) {
			count = hboardDAO.modImageFileList(articleMap);	
		}
		System.out.println("�������� ������ ���������� �����");
		
		//������ �ƴ� ����â���� �߰��� �̹������� �ϴ� ����
		if(uploadFileList.size()>count) {
			Map tempMap = new HashMap();
			List<String> tempList = uploadFileList.subList(count, uploadFileList.size());
			List<ImageVO> imageFileList = new ArrayList<ImageVO>();
			for(String fileName : tempList) {
				ImageVO imageVO = new ImageVO();
				imageVO.setImageFileName(fileName);
				imageFileList.add(imageVO);
			}
			tempMap.put("imageFileList", imageFileList);
			tempMap.put("articleNO", articleNO);
			hboardDAO.insertNewImage(tempMap);
		}
		System.out.println("���� �߰��� ���������� �����");
	}
	
	@Override
	public void removeArticle(int articleNO) throws Exception {
		hboardDAO.removeArticle(articleNO);
	}
	
	@Override
	public void addComment(Map commentMap) throws Exception {
		hboardDAO.addComment(commentMap);
	}
}
