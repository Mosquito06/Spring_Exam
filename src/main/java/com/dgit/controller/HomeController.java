package com.dgit.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private MemberService service;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
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
		logger.info("회원가입 진입!!");
		vo.setId(signId);
		vo.setPw(signPw);
		
		try {
			service.insert(vo);
			req.getSession().setAttribute("login", vo);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "redirect: /exam/";
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
		logger.info("Login 진입");
		
		 
		try{

			MemberVO user = service.readWithPW(id, pw);
			model.addAttribute("login", user); 
					
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
}
