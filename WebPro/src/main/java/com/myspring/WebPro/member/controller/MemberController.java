package com.myspring.WebPro.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.WebPro.member.vo.MemberVO;

public interface MemberController {
	public ModelAndView listMembers(RedirectAttributes rAttr,HttpServletRequest request, HttpServletResponse response) throws Exception ;
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity removeMember(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception ;
	public ModelAndView form(@RequestParam("action") String action, HttpServletRequest request, HttpServletResponse response) throws Exception ;
	public ModelAndView login(@ModelAttribute("member") MemberVO member,RedirectAttributes rAttr,HttpServletRequest request, HttpServletResponse response) throws Exception ;
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity modify(@ModelAttribute("member") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView modMember(RedirectAttributes rAttribute, @RequestParam("id") String id, HttpServletRequest request) throws Exception;
}
