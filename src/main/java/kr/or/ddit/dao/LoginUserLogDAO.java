package kr.or.ddit.dao;

import java.sql.SQLException;

import kr.or.ddit.dto.LoginUserLogVO;

public interface LoginUserLogDAO {

	public void insertLoginUserLog(LoginUserLogVO logVO)throws SQLException;
	public void deleteLoginUserLog()throws SQLException;
	
}
