package com.myspring.WebPro.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.WebPro.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
@Override
public List SelectAllMembers() throws DataAccessException {
	List<MemberVO> membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
	return membersList; 
			
}

@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.member.insertMember",memberVO);
		return result;
	}

@Override
public int deleteMember(String id) throws DataAccessException {
	int result = sqlSession.delete("mapper.member.deleteMember",id);
	return result;
}

@Override
public MemberVO loginById(MemberVO memberVO) throws DataAccessException {
	MemberVO member = sqlSession.selectOne("mapper.member.loginById",memberVO);
	return member;
}

@Override
public MemberVO selectMember(String id) throws DataAccessException {
	return sqlSession.selectOne("mapper.member.selectMember",id);
}

@Override
public void modify(MemberVO memberVO) throws DataAccessException {
	sqlSession.update("mapper.member.updateMember",memberVO);
	
}


}
