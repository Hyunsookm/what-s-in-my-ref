package kr.ac.dankook.ace.whatsinmyref.controller;

import kr.ac.dankook.ace.whatsinmyref.dto.UserDTO;
import kr.ac.dankook.ace.whatsinmyref.dto.boardDTO;
import kr.ac.dankook.ace.whatsinmyref.entity.Board;
import kr.ac.dankook.ace.whatsinmyref.entity.PersonalRecipe;
import kr.ac.dankook.ace.whatsinmyref.entity.Recipe;
import kr.ac.dankook.ace.whatsinmyref.entity.RecipeCmt;
import kr.ac.dankook.ace.whatsinmyref.entity.RecipeLikes;
import kr.ac.dankook.ace.whatsinmyref.entity.Scrap;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.service.BoardService;
import kr.ac.dankook.ace.whatsinmyref.service.MyBoardService;
import kr.ac.dankook.ace.whatsinmyref.service.MyRecipeService;
import kr.ac.dankook.ace.whatsinmyref.service.PersonalRecipeService;
import kr.ac.dankook.ace.whatsinmyref.service.RecipeCmtService;
import kr.ac.dankook.ace.whatsinmyref.service.RecipeLikesService;
import kr.ac.dankook.ace.whatsinmyref.service.RecipeService;
import kr.ac.dankook.ace.whatsinmyref.service.ScrapService;
import kr.ac.dankook.ace.whatsinmyref.service.UserService;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Slf4j
@Controller
@RequestMapping("/Wimr")
@RequiredArgsConstructor
public class WhatsInMyRefController {

    @Autowired
    private final UserService userService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeCmtService recipeCmtService;
    @Autowired
    private ScrapService scrapService;
    @Autowired
    private RecipeLikesService recipeLikesService;
    @Autowired
    private PersonalRecipeService personalRecipeService;
    @Autowired
    private MyRecipeService myRecipeService;
    @Autowired
    private MyBoardService myBoardService;
    @Autowired
    private BoardService boardService;

    @GetMapping("")
    public String mainPage(Model model) {
        //test 임시값
        List<Recipe> recipe=recipeService.getTop3ByLikecount();
        model.addAttribute("first_rank_recipe",recipe.get(0));
        model.addAttribute("second_rank_recipe",recipe.get(1));
        model.addAttribute("third_rank_recipe",recipe.get(2));
        //test 임시값 여기까지
        return "index";
    }

    @GetMapping("/recipe/getRecipeByTitle/{title}")
    public String getRecipe(@PathVariable String title,HttpSession session, Model model) {
        int recipeNo=recipeService.getRecipeByTitleAndRecipenoLessThan(title,1000).getRecipeno();
        return "redirect:/Wimr/recipe/which/"+recipeNo;
    }
    
    
    @GetMapping("/recipe/which/{recipeno}")
    public String isPersonalRecipe(@PathVariable int recipeno) {
        if(personalRecipeService.getById(recipeno)!=null){
            return "redirect:/Wimr/recipe/usermade/"+recipeno;
        }
        return "redirect:/Wimr/recipe/"+recipeno;
    }
    

    @GetMapping("/recipe/{recipeno}")
    public String listRecipe(@PathVariable int recipeno,HttpSession session, Model model) {
        //Ingredients 재료
        //Recipe : 사진, 정보
        //others : 영양 성분
        List<String> manualList = new ArrayList<>();
        List<String> manualImgList = new ArrayList<>();
        recipeService.getRecipeById(recipeno).ifPresent(recipe -> {
            List<String> others = List.of("열량 : " + recipe.getCalories(), "탄수화물 : " + recipe.getCarbohydrates() ,"단백질 :" + recipe.getProtein(), "지방 : " + recipe.getFat(), "나트륨 : " + recipe.getSodium());
            List<String> ingredient = Arrays.asList(recipe.getIngredient().split(","));
            List<Recipe> scrapRecipeList=new ArrayList<>();
            List<Recipe> likeRecipeList=new ArrayList<>();
            if(session.getAttribute("user")!=null)
            {
                UserDTO loginUser=(UserDTO)session.getAttribute("user");
                scrapRecipeList=scrapService.getAllRecipesBymemberNo(loginUser.getMemberNo());
                likeRecipeList=recipeLikesService.getAllRecipesBymemberNo(loginUser.getMemberNo());
            }

            String k1 = "MANUAL0";
            String k2 = "MANUAL_IMG0";

            int i = 1;
            while(true) {
                String kee = k1.concat(Integer.toString(i));
                if (!recipe.getManual().get(kee).isEmpty()) {
                    manualList.add(recipe.getManual().get(kee));
                    i++;
                } else {
                    i = 1;
                    break;
                }
            }
            while(true){
                String kee = k2.concat(Integer.toString(i));
                if(!recipe.getManualImg().get(kee).isEmpty()){
                    manualImgList.add(recipe.getManualImg().get(kee));
                    i++;
                }
                else{
                    break;
                }
            }
            model.addAttribute("recipe", recipe);
            model.addAttribute("ingredients", ingredient);
            model.addAttribute("others", others);
            model.addAttribute("manualList", manualList);
            model.addAttribute("manualImgList", manualImgList);
            model.addAttribute("comments", recipeCmtService.findRecipeCmtsById(recipe.getRecipeno()));
            model.addAttribute("likeList", likeRecipeList);
            model.addAttribute("scrapList", scrapRecipeList);
        });
        return "recipe";
    }

    @GetMapping("/recipe/usermade/{recipeno}")
    public String listUserRecipe(@PathVariable int recipeno,HttpSession session, Model model) {
        //Ingredients 재료
        //Recipe : 사진, 정보
        //others : 영양 성분
        
        List<String> manualList = new ArrayList<>();
        List<String> manualImgList = new ArrayList<>();
        recipeService.getRecipeById(recipeno).ifPresent(recipe -> {
            PersonalRecipe personalRecipe=personalRecipeService.getById(recipe.getRecipeno());
            personalRecipe.setViewCount(personalRecipe.getViewCount()+1);
            personalRecipeService.save(personalRecipe);

            List<String> others = Arrays.asList(personalRecipe.getOthers().split("[,]"));
            List<String> ingredient = Arrays.asList(recipe.getIngredient().split(","));
            List<Recipe> scrapRecipeList=new ArrayList<>();
            List<Recipe> likeRecipeList=new ArrayList<>();
            if(session.getAttribute("user")!=null)
            {
                UserDTO loginUser=(UserDTO)session.getAttribute("user");
                scrapRecipeList=scrapService.getAllRecipesBymemberNo(loginUser.getMemberNo());
                likeRecipeList=recipeLikesService.getAllRecipesBymemberNo(loginUser.getMemberNo());
            }
            
            String k1 = "MANUAL0";
            String k2 = "MANUAL_IMG0";

            int i = 1;
            int count=0;
            while(true) {
                String kee = k1.concat(Integer.toString(i));
                if ((recipe.getManual().get(kee)!=null)&&!recipe.getManual().get(kee).isEmpty()) {
                    System.out.println("good");
                    manualList.add(recipe.getManual().get(kee));
                    i++;
                } else {
                    count=i;
                    i = 1;
                    break;
                }
            }
            for(;i<=count;i++){
                String kee = k2.concat(Integer.toString(i));
                manualImgList.add(recipe.getManualImg().get(kee));
            }
            model.addAttribute("nickname",personalRecipe.getNickname());
            model.addAttribute("recipe", recipe);
            model.addAttribute("ingredients", ingredient);
            model.addAttribute("others", others);
            model.addAttribute("manualList", manualList);
            model.addAttribute("manualImgList", manualImgList);
            model.addAttribute("comments", recipeCmtService.findRecipeCmtsById(recipe.getRecipeno()));
            model.addAttribute("likeList", likeRecipeList);
            model.addAttribute("scrapList", scrapRecipeList);
        });
        return "recipe";
    }

    @PostMapping("/recipe/{recipeno}")
    @ResponseBody
    public Map<String,String> addRecipeCmt(@PathVariable int recipeno, RecipeCmt recipeCmt, HttpSession session, @RequestHeader(value = "Referer", required = false) String referer) throws UnsupportedEncodingException {
        final boolean success;
        Map<String,String> response=new HashMap<>();

        // 로그인 상태 확인
        if (session.getAttribute("user") == null) {
            session.setAttribute("prevURL", referer);
            response = new HashMap<>();
            response.put("redirect", "/Wimr/login");
            response.put("errorMessage", "로그인을 해주세요.");
            return response;
        }

        Optional<Recipe> optionalRecipe = recipeService.getRecipeById(recipeno);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipeCmt.setTime(new Date());
            recipeCmt.setRno(recipe.getRecipeno());
            recipeCmt.setNickname(((UserDTO)session.getAttribute("user")).getMemberNick());
            success = recipeCmtService.saveRecipeCmt(recipeCmt);
        } else {
            success = false;
        }

        if (success) {
            response.put("message", "Scrap successful!");
        } else {
            response.put("message", "Scrap failed.");
        }

        return response;
    }

    @GetMapping("/register")
    public String save(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String save(@ModelAttribute UserDTO userDTO, Model model){
        if(userService.existsByMemberId(userDTO.getMemberId())){
            model.addAttribute("errorMessage", "이미 사용중인 아이디입니다.");
            return "register";
        }
        else if(userService.existsByMemberNick(userDTO.getMemberNick())){
            model.addAttribute("errorMessage", "이미 사용중인 닉네임입니다.");
            return "register";
        }
        else if(userService.existsByMemberEmail(userDTO.getMemberEmail())){
            model.addAttribute("errorMessage", "이미 사용중인 이메일입니다.");
            return "register";
        }
        userService.save(userDTO);
        model.addAttribute("successMessage", "정상적으로 회원가입되었습니다!");
        model.addAttribute("searchUrl","/Wimr");
        return "register";
    }
    
    
    @GetMapping("/login")
    public String login(@RequestHeader(value = "Referer", required = false) String referer, Model model,HttpSession session) {
        model.addAttribute("userDTO", new UserDTO());
        if(session.getAttribute("prevURL")==null){
            session.setAttribute("prevURL",referer );}
        return "login";
    }
    
    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserDTO userDTO, HttpSession session, Model model) {
        UserDTO loginResult = userService.login(userDTO);
        if(loginResult != null){
            session.setAttribute("user", loginResult);
            session.setMaxInactiveInterval(1800);
            //이전 페이지로 돌아가는 로직
            if(session.getAttribute("prevURL")!=null){
                String prevURL=(String)session.getAttribute("prevURL");
                session.removeAttribute("prevURL");
                return "redirect:"+prevURL;
            }
            return "redirect:/Wimr"; //로그인 성공 확인용
        } else{
            if(!userService.existsByMemberId(userDTO.getMemberId())){
            model.addAttribute("errorMessage", "존재하지 않는 회원 아이디입니다.");
            model.addAttribute("searchUrl","/Wimr/login");
            return "login";
        } else{
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("searchUrl","/Wimr/login");
                return "login";
            }
        }

    }

    @PostMapping("/logout")
    public String logoutUser(HttpSession session){
        session.invalidate();
        return "redirect:/Wimr";
    }
    
    @GetMapping("/myPage/{memberNick}")
    public String myPage(@PathVariable String memberNick, Model model) {
        UserDTO pageUser=userService.getByMemberNick(memberNick);
        List<Recipe> scrapRecipes=null;
        List<Recipe> myRecipes=null;
        List<Board> myBoards=null;

        //내가 작성한 글 불러오기
        if((myBoards=myBoardService.getAllBoardsBymemberNo(pageUser.getMemberNo()))==null){
            myBoards=new ArrayList<Board>();
        }

        //내가 등록한 레시피 불러오기
        if((myRecipes=myRecipeService.getAllRecipesBymemberNo(pageUser.getMemberNo()))==null){
            myRecipes=new ArrayList<Recipe>();
        }

        //스크랩한 레시피 불러오기
        if((scrapRecipes=scrapService.getAllRecipesBymemberNo(pageUser.getMemberNo()))==null){
        scrapRecipes=new ArrayList<Recipe>();
        }
        model.addAttribute("pageUser", pageUser);
        model.addAttribute("myBoardList", myBoards);
        model.addAttribute("myRecipeList", myRecipes);
        model.addAttribute("favoriteRecipeList", scrapRecipes);
        return "myPage";
    }

    @GetMapping("/foodSelect")
    public String foodSelect(){
        return "foodSelect";
    }

    @GetMapping("/findAcc")
    public String findAccount(@ModelAttribute UserDTO userDTO) {
        return "findAcc";
    }

    @PostMapping("/editProfile")
    public String editProfile(@RequestParam String memberNick, @RequestParam String memberEmail, HttpSession session,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        UserDTO loginUser=(UserDTO)session.getAttribute("user");
        System.out.println("Before update: " + loginUser);
        String prevNick=loginUser.getMemberNick();

        if(userService.existsByMemberNick(memberNick)){
            return "redirect:/Wimr/myPage/"+URLEncoder.encode(prevNick, "UTF-8");
        }

        if(memberNick!=null){
            loginUser.setMemberNick(memberNick);
        }
        if(memberEmail!=null){
            loginUser.setMemberEmail(memberEmail);
        }

        session.setAttribute("user", loginUser);
        System.out.println("After update: " + loginUser);

        userService.updateUser(loginUser);
        System.out.println("User updated");
        //board의 nick 변경
        if(boardService.getAllBoardsByNickname(prevNick)!=null){
            for(Board board:boardService.getAllBoardsByNickname(prevNick)){
                board.setNickname(memberNick);
                boardService.save(board);
            }
        }

        //recipe의 nick 변경
        if(personalRecipeService.getAllByNickname(prevNick)!=null){
            for(PersonalRecipe pRecipe:personalRecipeService.getAllByNickname(prevNick)){
                pRecipe.setNickname(memberNick);
                personalRecipeService.save(pRecipe);
            }
        }
        return "redirect:/Wimr/myPage/"+URLEncoder.encode(memberNick, "UTF-8");
    }
    
    @GetMapping("/editMyPage")
    public String editMyPage(HttpSession session,@RequestHeader(value = "Referer", required = false) String referer,Model model) {
        if(session.getAttribute("user") == null){
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("errorMessage","로그인을 해주세요.");
            model.addAttribute("searchUrl","/Wimr/login");
            //돌아오기위한 페이지 저장
            session.setAttribute("prevURL",referer );
            return "login";
        }
        return "editMyPage";
    }


    /*============================================================
    스크랩
    ==============================================================*/
    @PostMapping("/scrap")
    @ResponseBody
    public Map<String,String> doScrap(@RequestParam int recipeNo,HttpSession session,@RequestHeader(value = "Referer", required = false) String referer, Model model) {
        //로그인된 유저의 scrap 배열에 recipeNo 추가
        
        // 로그인 상태 확인
        if (session.getAttribute("user") == null) {
            session.setAttribute("prevURL", referer);
            Map<String, String> response = new HashMap<>();
            response.put("redirect", "/Wimr/login");
            response.put("errorMessage", "로그인을 해주세요.");
            return response;
        }
        User loginUser=User.toUser((UserDTO)session.getAttribute("user"));
        Recipe recipe=recipeService.getRecipeById(recipeNo).get();
        boolean success=scrapService.addToScrapList(loginUser, recipe);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Scrap successful!");
        } else {
            response.put("message", "Scrap failed.");
        }

        return response;
    }
    
    @PostMapping("/unscrap")
    @ResponseBody
    public Map<String,String> doUnscrap(@RequestParam int recipeNo,HttpSession session,@RequestHeader(value = "Referer", required = false) String referer, Model model) {
        //로그인된 유저의 scrap 배열에 recipeNo 제거
        if (session.getAttribute("user") == null) {
            session.setAttribute("prevURL", referer);
            Map<String, String> response = new HashMap<>();
            response.put("redirect", "/Wimr/login");
            response.put("errorMessage", "로그인을 해주세요.");
            return response;
        }
        User loginUser=User.toUser((UserDTO)session.getAttribute("user"));
        Recipe recipe=recipeService.getRecipeById(recipeNo).get();
        boolean success=scrapService.deleteScrap(loginUser, recipe);
        
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Scrap successful!");
        } else {
            response.put("message", "Scrap failed.");
        }
        return response;
    }
    
    /*============================================================
    좋아요
    ==============================================================*/
    @PostMapping("/like")
    @ResponseBody
    public Map<String,String> doLike(@RequestParam int recipeNo,HttpSession session,@RequestHeader(value = "Referer", required = false) String referer, Model model) {
        //로그인 체크
        if (session.getAttribute("user") == null) {
            session.setAttribute("prevURL", referer);
            Map<String, String> response = new HashMap<>();
            response.put("redirect", "/Wimr/login");
            response.put("errorMessage", "로그인을 해주세요.");
            return response;
        }
        User loginUser=User.toUser((UserDTO)session.getAttribute("user"));
        Recipe recipe=recipeService.getRecipeById(recipeNo).get();
        //like레시피에 추가
        boolean success= recipeLikesService.addTolikeList(loginUser, recipe);
        

        Map<String, String> response = new HashMap<>();
        if (success) {
            //레시피의 likecount 1 증가
            recipe.setLikecount(recipe.getLikecount()+1);
            recipeService.saveRecipe(recipe);
            response.put("message", "Scrap successful!");
        } else {
            response.put("message", "Scrap failed.");
        }
        return response;
    }

    @PostMapping("/unlike")
    @ResponseBody
    public Map<String,String> doUnlike(@RequestParam int recipeNo,HttpSession session,@RequestHeader(value = "Referer", required = false) String referer, Model model) {
        //로그인 체크
        if (session.getAttribute("user") == null) {
            session.setAttribute("prevURL", referer);
            Map<String, String> response = new HashMap<>();
            response.put("redirect", "/Wimr/login");
            response.put("errorMessage", "로그인을 해주세요.");
            return response;
        }
        User loginUser=User.toUser((UserDTO)session.getAttribute("user"));
        Recipe recipe=recipeService.getRecipeById(recipeNo).get();
        //like레시피 제거
        boolean success = recipeLikesService.deleteLike(loginUser, recipe);
        //레시피의 likecount 1 감소
        

        Map<String, String> response = new HashMap<>();
        if (success) {
            //레시피의 likecount 1 증가
            recipe.setLikecount(recipe.getLikecount()-1);
            recipeService.saveRecipe(recipe);
            response.put("message", "Scrap successful!");
        } else {
            response.put("message", "Scrap failed.");
        }
        return response;
    }

    /*============================================================
    아이디-비밀번호 찾기
    ==============================================================*/
  
    @PostMapping("/findAcc/find-id")
    public String findId(@ModelAttribute UserDTO userDTO, Model model) {
        String memberEmail = userDTO.getMemberEmail();
        System.out.println(memberEmail);
        String maskedMemberId = null;
        if(userService.existsByMemberEmail(memberEmail)){
            String memberId = userService.findMemberIdByMemberEmail(memberEmail);
            maskedMemberId = "**" + memberId.substring(2);
            System.out.println("success");  
        } else{
            model.addAttribute("errorMessage", "등록되지 않은 이메일입니다.");
            model.addAttribute("searchUrl","/Wimr/findAcc");
            return "findAcc";
        }
        model.addAttribute("masked_member_id", maskedMemberId);
        model.addAttribute("idSearchPerformed", true);
        return "findAcc";
    }
    

    @PostMapping("/findAcc/find-pwd")
    public String findPwd(@ModelAttribute UserDTO userDTO, HttpSession session, Model model) {
        String memberId = userDTO.getMemberId();
        String memberEmail = userDTO.getMemberEmail();
        if (userService.existsByMemberIdAndMemberEmail(memberId, memberEmail)) {
            UserDTO foundUser = userService.findByMemberIdAndMemberEmail(memberId, memberEmail);
            session.setAttribute("authenticatedUser",foundUser);
            session.setMaxInactiveInterval(1800);
            return "redirect:/Wimr/findAcc/rewritepw";
        } else {
            model.addAttribute("errorMessage", "회원님의 아이디와 이메일이 일치하지 않습니다.");
            model.addAttribute("searchUrl","/Wimr/findAcc");
            return "findAcc";
        }
    }
    
    /*============================================================
    비밀번호 변경
    ==============================================================*/
    @GetMapping("/findAcc/rewritepw")
    public String reWritePw(HttpSession session, Model model) {
        UserDTO authenticatedUser = (UserDTO) session.getAttribute("authenticatedUser");
        if (authenticatedUser == null) {
            model.addAttribute("errorMessage", "비정상적인 접근입니다.");
            model.addAttribute("searchUrl","/Wimr/login");
            return "reWritePw";
        }
        model.addAttribute("authenticatedUser", authenticatedUser);
        return "reWritePw";
    }

    @PostMapping("/doRewritepw")
    public String doReWritePw(@RequestParam("memberPw") String memberPw, HttpSession session, Model model) {
        UserDTO authenticatedUser = (UserDTO) session.getAttribute("authenticatedUser");
        System.out.println(authenticatedUser);
        if (authenticatedUser == null) {
            model.addAttribute("errorMessage", "비정상적인 접근입니다.");
            model.addAttribute("searchUrl","/Wimr/login");
            return "reWritePw";
        }
        authenticatedUser.setMemberPw(memberPw);
        System.out.println(authenticatedUser);
        userService.updateUser(authenticatedUser);
        session.invalidate();
        model.addAttribute("successMessage", "비밀번호 변경이 완료되었습니다.");
        model.addAttribute("searchUrl","/Wimr/login");
        return "reWritePw";
    }
    
    
}


