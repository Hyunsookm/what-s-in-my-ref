package kr.ac.dankook.ace.whatsinmyref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.ac.dankook.ace.whatsinmyref.entity.Board;
import kr.ac.dankook.ace.whatsinmyref.entity.MyBoard;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.repository.MyBoardRepository;

@Service
public class MyBoardService {

    @Autowired
    MyBoardRepository myBoardRepository;

    public List<Board> getAllBoardsBymemberNo(int memberNo) {
        List<Board> myBoards=new ArrayList<>();

        for(MyBoard b:myBoardRepository.findAllByUser_memberNo(memberNo)){
            myBoards.add(b.getBoard());
        }
        return myBoards;
    }

    public void deleteByBoard_bno(Integer bno) {
        myBoardRepository.deleteByBoard_bno(bno);
    }

    public MyBoard save(User user, Board board) {
        return myBoardRepository.save(new MyBoard(user,board));
    }
    
}
