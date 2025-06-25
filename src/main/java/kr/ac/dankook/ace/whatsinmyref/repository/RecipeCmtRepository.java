package kr.ac.dankook.ace.whatsinmyref.repository;

import kr.ac.dankook.ace.whatsinmyref.entity.RecipeCmt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCmtRepository extends JpaRepository<RecipeCmt, Integer> {
    List<RecipeCmt> findRecipeCmtByRno(int id);
}
