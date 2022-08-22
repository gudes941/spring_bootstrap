package kr.or.ddit.controller.view;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.SearchCriteria;
import com.jsp.controller.FileDownloadResolver;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.GetUploadPath;
import com.jsp.controller.MakeFileName;
import com.jsp.controller.XSSMultipartHttpServletRequestParser;
import com.jsp.dto.AttachVO;
import com.jsp.dto.BoardVO;
import com.jsp.dto.PdsVO;
import com.jsp.exception.NotMultipartFormDataException;
import com.jsp.service.BoardService;
import com.jsp.service.PdsService;

import kr.or.ddit.command.PdsModifyCommand;
import kr.or.ddit.command.PdsRegistCommand;
import kr.or.ddit.controller.rest.GetAttachesByMultipartFileAdapter;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	
 
   
   @Resource(name="pdsService")
   private PdsService service;
   
   @RequestMapping("/main")
   public String main() throws Exception {
      String url = "pds/main";
      return url;
   }
   
   @RequestMapping("/list")
   public ModelAndView list(SearchCriteria cri, ModelAndView mnv)throws Exception{
      String url="pds/list";      
      
      Map<String,Object> dataMap = service.getList(cri);
      
      mnv.addObject("dataMap",dataMap);
      mnv.setViewName(url);
      
      return mnv;
   }
   
   @RequestMapping("/registForm")
   public String registForm()throws Exception{
      String url="pds/regist";      
      return url;
   }
   
   
   @Resource(name = "fileUploadPath")
   private String fileUploadPath;
   
   @RequestMapping(value="/regist",method=RequestMethod.POST,produces="text/plain; charset=utf-8")
   public String regist(PdsRegistCommand registReq,HttpServletRequest request,
                   RedirectAttributes rttr)throws Exception{
      String url="redirect:/pds/list.do";   
      
      //file 저장 ->List<AttachVO>
      String savePath=this.fileUploadPath;
      List<AttachVO>attachList=GetAttachesByMultipartFileAdapter.save(registReq.getUploadFile(),savePath);
      
      //DB
      PdsVO pds=registReq.toPdsVO();
      pds.setAttachList(attachList);
      pds.setTitle((String)request.getAttribute("XSStitle"));
      service.regist(pds);
      
      //output
      rttr.addFlashAttribute("from","regist");
      
      return url;
   }
   
   @RequestMapping("/detail")
   public ModelAndView detail(int pno,String from, ModelAndView mnv )throws SQLException{
      String url="pds/detail";      
      
      PdsVO pds =null;
      if(from!=null && from.equals("list")) {
         pds=service.read(pno);
         url="redirect:/pds/detail.do?pno="+pno;
      }else {
         pds=service.getPds(pno);
      }
      
      //파일명 재정의
      if(pds!=null) {
    	  List<AttachVO>attachList=pds.getAttachList();
    	  if(attachList!=null) {
    		  for(AttachVO attach:attachList) {
    			  String fileName=attach.getFileName().split("\\$\\$")[1];
    			  attach.setFileName(fileName);
    		  }
    	  }
    	  
      }
  	
	
               
      mnv.addObject("pds",pds);      
      mnv.setViewName(url);
      
      return mnv;
   }
   @RequestMapping("/getFile")
   public String getFile(int ano,Model model)throws Exception{
	   String url="downloadFile";
	   
	   AttachVO attach=service.getAttachByAno(ano);
	   
	   model.addAttribute("savedPath",attach.getUploadPath());
	   model.addAttribute("fileName",attach.getFileName());
	   
	   return url;
   }
   
   
   @RequestMapping("/modifyForm")
   public ModelAndView modifyForm(int pno,ModelAndView mnv)throws SQLException{
      String url="pds/modify";
      
      PdsVO pds = service.read(pno);
      
      if(pds!=null) {
    	  List<AttachVO>attachList=pds.getAttachList();
    	  if(attachList!=null) {
    		  for(AttachVO attach:attachList) {
    			  String fileName=attach.getFileName().split("\\$\\$")[1];
    			  attach.setFileName(fileName);
    		  }
    	  }

      }
  	
      
      
      
      mnv.addObject("pds",pds);      
      mnv.setViewName(url);
      
      return mnv;
   }
   
   @RequestMapping("/modify")
   public String modifyPost(PdsModifyCommand modifyReq,HttpServletRequest request,
                      RedirectAttributes rttr) throws Exception{
      
      String url = "redirect:/pds/detail.do";
      
    //파일 삭제
      if(modifyReq.getDeleteFile()!=null&&modifyReq.getDeleteFile().length>0) {
    	  for(String anoStr:modifyReq.getDeleteFile()) {
    		  int ano=Integer.parseInt(anoStr);
    		  AttachVO attach=service.getAttachByAno(ano);
    		  
    		  File deleteFile=new File(attach.getUploadPath(),attach.getFileName());
    		  
    		  if(deleteFile.exists()) {
    			  deleteFile.delete();//File 삭제
    		  }
    		  service.removeAttachByAno(ano);//db 삭제
    	  }
      }
      
      //파일 저장
      List<AttachVO>attachList=GetAttachesByMultipartFileAdapter.save(modifyReq.getUploadFile(),fileUploadPath);
      
      //pdsVO setting
      PdsVO pds=modifyReq.toPdsVO();
      pds.setAttachList(attachList);
      pds.setTitle((String)request.getAttribute("XSStitle"));
      
      	//DB 저장
      	service.modify(pds);
      
      
      	rttr.addFlashAttribute("from","modify");
      	rttr.addAttribute("pno",pds.getPno());
      
      return url;
   }
   	
	

@RequestMapping("/remove")
   public String remove(int pno,RedirectAttributes rttr) throws Exception{
      String url = "redirect:/pds/detail.do";
      
      //첨부파일 삭제
      List<AttachVO>attachList=service.getPds(pno).getAttachList();
      if(attachList!=null) {
    	  for(AttachVO attach:attachList) {
    		  File target=new File(attach.getUploadPath(),attach.getFileName());
    		  if(target.exists()) {
    			  target.delete();
    		  }
    	  }
      }
     
  		
  		service.remove(pno);    
	
          
      
      rttr.addAttribute("pno",pno);
      rttr.addFlashAttribute("from","remove");
      return url;      
   }
   
  
   
}









