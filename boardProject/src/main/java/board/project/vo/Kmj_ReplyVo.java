package board.project.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("rvo")
@Getter
@Setter
public class Kmj_ReplyVo {
private int replyNo, associateNo, boardNo, postOpenId,depth;
private String id, comments, createDate, updateDate;
}
