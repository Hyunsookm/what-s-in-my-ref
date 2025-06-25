package kr.ac.dankook.ace.whatsinmyref.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(int id){
        return recipeRepository.findById(id);
    }

    public Optional<Recipe> getRecipeByTitle(String title){ return recipeRepository.findByTitle(title);}

    public void saveRecipe(Recipe recipe){
        recipeRepository.save(recipe);
    }

    public List<Recipe> getTop3ByLikecount(){
        return recipeRepository.findTop3ByLikecountDesc();
    }

    public void getRecipes() {
        String url = "http://openapi.foodsafetykorea.go.kr/api/f415b345bda946528b8e/COOKRCP01/json/0/1000";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode rowNode = rootNode.path("COOKRCP01").path("row");
            if (rowNode.isArray()) {
                for (JsonNode node : rowNode) {
                    Recipe recipe = new Recipe();
                    recipe.setTitle(node.path("RCP_NM").asText());
                    recipe.setCalories(node.path("INFO_ENG").asText());
                    recipe.setCarbohydrates(node.path("INFO_CAR").asText());
                    recipe.setProtein(node.path("INFO_PRO").asText());
                    recipe.setFat(node.path("INFO_FAT").asText());
                    recipe.setSodium(node.path("INFO_NA").asText());
                    recipe.setIngredient(node.path("RCP_PARTS_DTLS").asText());
                    recipe.setPicture(node.path("ATT_FILE_NO_MAIN").asText());

                    Map<String, String> manual = new HashMap<>();
                    Map<String, String> manualImg = new HashMap<>();
                    for (int i = 1; i <= 20; i++) {
                        String manualKey = "MANUAL" + (i < 10 ? "0" : "") + i;
                        String manualImgKey = "MANUAL_IMG" + (i < 10 ? "0" : "") + i;
                        if (node.has(manualKey)) {
                            manual.put(manualKey, node.path(manualKey).asText());
                        }
                        if (node.has(manualImgKey)) {
                            manualImg.put(manualImgKey, node.path(manualImgKey).asText());
                        }
                    }

                    recipe.setManual(manual);
                    recipe.setManualImg(manualImg);

                    recipeRepository.save(recipe);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Recipe getRecipeByTitleAndRecipenoLessThan(String title, int recipeno) {
        return recipeRepository.findByTitleAndRecipenoLessThan(title,recipeno).get();
    }
}

//    @Autowired
//    public RecipeRepository recipeRepository;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    String url = "http://openapi.foodsafetykorea.go.kr/api/f415b345bda946528b8e/COOKRCP01/json/0/10";
//
//    public void fetchRecipes() {
//        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
//        if (response != null && response.getRecipe() != null){
//            for(Recipe recipe : response.getRecipe()) recipeRepository.save(recipe);
//        }
//    }
//}

//    private final RecipeRepository recipeRepository;
//    private final RestTemplate restTemplate;
//
//    public RecipeService(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//        this.restTemplate = new RestTemplate();
//    }
//
//    public void getRecipes(String url) {
//
//        ApiResponseDTO response = restTemplate.getForObject(url, ApiResponseDTO.class);
//
//        if (response != null && "success".equals(response.getStatus())) {
//            List<RecipeDTO> recipes = response.getData().getRecipes();
//            if (recipes != null) {
//                for (RecipeDTO recipeDto : recipes) {
//                    Recipe recipe = mapToEntity(recipeDto);
//                    recipeRepository.save(recipe);
//                }
//            }
//        }
//    }
//
//    private Recipe mapToEntity(RecipeDTO dto) {
//        Recipe recipe = new Recipe();
//        recipe.setTitle(dto.getRCP_NM());
//        recipe.setCalories(dto.getINFO_ENG());
//        recipe.setCarbohydrates(dto.getINFO_CAR());
//        recipe.setProtein(dto.getINFO_PRO());
//        recipe.setFat(dto.getINFO_FAT());
//        recipe.setSodium(dto.getINFO_NA());
//        recipe.setIngredients(dto.getRCP_PARTS_DTLS());
//        recipe.setPicture(dto.getATT_FILE_NO_MAIN());
//
//        List<Recipe.Manual> manuals = new ArrayList<>();
//        for (int i = 1; i <= 20; i++) {
//            String step = dto.getManualStep(i);
//            String image = dto.getManualImage(i);
//            if (step != null || image != null) {
//                Recipe.Manual manual = new Recipe.Manual();
//                manual.setStep(step);
//                manual.setImage(image);
//                manuals.add(manual);
//            }
//        }
//        recipe.setManuals(manuals);
//
//        return recipe;
//    }
