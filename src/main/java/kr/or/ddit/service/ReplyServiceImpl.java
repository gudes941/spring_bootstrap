package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.Criteria;
import com.jsp.command.PageMaker;
import com.jsp.dto.MemberVO;
import com.jsp.dto.ReplyVO;
import com.jsp.service.ReplyService;

import kr.or.ddit.dao.MemberDAOBean;
import kr.or.ddit.dao.ReplyDAOBean;

public class ReplyServiceImpl implements ReplyService{
	
	private ReplyDAOBean replyDAOBean;

	public void setReplyDAOBean(ReplyDAOBean replyDAOBean) {
		this.replyDAOBean = replyDAOBean;
	}

	private MemberDAOBean memberDAOBean;
	public void setMemberDAOBean(MemberDAOBean memberDAOBean) {
		this.memberDAOBean = memberDAOBean;
	}
	
	@Override
	public Map<String, Object> getReplyList(int bno,Criteria cri) throws SQLException {

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<ReplyVO> replyList = replyDAOBean.selectReplyListPage(bno, cri);

		if(replyList!=null)for(ReplyVO reply : replyList) {
			MemberVO member = memberDAOBean.selectMemberById(reply.getReplyer());
			reply.setPicture(member.getPicture());
		}
		
		int count = replyDAOBean.countReply(bno);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(count);

		dataMap.put("replyList", replyList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}

	@Override
	public void registReply(ReplyVO reply) throws SQLException {
		int rno = replyDAOBean.selectReplySeqNextValue();
		reply.setRno(rno);

		replyDAOBean.insertReply(reply);

	}

	@Override
	public void modifyReply(ReplyVO reply) throws SQLException {

		replyDAOBean.updateReply(reply);

	}

	@Override
	public void removeReply(int rno) throws SQLException {

		replyDAOBean.deleteReply(rno);
	}

	@Override
	public int getReplyListCount(int bno) throws SQLException {
		int count = replyDAOBean.countReply(bno);
		return count;
	}

}
