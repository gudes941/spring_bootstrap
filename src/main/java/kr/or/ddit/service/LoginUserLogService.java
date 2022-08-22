package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.dto.LoginUserLogVO;

public interface LoginUserLogService {
	
	public void write(List<LoginUserLogVO>logVO)throws Exception;

}
