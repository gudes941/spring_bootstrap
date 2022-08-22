package kr.or.ddit.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.SearchCriteria;
import com.jsp.dao.SearchMemberDAO;
import com.jsp.dto.MemberVO;

public class MemberDAOBeanImpl implements MemberDAOBean {
	
	private SqlSession session;
	private SearchMemberDAO memberDAO;	
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	public void setSearchMemberDAO(SearchMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
	public List<MemberVO> selectMemberList() throws Exception {
		return memberDAO.selectMemberList(session);
	}

	@Override
	public List<MemberVO> selectMemberList(SearchCriteria cri) throws Exception {
		return memberDAO.selectSearchMemberList(session, cri);
	}

	@Override
	public int selectMemberListCount() throws Exception {
		return memberDAO.selectMemberListCount(session);
	}

	@Override
	public int selectMemberListCount(SearchCriteria cri) throws Exception {
		return memberDAO.selectSearchMemberListCount(session,cri);
	}

	@Override
	public MemberVO selectMemberById(String id) throws SQLException {
		return memberDAO.selectMemberById(session, id);
	}

	@Override
	public MemberVO selectMemberByPicture(String picture) throws SQLException {	
			MemberVO member=null;
			member=session.selectOne("Member-Mapper.selectMemberByPicture",picture);
			return member;
	}

	@Override
	public void insertMember(MemberVO member) throws SQLException {
		memberDAO.insertMember(session, member);	
	}

	@Override
	public void updateMember(MemberVO member) throws SQLException {
		memberDAO.updateMember(session, member);

	}

	@Override
	public void deleteMember(String id) throws SQLException {
		memberDAO.deleteMember(session, id);

	}

	@Override
	public void enabledMember(String id, int enabled) throws SQLException {
		memberDAO.enabledMember(session, id, enabled);		
	}

}
