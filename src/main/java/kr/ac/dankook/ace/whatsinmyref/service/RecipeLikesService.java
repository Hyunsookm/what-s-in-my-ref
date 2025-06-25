package kr.ac.dankook.ace.whatsinmyref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.RecipeLikes;
import kr.ac.dankook.ace.whatsinmyref.entity.Scrap;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.repository.RecipeLikesRepository;

@Service
public class RecipeLikesService {
    @Autowired
    RecipeLikesRepository recipeLikesRepository;

    //memberNo로 찾은 좋아요한 Recipe의 리스트로 반환 
    public List<Recipe> getAllRecipesBymemberNo(int memberNo){
        List<Recipe> likeRecipes=new ArrayList<>();

        for(RecipeLikes l: recipeLikesRepository.findAllByUser_memberNo(memberNo)){
                likeRecipes.add(l.getRecipe());
            }
        return likeRecipes;
    }

    //유저-레시피로 좋아요한 레시피 추가
    public boolean addTolikeList(User user,Recipe recipe){
        RecipeLikes likeRecipe=new RecipeLikes(user,recipe);
        if(recipeLikesRepository.save(likeRecipe)!=null)    return true;
        return false;
    }

    public boolean deleteLike(User user,Recipe recipe){
        RecipeLikes likeRecipe=new RecipeLikes(user,recipe);
        recipeLikesRepository.delete(likeRecipe);
        if(recipeLikesRepository.findByUserAndRecipe(user,recipe)==null) {return true;}
        return false;
    }
}
