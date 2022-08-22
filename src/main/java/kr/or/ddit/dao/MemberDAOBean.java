package kr.or.ddit.dao;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;

public interface MemberDAOBean {

	// 회원리스트 조회
	List<MemberVO> selectMemberList() throws Exception;

	List<MemberVO> selectMemberList(SearchCriteria cri) throws Exception;

	// 일반 리스트 전체 개수
	int selectMemberListCount() throws Exception;

	// 검색 결과의 전체 리스트 개수
	int selectMemberListCount(SearchCriteria cri) throws Exception;

	// 회원정보 조회
	MemberVO selectMemberById(String id) throws SQLException;

	MemberVO selectMemberByPicture(String picture) throws SQLException;

	// 회원 추가
	public void insertMember(MemberVO member) throws SQLException;

	// 회원 수정
	public void updateMember(MemberVO member) throws SQLException;

	// 회원정보 삭제
	void deleteMember(String id) throws SQLException;

	// 회원 활성화
	void enabledMember(String id, int enabled) throws SQLException;

}
