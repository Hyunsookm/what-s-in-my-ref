package kr.ac.dankook.ace.whatsinmyref.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
// import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import kr.ac.dankook.ace.whatsinmyref.dto.UserDTO;
import kr.ac.dankook.ace.whatsinmyref.entity.Board;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.service.BoardService;
import kr.ac.dankook.ace.whatsinmyref.service.MyBoardService;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private MyBoardService myBoardService;

    @GetMapping("")
    public String toMainPage(){
        return "redirect:/Wimr";
    }
    
     // 게시글 목록
     @GetMapping("/boardList")
     public String BoardList(@ModelAttribute UserDTO userDTO, Model model, 
                             @RequestParam(required = false, defaultValue = "bno") String sort,
                             @PageableDefault(page = 0, size = 10, sort = "bno", direction = Sort.Direction.DESC) Pageable pageable) {
 
         Page<Board> boardList;
         if ("viewcount".equals(sort)) {
             boardList = boardService.boardListByViewCount(pageable);
         } else {
             boardList = boardService.boardList(pageable);
         }
 
         // 현재 페이지
         int nowPage = boardList.getPageable().getPageNumber() + 1;
         // 시작 페이지
         int startPage = Math.max(nowPage - 4, 1);
         // 끝 페이지
         int endPage = Math.min(nowPage + 5, boardList.getTotalPages());
 
         model.addAttribute("boardList", boardList);
         model.addAttribute("nowPage", nowPage);
         model.addAttribute("startPage", startPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("sort", sort);
         
         return "/board/boardList";
     }
 
    // 게시글 작성폼
    @GetMapping("/boardForm")
    public String BoardForm(HttpSession session, Model model){
        if (session.getAttribute("user")== null){
           
            model.addAttribute("errorMessage", "로그인이 필요한 서비스입니다.");
            model.addAttribute("searchUrl","/Wimr/login");
            return "/board/boardForm";
        }
        return "/board/boardForm";
        
    }
    // 작성폼 전송 처리
    @PostMapping("/board/writePro")
    public String boardwritePro(Board board,HttpSession session){
        boardService.write(board);

        myBoardService.save(User.toUser((UserDTO)session.getAttribute("user")),board);
        
        return "redirect:/boardList";
    }
    // 게시글 상세보기
    @GetMapping("/boardView") //localhost:8080/boardView?bno=1
    public String boardView(Model model,Integer bno){
        model.addAttribute("board",boardService.boardView(bno));
        // 조회수 증가
        boardService.increaseViewCount(bno);
        return "/board/boardView";
    }
    
    // 게시글 삭제
    @Transactional
    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam Integer bno){

        //myBoard 삭제
        myBoardService.deleteByBoard_bno(bno);

        return "redirect:/boardList";
    }

    @GetMapping("/food")
    public String Food() {
        return "/foodSelect";
    }

     // 게시글 수정 페이지 이동
    @GetMapping("/board/edit/{bno}")
    public String editBoard(@PathVariable("bno") Integer bno, Model model) {
        Board board = boardService.boardView(bno);
        model.addAttribute("board", board);
        return "board/boardEdit"; // boardEdit.html로 이동
    }

    // 게시글 수정 처리
    @PostMapping("/board/update/{bno}")
    public String updateBoard(@PathVariable("bno") Integer bno, @ModelAttribute Board board) {
        boardService.updateBoard(bno, board);
        return "redirect:/boardList"; // 수정 후 해당 게시글 페이지로 리다이렉트
    }

    @GetMapping("/myrecipeRegister")
    public String myRecipe(HttpSession session, Model model){
        if (session.getAttribute("user")== null){
           
            model.addAttribute("errorMessage", "로그인이 필요한 서비스입니다.");
            model.addAttribute("searchUrl","/Wimr/login");
            return "/myrecipeRegister";
        }
        return "/myrecipeRegister";
    }

}
