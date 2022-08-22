package kr.or.ddit.controller.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.controller.MakeFileName;
import com.jsp.dto.MemberVO;
import com.jsp.service.LoginSearchMemberService;

@RestController
@RequestMapping("/member")
public class MemberRestController {

	@Autowired
	private LoginSearchMemberService memberService;
	
	@Resource(name="picturePath")
	private String picturePath;
	
	public String savePicture(String oldPicture,MultipartFile multi)throws Exception{
		
		String fileName=null;
		
		/*파일저장폴더설정*/
		String uploadPath=picturePath;
		/*파일유뮤확인*/
		if(!(multi==null||multi.isEmpty()||multi.getSize()>1024*1024*1)) {
			/*파일저장폴더설정*/
			
			fileName=MakeFileName.toUUIDFileName(multi.getOriginalFilename(),"/");
			File storeFile=new File(uploadPath,fileName);
			
			//파일경로 생성
			storeFile.mkdirs();
			
			//local HDD에저장
			multi.transferTo(storeFile);
			
			
		}
		//기존파일 삭제
		if(oldPicture!=null&&!oldPicture.isEmpty()) {
			File oldFile=new File(uploadPath,oldPicture);
			if(oldFile.exists()) {
				oldFile.delete();
			}
		}
		return uploadPath;
		
		
	}
	
	@RequestMapping(value="/picture",method=RequestMethod.POST,produces="text/plain;charset=utf-8")
	public ResponseEntity<String>picture(@RequestParam("pictureFile")MultipartFile multi,String oldPicture)throws Exception{
		
		ResponseEntity<String>entity=null;
		
		String result="";
		HttpStatus status=null;
		
		/*파일저장확인*/
		if((result=savePicture(oldPicture,multi))==null) {
			result="업로드 실패했습니다.!";
			status=HttpStatus.BAD_REQUEST;
		}else {
			status=HttpStatus.OK;
		}
		
		
		entity=new ResponseEntity<String>(result,status);
		return entity;
	}
	@RequestMapping(value="/getPicture",produces="text/plain;charset=utf-8")
	   public ResponseEntity<byte[]>getPicture(String id)throws Exception{
	      String picture=memberService.getMember(id).getPicture();
	      
	      InputStream in=null;
	      ResponseEntity<byte[]>entity=null;
	      String imgPath=this.picturePath;
	      
	      try {
	         in=new FileInputStream(new File(imgPath,picture));
	         
	         entity=new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.CREATED);
	      } finally  {
	         if(in!=null)in.close();
	      }
	      return entity;
	   }

	@RequestMapping("/idCheck")
	public ResponseEntity<String>idCheck(String id)throws Exception{
		ResponseEntity<String>entity=null;
		
		MemberVO member=memberService.getMember(id);
		
		if(member!=null) {
			entity=new ResponseEntity<String>("duplicated",HttpStatus.OK);
			
		}else {
			entity=new ResponseEntity<String>("",HttpStatus.OK);
		}
		return entity;
	}
	
	
}
