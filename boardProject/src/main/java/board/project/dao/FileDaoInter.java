package board.project.dao;

import java.util.List;

import board.project.vo.Kmj_FilesVo;

public interface FileDaoInter {
	public void fileInfoSave(List<Kmj_FilesVo> list);
	public List<Kmj_FilesVo> downloadFileList(int boardNo);
	public Kmj_FilesVo selectName(String fileName);
	public void deleteFile(String fileName);
	public List<Kmj_FilesVo> emptyRecyclebinChk();
	public void emptyRecyclebin(String fileName);
	public int fileChk(int boardNo);
	public List<String> storeFileName(int boardNo);
	public String fileDate(String fileName);
}
