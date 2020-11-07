package com.myspring.WebPro.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.WebPro.board.vo.ArticleVO;

public interface BoardDAO {
	public List selectAllArticlesList(Map page) throws DataAccessException;
	public int selectTotalArticle() throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public ArticleVO selectArticle(int articleNO) throws DataAccessException;
	public void modArticle(Map articleMap) throws DataAccessException;
	public void removeArticle(int articleNO) throws DataAccessException;
	public void viewsUpdate(int articleNO) throws DataAccessException;
	public void insertNewImage(Map articleMap) throws DataAccessException;
	public List selectImageFileList(int articleNO) throws DataAccessException;
	public int modImageFileList(Map articleMap) throws DataAccessException;
	
	public List selectTotalComment(List<ArticleVO> articleList) throws DataAccessException;
	public void addComment(Map commentMap) throws DataAccessException;
	public List selectAllComment(int articleNO) throws DataAccessException;
}
