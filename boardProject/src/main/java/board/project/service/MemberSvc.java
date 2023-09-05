package board.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import board.project.dao.MemberDaoInter;
import board.project.vo.Kmj_BoardVo;
import board.project.vo.Kmj_MemberTableVo;

@Service
public class MemberSvc{
	
	@Autowired
	private MemberDaoInter memberDaoInter;

	
	public void memberInsert(Kmj_MemberTableVo vo) {
		
		memberDaoInter.memberInsert(vo);
	}
	
	
}
