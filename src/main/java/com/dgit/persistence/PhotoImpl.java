package com.dgit.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.PhotoVO;

@Repository
public class PhotoImpl implements PhotoDao {
	private final String NAMESPACE = "com.dgit.persistence.PhotoDao";
	
	 
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<PhotoVO> list() throws Exception {
		return sqlSession.selectList(NAMESPACE + ".list");
	}

	@Override
	public void update(PhotoVO photo) throws Exception {
		sqlSession.update(NAMESPACE + ".update", photo);
	}

	@Override
	public void insert(PhotoVO photo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insert", photo);

	}

	@Override
	public void delete(PhotoVO photo) throws Exception {
		sqlSession.delete(NAMESPACE + ".delete", photo);
	}

	@Override
	public List<PhotoVO> selectByNum(int num) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectByNum", num);
	}

}
