package kr.ac.dankook.ace.whatsinmyref.repository;

import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.Scrap;
import kr.ac.dankook.ace.whatsinmyref.entity.ScrapNo;
import kr.ac.dankook.ace.whatsinmyref.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScrapRepository extends JpaRepository<Scrap, ScrapNo>{
    List<Scrap> findAllByUser_memberNo(int memberno);


    Object findByUserAndRecipe(User user,Recipe recipe);

}

