package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;
import com.dgit.domain.PhotoVO;
import com.dgit.persistence.MemberDao;
import com.dgit.persistence.PhotoDao;

@Repository
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao dao;
	
	@Autowired
	private PhotoDao photoDao;
	
	
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

	@Override
	public String checkID(String id) throws Exception {
		return dao.checkID(id);
	}

	@Override
	public MemberVO selectByNum(int num) throws Exception {
		List<PhotoVO> photo = photoDao.selectByNum(num);
		MemberVO vo = dao.selectByNum(num);
				
		if(photo.size() > 0){
			
			PhotoVO[] images = photo.toArray(new PhotoVO[photo.size()]);			
			
			vo.setImages(images);
		}

		return vo;
	}

}
