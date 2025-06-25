package kr.ac.dankook.ace.whatsinmyref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.dankook.ace.whatsinmyref.dto.UserDTO;
import kr.ac.dankook.ace.whatsinmyref.entity.MyRecipe;
import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.Scrap;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.repository.MyRecipeRepository;

@Service
public class MyRecipeService {

    @Autowired
    MyRecipeRepository myRecipeRepository;

    public MyRecipe save(User user, Recipe userRecipe) {
        MyRecipe myRecipe=new MyRecipe(user,userRecipe);
        return myRecipeRepository.save(myRecipe);
    }

    public List<Recipe> getAllRecipesBymemberNo(int memberNo) {
        List<Recipe> myRecipes=new ArrayList<>();

        for(MyRecipe mr:myRecipeRepository.findAllByUser_memberNo(memberNo)){
                myRecipes.add(mr.getRecipe());
            }
        return myRecipes;
    }

}
