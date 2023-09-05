package board.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.project.dao.ReplyDaoInter;
import board.project.vo.Kmj_ReplyVo;

@Service
public class ReplySvc {
	@Autowired
	private ReplyDaoInter replyDaoInter;
	
	public void replyInsert(Kmj_ReplyVo rvo) {
		replyDaoInter.replyInsert(rvo);
	}
	
	public List<Kmj_ReplyVo> replyList(int boardNo){
		List<Kmj_ReplyVo> replyvo=replyDaoInter.replyList(boardNo);
		for(Kmj_ReplyVo rvo : replyvo) {
		}
		return  replyvo;
	}
	
	public void replyUpdate(Kmj_ReplyVo rvo) {
		replyDaoInter.replyUpdate(rvo);
	}
	
	public void replyDelete(int replyNo) {
		replyDaoInter.replyDelete(replyNo);
	}
	
	public void replyReplyInsert(Kmj_ReplyVo rvo) {
		replyDaoInter.replyReplyInsert(rvo);
	}
	
	
	public void associateReplyDelete(int boardNo) {
		replyDaoInter.associateReplyDelete(boardNo);
	}

}
