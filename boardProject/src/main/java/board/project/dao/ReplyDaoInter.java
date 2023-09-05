package board.project.dao;

import java.util.List;

import board.project.vo.Kmj_ReplyVo;

public interface ReplyDaoInter {
	public void replyInsert(Kmj_ReplyVo rvo);
	public List<Kmj_ReplyVo> replyList(int boardNo);
	public void replyUpdate(Kmj_ReplyVo rvo);
	public void replyDelete(int replyNo);
	public void replyReplyInsert(Kmj_ReplyVo rvo);
	public void associateReplyDelete(int boardNo);
}
