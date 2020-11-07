package com.myspring.WebPro.board.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.WebPro.board.vo.ArticleVO;
import com.myspring.WebPro.board.vo.CommentVO;
import com.myspring.WebPro.board.vo.ImageVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List selectAllArticlesList(Map page) throws DataAccessException{
		List<ArticleVO> articlesList = sqlSession.selectList("mapper.board.selectAllArticlesList",page);
		return articlesList;
	}
	
	@Override
	public int selectTotalArticle() throws DataAccessException{
		int totalArticle = sqlSession.selectOne("mapper.board.selectTotalArticle");
		return totalArticle;
	}
	
	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert("mapper.board.insertNewArticle",articleMap);
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
		sqlSession.insert("mapper.board.insertNewImage",imageFileList);
		}
	}
	
	//�Խñ� �ϳ� ã��
	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectArticle",articleNO);
	}

	//�� �Խñۿ� ���� �̹��� ���� ����Ʈ ã��
	@Override
	public List selectImageFileList(int articleNO) throws DataAccessException {
		return sqlSession.selectList("mapper.board.selectImageFileList",articleNO);
	}
	
	@Override
	public void modArticle(Map articleMap) throws DataAccessException {
		sqlSession.update("mapper.board.updateArticle",articleMap);
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
			sqlSession.update("mapper.board.updateImageFile",imageMap);
			i++;
		}
		
		return i;
	}
	
	@Override
	public void removeArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
		
	}
	
	@Override
	public void viewsUpdate(int articleNO) throws DataAccessException {
		sqlSession.update("mapper.board.viewsUpdate",articleNO);
	}
	
	
	
	// �Ʒ��� �� �Խñ� ��ȣ ���ϴ� �޼���
	private int selectNewArticleNO() throws DataAccessException{
		int articleNO = sqlSession.selectOne("mapper.board.selectNewArticleNO");
		articleNO++;
		return articleNO;
		
	}
	
	private int selectNewImageFileNO() throws DataAccessException{
		int imageFileNO = sqlSession.selectOne("mapper.board.selectNewImageFileNO");
		imageFileNO++;
		return imageFileNO;
		
	}
	
	private int selectNewCommentNO() throws DataAccessException{
		int commentNO = sqlSession.selectOne("mapper.board.selectNewCommentNO");
		return commentNO;
	}
	
	@Override
	public void addComment(Map commentMap) throws DataAccessException {
		int commentNO = selectNewCommentNO();
		commentNO++;
		commentMap.put("commentNO",commentNO);
		sqlSession.insert("mapper.board.insertComment",commentMap);
	}
	

	@Override
	public List selectAllComment(int articleNO) throws DataAccessException {
		List<CommentVO> commentList = sqlSession.selectList("mapper.board.selectAllComment",articleNO);
		return commentList;
	}
	
	//�Խñ� �� �ڸ�Ʈ ���� ���°�
	@Override
	public List selectTotalComment(List<ArticleVO> articleList) throws DataAccessException {
		List<Integer> commentCountList = new ArrayList<Integer>();
		for(ArticleVO articleVO : articleList) {
			int count = sqlSession.selectOne("mapper.board.commentCount", articleVO);
			commentCountList.add(count);
		}
		return commentCountList;
		
	}
}
