package board.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import board.project.exception.MaxDepthException;
import board.project.service.ReplySvc;
import board.project.vo.Kmj_ReplyVo;

@Controller
public class ReplyCtr {
	@Autowired
	private ReplySvc replyService;
	
	@RequestMapping(value = "replyForm")
	public String replyForm(int boardNo, Model model) {
	model.addAttribute("boardNo",boardNo);
	return "ReplyForm";
	}
	
	@RequestMapping(value = "replyIn")
	public String replyInsult(Kmj_ReplyVo rvo) {
		replyService.replyInsert(rvo);
	
	return "redirect:/detailPage?boardNo="+rvo.getBoardNo();
	}
	
	@RequestMapping(value = "replyUpdateForm")
	public String replyUpdateForm(Kmj_ReplyVo rvo, Model model) {
	model.addAttribute("rvo",rvo);
	
	return "replyUpdateForm";
	}
	
	@RequestMapping(value = "replyUpdate")
	public String replyUpdate(Kmj_ReplyVo rvo) {
	replyService.replyUpdate(rvo);
	return "redirect:/detailPage?boardNo="+rvo.getBoardNo();
	}
	
	@RequestMapping(value = "replyDelte")
	public String replyDelete(Kmj_ReplyVo rvo) {
	replyService.replyDelete(rvo.getReplyNo());
	return "redirect:/detailPage?boardNo="+rvo.getBoardNo();
	}
	
	@RequestMapping(value = "replyReplyFrom")
	public String replyReplyFrom(Kmj_ReplyVo rvo, Model model) {
	model.addAttribute("rvo",rvo);
	return "replyReplyForm";
	}
	
	@RequestMapping(value = "replyReplyIn")
	public String replyReplyIn(Kmj_ReplyVo rvo) throws MaxDepthException {
	List<Kmj_ReplyVo> replyList=replyService.replyList(rvo.getBoardNo());
	for(Kmj_ReplyVo depthExceptjon : replyList) {
		if(depthExceptjon.getDepth()>=300) {
			throw new MaxDepthException("최대 답글 갯수를 초과하였습니다.");
		}
	}
		
	replyService.replyReplyInsert(rvo);
	return "redirect:/detailPage?boardNo="+rvo.getBoardNo(); 
	}
}
