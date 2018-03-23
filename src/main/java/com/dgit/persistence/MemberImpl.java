package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;

@Repository
public class MemberImpl implements MemberDao {
	public static final String NAMESPACE = "com.dgit.persistence.MemberDao";
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public List<MemberVO> list() throws Exception {
		return sqlSession.selectList(NAMESPACE + ".list");
	}

	@Override
	public void update(MemberVO member) throws Exception {
		sqlSession.update(NAMESPACE + ".update", member);

	}

	@Override
	public void insert(MemberVO member) throws Exception {
		sqlSession.insert(NAMESPACE + ".insert", member);

	}

	@Override
	public void delete(MemberVO member) throws Exception {
		sqlSession.delete(NAMESPACE + ".delete", member);

	}

	@Override
	public MemberVO readWithPW(String userid, String userpw) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		
		return sqlSession.selectOne(NAMESPACE + ".readWithPw", map);
	}

}
