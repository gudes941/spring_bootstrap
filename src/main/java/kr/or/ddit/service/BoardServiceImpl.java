package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.Criteria;
import com.jsp.command.PageMaker;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.BoardVO;
import com.jsp.service.BoardService;

import kr.or.ddit.dao.BoardDAOBean;
import kr.or.ddit.dao.ReplyDAOBean;

public class BoardServiceImpl implements BoardService {

	private BoardDAOBean boardDAOBean;

	public void setBoardDAOBean(BoardDAOBean boardDAOBean) {
		this.boardDAOBean = boardDAOBean;
	}

	private ReplyDAOBean replyDAOBean;

	public void setReplyDAOBean(ReplyDAOBean replyDAOBean) {
		this.replyDAOBean = replyDAOBean;
	}

	@Override
	public BoardVO getBoard(int bno) throws SQLException {
		BoardVO board = boardDAOBean.selectBoardByBno(bno);
		boardDAOBean.increaseViewCnt(bno);
		return board;
	}

	@Override
	public BoardVO getBoardForModify(int bno) throws SQLException {
		BoardVO board = boardDAOBean.selectBoardByBno(bno);
		return board;
	}

	@Override
	public Map<String, Object> getBoardList(Criteria cri) throws SQLException {

		SearchCriteria searchCri = (SearchCriteria) cri;

		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 현재 page 번호에 맞는 리스트를 perPageNum 개수 만큼 가져오기.
		List<BoardVO> boardList = boardDAOBean.selectBoardCriteria(searchCri);
		// reply count 입력
		for (BoardVO board : boardList) {
			int replycnt = replyDAOBean.countReply(board.getBno());
			board.setReplycnt(replycnt);
		}
		// 전체 board 개수
		int totalCount = boardDAOBean.selectBoardCriteriaTotalCount(searchCri);

		// PageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);

		dataMap.put("boardList", boardList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}

	@Override
	public void regist(BoardVO board) throws SQLException {

		int bno = boardDAOBean.selectBoardSeqNext();
		board.setBno(bno);
		boardDAOBean.insertBoard(board);
	}

	@Override
	public void modify(BoardVO board) throws SQLException {

		boardDAOBean.updateBoard(board);
	}

	@Override
	public void remove(int bno) throws SQLException {

		boardDAOBean.deleteBoard(bno);
	}
}
