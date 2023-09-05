package board.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.project.dao.BoardDaoInter;
import board.project.vo.Kmj_BoardVo;

@Service
public class BoardSvc {
	@Autowired
	private BoardDaoInter boardDaoInter;
	
	public void boardIn(Kmj_BoardVo vo) {
	boardDaoInter.boardInsert(vo);
	}
	
	public List<Kmj_BoardVo> boardList(){ 
		return boardDaoInter.boardList();
	}
	
	public List<Kmj_BoardVo> boardListPaging(Map<String, Object> pagingSaurce){
		System.out.println("startpage=>"+pagingSaurce);
		return boardDaoInter.boardListPaging(pagingSaurce);
	}
	
	
	public void boardUpdate(Kmj_BoardVo bvo) {
		
		boardDaoInter.boardUpdate(bvo);
	}
	
	public void boardDelete(int boardNo) {
		boardDaoInter.boardDelete(boardNo);
	}
	
	public Kmj_BoardVo detailPage(int boardNo) {
		return boardDaoInter.detailPage(boardNo);
	}
	
	public void fileChkUpdate(int boardNo) {
		boardDaoInter.fileChkUpdate(boardNo);
	}
	
	public void AssociateDelete(int boardNo) {
		boardDaoInter.AssociateDelete(boardNo);
	}
	
	public void boardReplyInsert(Kmj_BoardVo bvo) {
		boardDaoInter.boardReplyInsert(bvo);
	}
	
	public void fileChkDelete(int boardNo) {
		boardDaoInter.fileChkDelete(boardNo);
	}
	
	public int boardTotalCount(String searchValue) {
		return boardDaoInter.boardTotalCount(searchValue);
	}
	
}
