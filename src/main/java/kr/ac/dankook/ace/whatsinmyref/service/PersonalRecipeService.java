package kr.ac.dankook.ace.whatsinmyref.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.ac.dankook.ace.whatsinmyref.entity.PersonalRecipe;
import kr.ac.dankook.ace.whatsinmyref.repository.PersonalRecipeRepository;

@Service
public class PersonalRecipeService {
    @Autowired
    private PersonalRecipeRepository personalRecipeRepository;

    public PersonalRecipe save(PersonalRecipe personalRecipe) {
        return personalRecipeRepository.save(personalRecipe);
    }

    public PersonalRecipe getById(int recipeno) {
        if(personalRecipeRepository.findById(recipeno).isPresent())
        {
            return personalRecipeRepository.findById(recipeno).get();
        }
        else return null;
    }

    public Page<PersonalRecipe> getPersonalRecipeListByViewCount(Pageable pageable) {
        return personalRecipeRepository.findAllByOrderByViewCountDesc(pageable);
    }

    public Page<PersonalRecipe> getPersonalRecipeList(Pageable pageable) {
        return personalRecipeRepository.findAll(pageable);
    }

    public List<PersonalRecipe> getAllByNickname(String prevNick) {
        return personalRecipeRepository.findAllByNickname(prevNick);
    }

}
