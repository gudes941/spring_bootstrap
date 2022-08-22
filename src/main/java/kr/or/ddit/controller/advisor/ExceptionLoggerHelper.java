package kr.or.ddit.controller.advisor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.jsp.dto.MemberVO;

import jdk.jfr.events.FileWriteEvent;

@Component("exceptionLoggerHelper")
public class ExceptionLoggerHelper {
	
	@Resource(name="errorLogPath")
	private String errorLogPath;
	
	public void write(HttpServletRequest request,Exception e,String classType) {
		
		String savePath=errorLogPath.replace("/",File.separator);
		String logFilePath=savePath+File.separator+"system_exception_log.csv";
		
		
		String url=request.getRequestURI().replace(request.getContextPath(),"");
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		
		String loginUserName=((MemberVO)request.getSession().getAttribute("loginUser")).getName();
		
		String exceptionType=e.getClass().getName();
		
		String log="[Error:"+exceptionType+"]"+url+","+date+","+loginUserName+","+classType+"\n";
		
		//로그 파일 생성
		File file=new File(savePath);
		file.mkdirs();
		
		try {
			BufferedWriter out=new BufferedWriter(new FileWriter(logFilePath,true));
			
			//로그를 ㅣㄱ록
			out.write(log);
			out.newLine();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		
	}

}
