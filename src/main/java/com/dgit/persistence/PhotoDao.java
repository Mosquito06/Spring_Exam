package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.PhotoVO;

public interface PhotoDao {
	public List<PhotoVO> list() throws Exception;
	public void update(PhotoVO photo) throws Exception;
	public void insert(PhotoVO photo) throws Exception;
	public void delete(PhotoVO photo) throws Exception;
	public PhotoVO selectByNum(int num) throws Exception;
}
