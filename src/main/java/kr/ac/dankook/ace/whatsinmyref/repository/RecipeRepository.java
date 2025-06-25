package kr.ac.dankook.ace.whatsinmyref.repository;

import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByTitle(String title);

    @Query("SELECT r FROM Recipe r ORDER BY r.likecount DESC")
    List<Recipe> findTop3ByLikecountDesc();

    Optional<Recipe> findByTitleAndRecipenoLessThan(String title, int recipeno);
}
