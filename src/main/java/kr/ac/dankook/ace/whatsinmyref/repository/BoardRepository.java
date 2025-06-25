package kr.ac.dankook.ace.whatsinmyref.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.ac.dankook.ace.whatsinmyref.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
  Page<Board> findAllByOrderByViewcountDesc(Pageable pageable);

  List<Board> findAllByNickname(String nickname);
} 