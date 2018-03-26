package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.PhotoVO;
import com.dgit.persistence.PhotoDao;

@Repository
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private PhotoDao dao;
	
	
	@Override
	public List<PhotoVO> list() throws Exception {
		return dao.list();
	}

	@Override
	public void update(PhotoVO photo) throws Exception {
		dao.update(photo);

	}

	@Override
	public void insert(PhotoVO photo) throws Exception {
		dao.insert(photo);

	}

	@Override
	public void delete(PhotoVO photo) throws Exception {
		dao.delete(photo);

	}

	@Override
	public List<PhotoVO> selectByNum(int num) throws Exception {
		return dao.selectByNum(num);
	}

	

}
