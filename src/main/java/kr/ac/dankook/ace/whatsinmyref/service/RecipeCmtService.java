package kr.ac.dankook.ace.whatsinmyref.service;

import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.RecipeCmt;
import kr.ac.dankook.ace.whatsinmyref.repository.RecipeCmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeCmtService {

    @Autowired
    private RecipeCmtRepository recipeCmtRepository;

    public List<RecipeCmt> findRecipeCmtsById(int id){
        return recipeCmtRepository.findRecipeCmtByRno(id);
    }

    public boolean saveRecipeCmt(RecipeCmt recipeCmt){
        if(recipeCmtRepository.save(recipeCmt)!=null) {return true;}
        return false;
    }
}
