package com.dgit.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String Sign(HttpServletRequest req){
		req.getSession().removeAttribute("login");
				
		return "redirect: /exam/";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(String id, String pw){
		
		try {
			service.readWithPW(id, pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	
}
