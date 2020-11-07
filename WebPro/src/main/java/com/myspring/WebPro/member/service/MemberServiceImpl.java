package com.myspring.WebPro.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.WebPro.member.dao.MemberDAO;
import com.myspring.WebPro.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
	public List<MemberVO> listMembers() throws DataAccessException {
		List<MemberVO> membersList = memberDAO.SelectAllMembers();;
		return membersList;
	}
	
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException{
		return memberDAO.addMember(memberVO);
	}
	
	@Override
	public int removeMember(String id) throws DataAccessException{
		return memberDAO.deleteMember(id);
	}
	
	public MemberVO login(MemberVO memberVO) throws DataAccessException{
		return memberDAO.loginById(memberVO);
	}
	
	@Override
	public MemberVO selectMember(String id) throws DataAccessException {
		return memberDAO.selectMember(id);
	}
	
	@Override
	public void modifyMember(MemberVO memberVO) throws DataAccessException {
		memberDAO.modify(memberVO);
	}
}
