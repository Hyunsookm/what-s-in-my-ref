package kr.ac.dankook.ace.whatsinmyref.repository;

import kr.ac.dankook.ace.whatsinmyref.entity.MyRecipe;
import kr.ac.dankook.ace.whatsinmyref.entity.MyRecipeNo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRecipeRepository extends JpaRepository<MyRecipe, MyRecipeNo> {

    List<MyRecipe> findAllByUser_memberNo(int memberNo);

}
