package com.myspring.WebPro.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.WebPro.member.vo.MemberVO;

public interface MemberService {
	public List<MemberVO> listMembers() throws DataAccessException;
	public int removeMember(String id) throws DataAccessException;
	public int addMember(MemberVO memberVO) throws DataAccessException;
	public MemberVO login(MemberVO memberVO) throws DataAccessException;
	public MemberVO selectMember(String id) throws DataAccessException;
	public void modifyMember(MemberVO memberVO) throws DataAccessException;
}