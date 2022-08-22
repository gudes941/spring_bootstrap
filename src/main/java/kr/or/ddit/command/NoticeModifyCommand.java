package kr.or.ddit.command;

import com.jsp.dto.NoticeVO;

public class NoticeModifyCommand {

	private String  nno;
	private String title;
	private String writer;
	private String content;
	public String getNno() {
		return nno;
	}
	public void setNno(String nno) {
		this.nno = nno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
