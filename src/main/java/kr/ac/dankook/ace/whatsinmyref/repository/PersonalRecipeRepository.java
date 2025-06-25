package kr.ac.dankook.ace.whatsinmyref.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.ac.dankook.ace.whatsinmyref.entity.PersonalRecipe;


@Repository
public interface PersonalRecipeRepository extends JpaRepository<PersonalRecipe,Integer> {
    Page<PersonalRecipe> findAllByOrderByViewCountDesc(Pageable pageable);

    List<PersonalRecipe> findAllByNickname(String prevNick);
    
}
