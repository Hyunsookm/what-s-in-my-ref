package kr.ac.dankook.ace.whatsinmyref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.Scrap;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.repository.ScrapRepository;

@Service
public class ScrapService {
    @Autowired
    ScrapRepository scrapRepository;

    public List<Scrap> getAllBymemberNo(int memberNo){
        return scrapRepository.findAllByUser_memberNo(memberNo);
    }

    //Recipe의 리스트로 반환 
    public List<Recipe> getAllRecipesBymemberNo(int memberNo){
        List<Recipe> scrapRecipes=new ArrayList<>();

        for(Scrap s:getAllBymemberNo(memberNo)){
                scrapRecipes.add(s.getRecipe());
            }
        return scrapRecipes;
    }

    public boolean addToScrapList(User user,Recipe recipe){
        Scrap scrap=new Scrap(user,recipe);
        if(scrapRepository.save(scrap)!=null)   return true;
        return false;
    }

    public boolean deleteScrap(User user,Recipe recipe){
        Scrap scrap=new Scrap(user,recipe);
        scrapRepository.delete(scrap);
        if(scrapRepository.findByUserAndRecipe(user,recipe)==null) {return true;}
        return false;
    }
}
