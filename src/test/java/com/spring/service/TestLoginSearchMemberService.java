package com.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kr/or/ddit/context/root-context.xml")
@Transactional
public class TestLoginSearchMemberService {
	
	@Resource(name="loginSearchMemberService")
	private MemberService memberService;
	
	@Test
	public void testGetMemberList()throws Exception{
		SearchCriteria cri=new SearchCriteria();
		cri.setKeyword("m");
		cri.setSearchType("i");
		cri.setPage(1);
		cri.setPerPageNum(10);
		
		List<MemberVO>memberList=(List<MemberVO>)memberService.getMemberListForPage(cri).get("memberList");
		
		if(memberList!=null)for(MemberVO member:memberList)
			System.out.println(member);
		
		Assert.assertEquals(5,memberList.size());
		
		
	}
}
