package kr.or.ddit.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.Criteria;
import com.jsp.command.PageMaker;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;
import com.jsp.exception.InvalidPasswordException;
import com.jsp.exception.NotFoundIdException;
import com.jsp.service.LoginSearchMemberService;

import kr.or.ddit.dao.MemberDAOBean;

public class MemberServiceImpl implements LoginSearchMemberService{
	
	private MemberDAOBean memberDAOBean;
	public void setMemberDAOBean(MemberDAOBean memberDAOBeanBean) {
		this.memberDAOBean = memberDAOBeanBean;
	}
	
	@Override
	public void enabled(String id,int state) throws Exception {
		memberDAOBean.enabledMember(id,state);
	}


	@Override
	public MemberVO getMember(String id) throws Exception {
		MemberVO member = memberDAOBean.selectMemberById(id);
		return member;
	}

	
	@Override
	public List<MemberVO> getMemberList() throws Exception {		
		return null;
	}

	@Override
	public List<MemberVO> getMemberList(Criteria arg0) throws Exception {		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getMemberListForPage(Criteria cri) throws Exception {

		SearchCriteria searchCri = (SearchCriteria)cri;
		Map<String, Object> dataMap = new HashMap<String, Object>();

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(memberDAOBean.selectMemberListCount(searchCri));

		List<MemberVO> memberList = memberDAOBean.selectMemberList(searchCri);

		dataMap.put("memberList", memberList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}
	
	@Override
	public void modify(MemberVO member) throws Exception {
		memberDAOBean.updateMember(member);
	}

	@Override
	public void regist(MemberVO member) throws Exception {
		memberDAOBean.insertMember(member);
	}

	@Override
	public void remove(String id) throws Exception {
		memberDAOBean.deleteMember(id);

	}

	@Override
	public void login(String id, String pwd) throws NotFoundIdException, 
													InvalidPasswordException,
													SQLException {
		MemberVO member = memberDAOBean.selectMemberById(id);
		if (member == null)
			throw new NotFoundIdException();
		if (!pwd.equals(member.getPwd()))
			throw new InvalidPasswordException();
		
	}


}
