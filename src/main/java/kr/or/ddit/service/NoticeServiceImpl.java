package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.Criteria;
import com.jsp.command.PageMaker;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.NoticeVO;
import com.jsp.service.NoticeService;

import kr.or.ddit.dao.NoticeDAOBean;

public class NoticeServiceImpl implements NoticeService{
	
	private NoticeDAOBean noticeDAOBean;

	public void setNoticeDAO(NoticeDAOBean noticeDAOBean) {
		this.noticeDAOBean = noticeDAOBean;
	}

	@Override
	public Map<String, Object> getNoticeList(Criteria cri) throws SQLException {
		
		SearchCriteria searchCri = (SearchCriteria)cri;
		
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 현재 page 번호에 맞는 리스트를 perPageNum 개수 만큼 가져오기.
		List<NoticeVO> noticeList = noticeDAOBean.selectSearchNoticeList(searchCri);

		// 전체 board 개수
		int totalCount = noticeDAOBean.selectSearchNoticeListCount(searchCri);

		// PageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);

		dataMap.put("noticeList", noticeList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}

	@Override
	public NoticeVO getNotice(int nno) throws SQLException {
		noticeDAOBean.increaseViewCount(nno);
		
		NoticeVO board = noticeDAOBean.selectNoticeByNno(nno);
		//if(true)throw new SQLException();
		return board;
	}

	@Override
	public NoticeVO getNoticeForModify(int nno) throws SQLException {
		NoticeVO board = noticeDAOBean.selectNoticeByNno(nno);
		
		//if(true)throw new SQLException();
		return board;
	}

	@Override
	public void regist(NoticeVO notice) throws SQLException {

		int nno = noticeDAOBean.selectNoticeSequenceNextValue();
		notice.setNno(nno);
		noticeDAOBean.insertNotice(notice);
	}

	@Override
	public void modify(NoticeVO notice) throws SQLException {

		noticeDAOBean.updateNotice(notice);
	}

	@Override
	public void remove(int nno) throws SQLException {

		noticeDAOBean.deleteNotice(nno);
	}
}
