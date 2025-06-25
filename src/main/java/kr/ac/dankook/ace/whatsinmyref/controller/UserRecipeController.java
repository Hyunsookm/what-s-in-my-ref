package kr.ac.dankook.ace.whatsinmyref.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import kr.ac.dankook.ace.whatsinmyref.dto.UserDTO;

import kr.ac.dankook.ace.whatsinmyref.entity.PersonalRecipe;
import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.service.FileService;
import kr.ac.dankook.ace.whatsinmyref.service.MyRecipeService;
import kr.ac.dankook.ace.whatsinmyref.service.PersonalRecipeService;
import kr.ac.dankook.ace.whatsinmyref.service.RecipeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/Wimr")
@RequiredArgsConstructor
public class UserRecipeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private PersonalRecipeService personalRecipeService;
    @Autowired
    private MyRecipeService myRecipeService;

    // 레시피 등록폼 처리
    @PostMapping("/userrecipe/register")
    public String registerPro(@RequestParam("recipe_title") String title,
                            @RequestParam("ingredient") String ingredient,
                            @RequestParam("tip") String tip,
                            @RequestParam("stepDescription") String[] stepDescriptions,
                            @RequestParam("manualImg") MultipartFile[] manualImages,
                            @RequestParam("mainImg") MultipartFile mainImage,
                            HttpSession session) throws Exception{
        
        Recipe userRecipe = new Recipe();
        PersonalRecipe personalRecipe=null;
        int recipeNo;
        String extension;
        String mainImagePath;
        Map<String, String> manual = new HashMap<>();
        Map<String, String> manualImg = new HashMap<>();
        UserDTO loginUser=(UserDTO)session.getAttribute("user");

        userRecipe.setTitle(title);
        userRecipe.setIngredient(ingredient);
        recipeService.saveRecipe(userRecipe);
        //요리 메인 이미지 저장
        recipeNo=recipeService.getRecipeByTitle(title).get().getRecipeno();
        extension=mainImage.getOriginalFilename().substring(mainImage.getOriginalFilename().lastIndexOf("."));
        mainImagePath=fileService.saveFile(mainImage,recipeNo,extension); //대표 이미지는 /recipeno/mainImage.
        userRecipe.setPicture(mainImagePath);
        
        try {//대표 이미지는 /recipeno/manual0.
            for (int i=0;i<stepDescriptions.length; i++) {
                String manualKey = "MANUAL" + (i < 10 ? "0" : "") + Integer.toString(i+1);
                String manualImgKey = "MANUAL_IMG" + (i < 10 ? "0" : "") + Integer.toString(i+1);
                manual.put(manualKey, Integer.toString(i+1)+". "+stepDescriptions[i]);
                manualImg.put(manualImgKey,fileService.saveManualFile(manualImages[i],recipeNo,i+1,extension));
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

        userRecipe.setManual(manual);
        userRecipe.setManualImg(manualImg);
        recipeService.saveRecipe(userRecipe);

        personalRecipe=new PersonalRecipe(recipeNo,tip,loginUser.getMemberNick(),new Date(),0);
        personalRecipeService.save(personalRecipe);

        myRecipeService.save(User.toUser(loginUser),userRecipe);

        return "redirect:/Wimr/recipe/usermade/"+userRecipe.getRecipeno();
    }


    // 게시글 목록
    @GetMapping("/userRecipes")
    public String userRecipeList(@ModelAttribute UserDTO userDTO, Model model, 
                             @RequestParam(required = false, defaultValue = "recipeno") String sort,
                             @PageableDefault(page = 0, size = 10, sort = "recipeno", direction = Sort.Direction.DESC) Pageable pageable){

        Page<PersonalRecipe> personalRecipeList;

         if ("viewCount".equals(sort)) {
            personalRecipeList = personalRecipeService.getPersonalRecipeListByViewCount(pageable);
         } else {
             personalRecipeList = personalRecipeService.getPersonalRecipeList(pageable);
         }

        // 현재 페이지
         int nowPage = personalRecipeList.getPageable().getPageNumber() + 1;
         // 시작 페이지
         int startPage = Math.max(nowPage - 4, 1);
         // 끝 페이지
         int endPage = Math.min(nowPage + 5, personalRecipeList.getTotalPages());


        // 예시: recipe 리스트를 가져옴
        List<Recipe> recipeList = recipeService.getAllRecipes();

        // recipeno를 키로 하고 recipe 객체를 값으로 하는 맵 생성
        Map<Integer, Recipe> recipeMap = recipeList.stream().collect(Collectors.toMap(Recipe::getRecipeno, recipe -> recipe));
        
        model.addAttribute("recipeMap", recipeMap);
        model.addAttribute("list", personalRecipeList);
        model.addAttribute("recipes", recipeService.getAllRecipes());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sort", sort);
        return "userRecipeList";
    }
}
