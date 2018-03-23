package com.dgit.service;

import java.util.List;

import com.dgit.domain.MemberVO;

public interface MemberService {
	public List<MemberVO> list() throws Exception;
	public void update(MemberVO member) throws Exception;
	public void insert(MemberVO member) throws Exception;
	public void delete(MemberVO member) throws Exception;
	public MemberVO readWithPW(String userid, String userpw) throws Exception;
}
