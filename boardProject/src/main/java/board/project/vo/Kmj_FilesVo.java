package board.project.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;


@Alias("fvo")
@Getter
@Setter
public class Kmj_FilesVo {
private String fileName,storeFileName,fileType,uploadDate,deleteDate;
private List<UploadFileVo> filesInfo; // 첨부 이미지
private List<MultipartFile> filesList;
private int boardNo,deleteChk;
private long fileSize;
}