package com.myspring.WebPro.Hboard.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.WebPro.Hboard.vo.ArticleVO;
import com.myspring.WebPro.Hboard.vo.CommentVO;
import com.myspring.WebPro.Hboard.vo.ImageVO;

@Repository("hboardDAO")
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List selectAllArticlesList(Map page) throws DataAccessException{
		List<ArticleVO> articlesList = sqlSession.selectList("mapper.Hboard.selectAllArticlesList",page);
		return articlesList;
	}
	
	@Override
	public int selectTotalArticle() throws DataAccessException{
		int totalArticle = sqlSession.selectOne("mapper.Hboard.selectTotalArticle");
		return totalArticle;
	}
	
	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.Hboard.insertNewArticle",articleMap);
		return articleNO;
	}
	
	@Override
	public void insertNewImage(Map articleMap) throws DataAccessException {
		List<ImageVO> imageFileList=(ArrayList)articleMap.get("imageFileList");
		if(imageFileList!=null&&imageFileList.size()!=0) {
		int articleNO=(Integer)articleMap.get("articleNO");
		int imageFileNO = selectNewImageFileNO();
		for(ImageVO imageVO : imageFileList) {
			imageVO.setArticleNO(articleNO);
			imageVO.setImageFileNO(imageFileNO++);
		}
		sqlSession.insert("mapper.Hboard.insertNewImage",imageFileList);
		}
	}
	
	//게시글 하나 찾기
	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.Hboard.selectArticle",articleNO);
	}

	//한 게시글에 대한 이미지 파일 리스트 찾기
	@Override
	public List selectImageFileList(int articleNO) throws DataAccessException {
		return sqlSession.selectList("mapper.Hboard.selectImageFileList",articleNO);
	}
	
	@Override
	public void modArticle(Map articleMap) throws DataAccessException {
		sqlSession.update("mapper.Hboard.updateArticle",articleMap);
	}
	
	@Override
	public int modImageFileList(Map articleMap) throws DataAccessException {
		List originalFileList = (List)articleMap.get("originalFileList");
		List uploadFileList = (List)articleMap.get("uploadFileList");
		String articleNO = (String)articleMap.get("articleNO");
		int i=0;
		while(i<originalFileList.size()) {
			Map imageMap = new HashMap();
			imageMap.put("articleNO",articleNO);
			imageMap.put("imageFileName",uploadFileList.get(i));
			imageMap.put("originalFileName",originalFileList.get(i));
			sqlSession.update("mapper.Hboard.updateImageFile",imageMap);
			i++;
		}
		
		return i;
	}
	
	@Override
	public void removeArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.Hboard.deleteArticle", articleNO);
		
	}
	
	@Override
	public void viewsUpdate(int articleNO) throws DataAccessException {
		sqlSession.update("mapper.Hboard.viewsUpdate",articleNO);
	}
	
	
	
	// 아래는 새 게시글 번호 구하는 메서드
	private int selectNewArticleNO() throws DataAccessException{
		int articleNO = sqlSession.selectOne("mapper.Hboard.selectNewArticleNO");
		articleNO++;
		return articleNO;
		
	}
	
	private int selectNewImageFileNO() throws DataAccessException{
		int imageFileNO = sqlSession.selectOne("mapper.Hboard.selectNewImageFileNO");
		imageFileNO++;
		return imageFileNO;
		
	}
	
	private int selectNewCommentNO() throws DataAccessException{
		int commentNO = sqlSession.selectOne("mapper.Hboard.selectNewCommentNO");
		return commentNO;
	}
	
	@Override
	public void addComment(Map commentMap) throws DataAccessException {
		int commentNO = selectNewCommentNO();
		commentNO++;
		commentMap.put("commentNO",commentNO);
		sqlSession.insert("mapper.Hboard.insertComment",commentMap);
	}
	
	@Override
	public List selectTotalComment(List<ArticleVO> articleList) throws DataAccessException {
		List<Integer> commentCountList = new ArrayList<Integer>();
		for(ArticleVO articleVO : articleList) {
			int count = sqlSession.selectOne("mapper.Hboard.commentCount", articleVO);
			commentCountList.add(count);
		}
		return commentCountList;
		
	}
	
	@Override
	public List selectAllComment(int articleNO) throws DataAccessException {
		List<CommentVO> commentList = sqlSession.selectList("mapper.Hboard.selectAllComment",articleNO);
		return commentList;
	}
}
