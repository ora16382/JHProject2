package com.myspring.WebPro.Pboard.service;

import java.util.Map;

import com.myspring.WebPro.Pboard.vo.CommentVO;

public interface BoardService {
	public Map listArticles(Map map) throws Exception;
	public int addNewArticle(Map articleMap) throws Exception;
	public Map viewArticle(int articleNO) throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	public void removeArticle(int articleNO) throws Exception;
	public void addComment(Map commentMap) throws Exception;
}
