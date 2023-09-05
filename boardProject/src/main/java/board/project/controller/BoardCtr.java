package board.project.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import board.project.exception.MaxDepthException;
import board.project.service.BoardSvc;
import board.project.service.FileSvc;
import board.project.service.ReplySvc;
import board.project.vo.Kmj_BoardVo;
import board.project.vo.Kmj_FilesVo;
import board.project.vo.Kmj_MemberTableVo;
import board.project.vo.Kmj_ReplyVo;

@Controller
public class BoardCtr {
	@Autowired
	private BoardSvc boardService;
	@Autowired
	private FileSvc fileService;
	@Autowired
	private ReplySvc replyService;
	
	int maxPageBlock=5;
	
	private final String path = "C:\\Users\\user\\Documents\\workspace-spring-tool-suite-4-4.18.1.RELEASE\\TestBoard\\src\\main\\resources\\savefiles\\files\\";
	
	
	
	@RequestMapping(value = "boardForm")
	public String boardForm() throws IOException {
		
		return "boardForm";
	}
	
	@RequestMapping(value = "boardIn") //게시판 등록
	public String boardInsert(Kmj_BoardVo bvo,Kmj_FilesVo fileList,Model model) throws IOException {		
		
		//게시판 등록
		boardService.boardIn(bvo);
		
		
		//파일 등록 파일을 등록 할 경우 insert / 파일을 등록하지 않을 경우 실행하지 않는다.
		if(!fileList.getFilesList().get(0).isEmpty()) {
			fileService.storeFiles(fileList.getFilesList(),bvo.getBoardNo());
		}
		
		//해당 게시판에 파일이 있는지 없는지 확인 있으면 1 없으면 0반환
		int fileChk=fileService.fileChk(bvo.getBoardNo());
		
		//파일이 있으면 file_check가 1로 변경된다.
		if(fileChk != 0) {
			boardService.fileChkUpdate(bvo.getBoardNo());	
		}
		return "redirect:/boardListOn";
	}
	
	@RequestMapping(value = "boardReplyForm/{boardNo}/{depth}")
	public String boardReplyFrorm(@PathVariable int boardNo,@PathVariable int depth, Model model) {
		model.addAttribute("boardNo",boardNo);
		model.addAttribute("depth",depth);
		return "boardReplyForm";
	}
	
	@RequestMapping(value = "boardReplyIn") //게시판 등록
	public String boardReplyInsert(Kmj_BoardVo bvo,Kmj_FilesVo fileList,Model model) throws IOException, MaxDepthException {	
		
		List<Kmj_BoardVo> boardList=boardService.boardList();	
		model.addAttribute("boardList",boardList);
		
		
		for(Kmj_BoardVo depthExceptjon : boardList) {
			if(depthExceptjon.getDepth()>=300) {
				throw new MaxDepthException("최대 답글 갯수를 초과하였습니다.");
			}
		}
		
		boardService.boardReplyInsert(bvo);
		
		if(!fileList.getFilesList().get(0).isEmpty()) {
		fileService.storeFiles(fileList.getFilesList(),bvo.getBoardNo());
		}
		
		int fileChk=fileService.fileChk(bvo.getBoardNo());
		
		if(fileChk != 0) {
		boardService.fileChkUpdate(bvo.getBoardNo());	
		}
		
		return "redirect:/boardListOn";
	}
	
	@RequestMapping(value = "boardListOn") //http://localhost:5555/boardListOn
	public String boardList(Model model, @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "") String searchValue) throws MaxDepthException{
		System.out.println("currentPage"+currentPage);
		System.out.println("searchValue===>"+searchValue);
		Map<String, Object> pagingSource = new HashMap<String, Object>();
		
		
		
		int totalPageCount=boardService.boardTotalCount(searchValue)-1;
		int totalPage = (int) Math.ceil((double) totalPageCount / 5); // Assuming 5 items per page
		int startPage = (currentPage-1)*5+1;
		int currentBlock = currentPage != null ? (int) Math.ceil((double) currentPage / maxPageBlock) : 1; // currentPage가 null이면 1로 초기화
        
		int startblockPage = (currentBlock - 1) * maxPageBlock + 1;
		int endPage = Math.min(startblockPage + maxPageBlock - 1, totalPage);
		System.out.println("===========================");
		System.out.println("startPage===>"+startPage);
		System.out.println("searchValue===>"+searchValue);
		pagingSource.put("startPage", startPage);
		pagingSource.put("searchValue", searchValue);
		
		
		for(Map.Entry<String, Object> item : pagingSource.entrySet()) {
			System.out.println("getValue=>>>"+item.getValue());
		}
		
		List<Kmj_BoardVo> boardList=boardService.boardListPaging(pagingSource);	
		model.addAttribute("boardList",boardList);
		model.addAttribute("currentBlock",currentBlock);
		model.addAttribute("maxPageBlock",maxPageBlock);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("startblockPage",startblockPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("searchValue",searchValue);
		
		return "boardList";
	}
	
	@RequestMapping(value = "detailPage")
	public String detailPage(int boardNo, Model model) {
		int boardout = boardNo;
		Kmj_BoardVo boardDetail = boardService.detailPage(boardNo);
		
		
		String sessionUserId="user1";
		model.addAttribute("sessionUserId",sessionUserId);
		
		
		List<Kmj_FilesVo> downloadFileList = fileService.downloadFileList(boardNo);
		
		model.addAttribute("boardDetail",boardDetail);
		model.addAttribute("downloadFileList",downloadFileList);
		
		List<Kmj_ReplyVo> replyList=replyService.replyList(boardNo);
		model.addAttribute("replyList",replyList);
		
		return "detailPage";
	}
	
	@RequestMapping(value = "updateForm")
	public String updateForm(Kmj_BoardVo bvo,Model model) {
		model.addAttribute("boardDetail",bvo);
		return "updateForm";
	}
	
	
	@RequestMapping(value = "boardUpdate")
	public String boardUpdate(Kmj_BoardVo bvo,Kmj_FilesVo fileList) throws IOException {
		int boardNo=bvo.getBoardNo();
		
		if(!fileList.getFilesList().get(0).isEmpty()) {
		fileService.storeFiles(fileList.getFilesList(),bvo.getBoardNo());
		}
		
		int fileChk=fileService.fileChk(bvo.getBoardNo());
		
		if(fileChk != 0) {
		boardService.fileChkUpdate(bvo.getBoardNo());	
		}
		
		
		
		
		boardService.boardUpdate(bvo);
		return "redirect:/detailPage?boardNo="+bvo.getBoardNo();
	}
	
	@RequestMapping(value = "boardDelete")
	public String boardDelete(int boardNo) throws IOException {
		boardService.boardDelete(boardNo);
		boardService.AssociateDelete(boardNo);
		boardService.fileChkDelete(boardNo);
		replyService.associateReplyDelete(boardNo);
		
		List<String> storeFileName=fileService.storeFileName(boardNo);
		
		if(!storeFileName.isEmpty()) {
			for(String fileName:storeFileName) {
				fileService.deleteFile(fileName);
				
				int fileChk=fileService.fileChk(boardNo);
				
				if(fileChk==0) {
				boardService.fileChkDelete(boardNo);
				}
			}
		}
		
		
		return "redirect:/boardListOn";			
	}
	
	@RequestMapping("download/{fileName}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable String fileName) throws MalformedURLException {
		
		String fileDate=fileService.fileDate(fileName);		
	    Kmj_FilesVo fileNames = fileService.selectName(fileName);
	    String storeFilename = fileNames.getStoreFileName();
	    String dwonloadFilename = fileNames.getFileName();
	    
	    
	    UrlResource urlResource = new UrlResource("file:" + fileService.getFullPath(fileDate,storeFilename)+fileNames.getFileType());

	    // 업로드 한 파일명이 한글인 경우 아래 작업을 안해주면 한글이 깨질 수 있음
	    String encodedUploadFileName = UriUtils.encode(dwonloadFilename, StandardCharsets.UTF_8);
	    
	    //attachment = 파일 이름 지정하여 다운로드
	    //attachment; filename="파일명"
	    String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
	    
	    // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
	            .body(urlResource);
	}
	
	
	@RequestMapping(value = "emptyRecyclebin")
	public String emptyRecyclebin() throws IOException{
	fileService.emptyRecyclebin();
	System.out.println("emptyRecyclebin 실행 완료");
	return "redirect:/boardListOn";
	}
}
