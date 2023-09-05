package board.project.dao;

import java.util.List;
import java.util.Map;

import board.project.vo.Kmj_BoardVo;

public interface BoardDaoInter {
	public void boardInsert(Kmj_BoardVo bvo);
	public void boardUpdate(Kmj_BoardVo bvo);
	public void boardDelete(int boardno);
	public List<Kmj_BoardVo> boardList();
	public List<Kmj_BoardVo> boardListPaging(Map<String, Object> pagingSaurce);
	public Kmj_BoardVo detailPage(int boardNo);
	public void fileChkUpdate(int boardNo);
	public void AssociateDelete(int boardNo);
	public void boardReplyInsert(Kmj_BoardVo bvo);
	public void fileChkDelete(int boardNo);
	public int boardTotalCount(String searchValue);
}
