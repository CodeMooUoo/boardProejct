package board.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.project.service.BoardSvc;
import board.project.service.FileSvc;
import board.project.vo.Kmj_FilesVo;

@RestController
public class FileRestCtr {
	
	@Autowired
	private FileSvc fileService;
	
	@Autowired
	private BoardSvc boardService;
	
	@RequestMapping(value = "fileDelete")
	public List<Kmj_FilesVo> deleteFile(@RequestParam String fileName, @RequestParam int boardNo) throws IOException {
		//fileService.deleteFile(fileName);
		fileService.deleteFile(fileName);
		
		int fileChk=fileService.fileChk(boardNo);
		
		if(fileChk==0) {
		boardService.fileChkDelete(boardNo);
		}
		
		return fileService.downloadFileList(boardNo);
		
		
	}
}
