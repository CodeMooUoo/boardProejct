package board.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.project.service.MemberSvc;
import board.project.vo.Kmj_MemberTableVo;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberCtr {

@Autowired
private MemberSvc memberService;

@RequestMapping(value = "memberForm")
public String memberForm() {
	return "memberForm";
}

@RequestMapping(value = "memberIn")
public String memberInsert(Kmj_MemberTableVo vo) {
	memberService.memberInsert(vo);
	return "loginForm";
}

@RequestMapping(value = "loginForm")
public String loginForm() {
	System.out.println("로그인 폼 확인");
return "loginForm";
}


	
}
