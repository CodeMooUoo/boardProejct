package board.project.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("mvo")
@Getter
@Setter
public class Kmj_MemberTableVo {
private String id, pwd, memberdate;
}
