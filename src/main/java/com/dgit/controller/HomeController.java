package com.dgit.controller;

import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.domain.MemberVO;
import com.dgit.domain.PhotoVO;
import com.dgit.service.MemberService;
import com.dgit.service.PhotoService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private MemberService service;
	
	@Inject
	private PhotoService photoService;
	
	@Resource(name="uploadPath")
	private String outUploadPath;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() throws Exception {
		logger.info("main 진입!!");
						
		return "main";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/checkid/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> checkId(@PathVariable("id") String id){
		ResponseEntity<String> entity = null;
		
		try{
			String checkId = service.checkID(id);
			if(checkId != null){
				entity = new ResponseEntity<String>("exist", HttpStatus.OK); 
			}else{
				entity = new ResponseEntity<String>("not exist", HttpStatus.OK);
			}
		
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
	
	@RequestMapping(value="/sign", method = RequestMethod.POST)
	public String Sign(MemberVO vo, String signId, String signPw, HttpServletRequest req){
		// logger.info("회원가입 진입!!");
		vo.setId(signId);
		vo.setPw(signPw);
		
		try {
			service.insert(vo);
			req.getSession().setAttribute("login", vo);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		logger.info("num = " + vo.getNum());
		
		
		return "redirect: /exam/goMain?num=" + vo.getNum();
	}
	
	@ResponseBody
	@RequestMapping(value="/loginCheck", method = RequestMethod.GET)
	public ResponseEntity<String> loginCheck(String id, String pw) throws Exception{
		
		try{  
			String checkId = service.checkID(id); 
			
			if(checkId != null){
				MemberVO user = service.readWithPW(id, pw);
				
				if(user != null){
					return new ResponseEntity<String>("correct ID, PW", HttpStatus.OK);
				}else{
					return new ResponseEntity<String>("non-correct PW", HttpStatus.OK);
				}
			
			}else{
				return new ResponseEntity<String>("not exist ID", HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String Sign(HttpServletRequest req){
		req.getSession().removeAttribute("login");
				
		return "redirect: /exam/"; 
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void login(String id, String pw, Model model){
		// logger.info("Login 진입");
		 
		 
		try{

			MemberVO user = service.readWithPW(id, pw);
			model.addAttribute("login", user); 
					 
		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/addPhoto", method = RequestMethod.GET)
	public String addPhoto(@ModelAttribute("num")int num){
		// logger.info("add GET 진입");
				
		return "add";
	}
	
	@RequestMapping(value="/addPhoto", method = RequestMethod.POST)
	public String addPhotoPOST(MemberVO vo, List<MultipartFile> files, Model model){
		// logger.info("add POST 진입");
		
		try{
			for(MultipartFile f : files){
				String filePath = UploadFileUtils.uploadFile(outUploadPath, f.getOriginalFilename(), f.getBytes());
							
				PhotoVO photo = new PhotoVO();
				photo.setNum(vo.getNum());
				photo.setFilepath(outUploadPath + filePath);
				
				photoService.insert(photo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
 
		return "redirect: /exam/goMain?num=" + vo.getNum();
	}
	
	@RequestMapping(value="/goMain", method = RequestMethod.GET)
	public String goMain(@ModelAttribute("num") int num, Model model) throws Exception{
		
		MemberVO vo = null;

		if(num != 0){
			vo = service.selectByNum(num);
			PhotoVO[] photo = vo.getImages();
			if(photo != null){
				for(int i = 0; i < photo.length; i++){
					String[] originalName = photo[i].getFilepath().split("_");
					photo[i].setOriginalFile(originalName[originalName.length - 1]);
				}
				
			} 
						
			model.addAttribute("images", vo);
		}
		
		return "main"; 
	}
	
	@ResponseBody
	@RequestMapping(value="displayFile", method = RequestMethod.GET) 
	public ResponseEntity<byte[]> displayFile(String filename, String check){
		ResponseEntity<byte[]> entity = null;
		
		if(check.equals("false")){
			entity = getDisplayFile(filename);	
		}else{
			filename = filename.substring(0, filename.indexOf("s_")) + filename.substring(filename.indexOf("s_") + 2);
			entity = getDisplayFile(filename);	
		}
		
		return entity;
	}


	private ResponseEntity<byte[]> getDisplayFile(String filename) {
		ResponseEntity<byte[]> entity = null;
		// logger.info("filename : " + filename);
		FileInputStream in = null;
		
		try{
			String formatName = filename.substring(filename.lastIndexOf(".") + 1); // 파일형식 반환(예: jpg, png)
			
			// jsp file 내 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			// contentType을 지정하기 위해 MediaUtils을 만들고 Type을 지정해줌
			// MediUtils 클래스를 만들지 않아도 되지만, 작업의 편의를 위해 별도의 util 패키지내 클래스로 작성
						
			MediaType type = MediaUtils.getMeditType(formatName); 
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);
			
			in = new FileInputStream(filename);
																		// contentType = "text/MediaType.IMAGE_JPEG"으로 설정한 header를 함께 넘김
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
												// fileInputStream으로 읽은 byte배열을 넘김
		
		
		}catch(Exception e){
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	
	@RequestMapping(value="delPhoto", method = RequestMethod.POST)
	public String delPhoto(String target, int num) throws Exception{
				
		PhotoVO vo = new PhotoVO();
		vo.setFilepath(target);
		vo.setNum(num);
		UploadFileUtils.deleteImg(target);
		photoService.delete(vo);
				
		return "redirect: /exam/goMain?num=" + vo.getNum();
	}
}
