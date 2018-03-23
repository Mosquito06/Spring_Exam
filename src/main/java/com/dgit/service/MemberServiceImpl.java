package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDao;

@Repository
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao dao;
	
	@Override
	public List<MemberVO> list() throws Exception {
		return dao.list();
	}

	@Override
	public void update(MemberVO member) throws Exception {
		dao.update(member);

	}

	@Override
	public void insert(MemberVO member) throws Exception {
		dao.insert(member);
	}

	@Override
	public void delete(MemberVO member) throws Exception {
		dao.delete(member);
	}

	@Override
	public MemberVO readWithPW(String userid, String userpw) throws Exception {
		return dao.readWithPW(userid, userpw);
	}

}
