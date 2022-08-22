package kr.or.ddit.dao;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.Criteria;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.BoardVO;

public interface BoardDAOBean {
	List<BoardVO> selectBoardCriteria( SearchCriteria cri) throws SQLException;
	
	int selectBoardCriteriaTotalCount( SearchCriteria cri) throws SQLException;
	
	
	BoardVO selectBoardByBno ( int bno) throws SQLException;
	
	BoardVO selectBoardByImage ( String imageFile) throws SQLException;
	
	void insertBoard( BoardVO board) throws SQLException;
	
	void updateBoard( BoardVO board) throws SQLException;
	
	void deleteBoard( int bno) throws SQLException;

	
	void increaseViewCnt( int bno) throws SQLException;
	
	int selectBoardSeqNext() throws SQLException;
}
