package com.myspring.WebPro.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.WebPro.member.vo.MemberVO;

public interface MemberDAO {
	public List SelectAllMembers() throws DataAccessException;
	public int addMember(MemberVO memberVO) throws DataAccessException;
	public int deleteMember(String id) throws DataAccessException;
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
	public MemberVO selectMember(String id) throws DataAccessException;
	public void modify(MemberVO memberVO) throws DataAccessException; 
}
