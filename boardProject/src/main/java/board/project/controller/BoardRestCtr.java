package board.project.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import board.project.service.BoardSvc;
import board.project.service.FileSvc;
import board.project.vo.Kmj_BoardVo;
import board.project.vo.Kmj_FilesVo;

//@RestController
public class BoardRestCtr {
	
	@Autowired
	private BoardSvc boardService;
	@Autowired
	private FileSvc fileService;
	
	
	@RequestMapping(value = "boardin") //http://localhost:5555/boardin?associateNo=1&id=user1&title=title&contents=contents
	public void boardInsert(Kmj_BoardVo bvo,List<MultipartFile> listFiles) throws IOException {
		int pkno = bvo.getBoardNo();		
		//게시판 등록
		boardService.boardIn(bvo);
	}
	
	@RequestMapping(value = "boardlist") //http://localhost:5555/boardlist
	public List<Kmj_BoardVo> boardList(){
		return boardService.boardList();
	}
	
	
	@RequestMapping(value = "boardup") //http://localhost:5555/boardup?title=updatetitle&contents=updatecontents&boardno=1
	public void boardUpdate(Kmj_BoardVo vo) {
		boardService.boardUpdate(vo);
	}
	
	
	@RequestMapping(value = "boarddel") //http://localhost:5555/boarddel?boardno=55
	public void boardDelete(int boardNo) {
		boardService.boardDelete(boardNo);
	}	
	
}
