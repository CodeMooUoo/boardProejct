package board.project.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("bvo")
@Getter
@Setter
public class Kmj_BoardVo {
private int boardNo,associateNo,postOpenId,fileCheck,depth;
private String id,title,contents,createDate,updateDate;
}
