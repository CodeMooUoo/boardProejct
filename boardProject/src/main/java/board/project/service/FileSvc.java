package board.project.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.sound.midi.Patch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import board.project.dao.FileDaoInter;
import board.project.vo.Kmj_FilesVo;

@Service
public class FileSvc {
	
	@Autowired
	private FileDaoInter fileDaoInter;
	 // 루트 경로 불러오기
    private final String rootpath = System.getProperty("user.dir");
    // 프로젝트 루트 경로에 있는 files 디렉토리
    private final String fileDir = rootpath + "\\src\\main\\resources\\savefiles\\";
    private final String recyclebinDir = rootpath + "\\src\\main\\resources\\savefiles\\recyclebin\\";

    public String getFullPath(String fileDate, String filename) {return fileDir+fileDate+"\\"+filename; }
    public String getPath(String fileDate) {return fileDir+fileDate;}
    public String getRecyclebinPath(String filename) {return recyclebinDir + filename; }
    public String todayDate() {
    	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    	Date now = new Date();
    	String today=date.format(now);
    	 
    	return today;
    }


    
    public void storeFiles(List<MultipartFile> multipartFiles, int boardNo) throws IOException {
    	String today=todayDate();
        List<Kmj_FilesVo> storeFileSave = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
            	Kmj_FilesVo fvo = new Kmj_FilesVo();
                
            	//사용자가 입력한 파일 이름
            	String originalFilename = multipartFile.getOriginalFilename();
            	int pos = originalFilename.lastIndexOf(".");
            	String nonExtFileName = originalFilename.substring(0, pos);
                
                //사용자가 업로드한 파일명 고유한 파일이름으로 변경(파일 이름 중복 방지)
                UUID storeFilename = UUID.randomUUID();
                String storeFilenameNonExt = storeFilename.toString();
                String storeFilenameExt = storeFilenameNonExt+extractExt(originalFilename);
                
                
                //확장자를 제외하고 데이터베이스에 저장
                String StrUuid = storeFilenameNonExt.toString();
                
                //파일 타입
                String fileType = extractExt(originalFilename);
                
                //파일 사이즈
                long fileSize = multipartFile.getSize();
            	
                
                //***파일 정보 Vo에 저장*****
                fvo.setFileName(nonExtFileName);
                fvo.setStoreFileName(storeFilenameNonExt);
                fvo.setFileSize(fileSize);
                fvo.setFileType(fileType);
                fvo.setBoardNo(boardNo);
                //********
                
                File file = new File(getPath(today));
                if (!file.exists()) {
                    file.mkdir();
                } else {
                    System.out.println("동일한 이름의 디렉토리가 이미 존재합니다.");
                }
                
                //변경한 파일 이름(고유값) 서버에 저장
                multipartFile.transferTo(new File(getFullPath(today,storeFilenameExt)));
                storeFileSave.add(fvo);
            }
        }
        fileDaoInter.fileInfoSave(storeFileSave);
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos);
    }
    
    public List<Kmj_FilesVo> downloadFileList(int boardNo){
    	return fileDaoInter.downloadFileList(boardNo);
    }    
    
    public Kmj_FilesVo selectName(String fileName) {
    	return fileDaoInter.selectName(fileName);
    }
    
    public void deleteFile(String fileName) throws IOException {
    	String fileDate = fileDaoInter.fileDate(fileName);
    	fileDaoInter.deleteFile(fileName);
    	Kmj_FilesVo fileType = fileDaoInter.selectName(fileName);
    	
    	Path source = Paths.get(getFullPath(fileDate,fileName)+fileType.getFileType());
    	
    	Path movePath = Paths.get(getRecyclebinPath(fileName)+fileType.getFileType());
    	Files.move(source, movePath, StandardCopyOption.REPLACE_EXISTING);
    }
    
    public void emptyRecyclebin() throws IOException {
    	List<Kmj_FilesVo> emptyRecyclebinChk = fileDaoInter.emptyRecyclebinChk();
    	
    	
    	for(Kmj_FilesVo emptyFile : emptyRecyclebinChk) {
    	 Path emptyPath = Paths.get(getRecyclebinPath(emptyFile.getStoreFileName())+emptyFile.getFileType());
     	
    		if(!emptyRecyclebinChk.isEmpty()) {
    			Files.delete(emptyPath);
        		fileDaoInter.emptyRecyclebin(emptyFile.getStoreFileName());
        	}
    	}
    	
    }
    
    public int fileChk(int boardNo) {
    	return fileDaoInter.fileChk(boardNo);
    }
    
    
    public List<String> storeFileName(int boardNo){
    	return fileDaoInter.storeFileName(boardNo);
    }
    
    public String fileDate(String fileName) {
    	return fileDaoInter.fileDate(fileName);
    }
    
  
}
