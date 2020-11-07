package com.myspring.WebPro.Gboard.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.WebPro.Gboard.vo.ArticleVO;
import com.myspring.WebPro.Gboard.vo.CommentVO;
import com.myspring.WebPro.Gboard.vo.ImageVO;

@Repository("gboardDAO")
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List selectAllArticlesList(Map page) throws DataAccessException{
		List<ArticleVO> articlesList = sqlSession.selectList("mapper.Gboard.selectAllArticlesList",page);
		return articlesList;
	}
	
	@Override
	public int selectTotalArticle() throws DataAccessException{
		int totalArticle = sqlSession.selectOne("mapper.Gboard.selectTotalArticle");
		return totalArticle;
	}
	
	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.Gboard.insertNewArticle",articleMap);
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
		sqlSession.insert("mapper.Gboard.insertNewImage",imageFileList);
		}
	}
	
	//게시글 하나 찾기
	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.Gboard.selectArticle",articleNO);
	}

	//한 게시글에 대한 이미지 파일 리스트 찾기
	@Override
	public List selectImageFileList(int articleNO) throws DataAccessException {
		return sqlSession.selectList("mapper.Gboard.selectImageFileList",articleNO);
	}
	
	@Override
	public void modArticle(Map articleMap) throws DataAccessException {
		sqlSession.update("mapper.Gboard.updateArticle",articleMap);
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
			sqlSession.update("mapper.Gboard.updateImageFile",imageMap);
			i++;
		}
		
		return i;
	}
	
	@Override
	public void removeArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.Gboard.deleteArticle", articleNO);
		
	}
	
	@Override
	public void viewsUpdate(int articleNO) throws DataAccessException {
		sqlSession.update("mapper.Gboard.viewsUpdate",articleNO);
	}
	
	
	
	// 아래는 새 게시글 번호 구하는 메서드
	private int selectNewArticleNO() throws DataAccessException{
		int articleNO = sqlSession.selectOne("mapper.Gboard.selectNewArticleNO");
		articleNO++;
		return articleNO;
		
	}
	
	private int selectNewImageFileNO() throws DataAccessException{
		int imageFileNO = sqlSession.selectOne("mapper.Gboard.selectNewImageFileNO");
		imageFileNO++;
		return imageFileNO;
		
	}
	
	private int selectNewCommentNO() throws DataAccessException{
		int commentNO = sqlSession.selectOne("mapper.Gboard.selectNewCommentNO");
		return commentNO;
	}
	
	@Override
	public void addComment(Map commentMap) throws DataAccessException {
		int commentNO = selectNewCommentNO();
		commentNO++;
		commentMap.put("commentNO",commentNO);
		sqlSession.insert("mapper.Gboard.insertComment",commentMap);
	}
	
	@Override
	public List selectTotalComment(List<ArticleVO> articleList) throws DataAccessException {
		List<Integer> commentCountList = new ArrayList<Integer>();
		for(ArticleVO articleVO : articleList) {
			int count = sqlSession.selectOne("mapper.Gboard.commentCount", articleVO);
			commentCountList.add(count);
		}
		return commentCountList;
		
	}
	
	@Override
	public List selectAllComment(int articleNO) throws DataAccessException {
		List<CommentVO> commentList = sqlSession.selectList("mapper.Gboard.selectAllComment",articleNO);
		return commentList;
	}
}
