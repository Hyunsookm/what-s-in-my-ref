package kr.ac.dankook.ace.whatsinmyref.repository;

import kr.ac.dankook.ace.whatsinmyref.entity.MyBoard;
import kr.ac.dankook.ace.whatsinmyref.entity.MyBoardNo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBoardRepository extends JpaRepository<MyBoard,MyBoardNo> {

    List<MyBoard> findAllByUser_memberNo(int memberNo);

    void deleteByBoard_bno(Integer bno);

}
