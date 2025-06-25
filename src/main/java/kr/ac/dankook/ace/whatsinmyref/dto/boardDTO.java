 package kr.ac.dankook.ace.whatsinmyref.dto;

 import java.io.Serializable;
 import java.time.LocalDateTime;

 public class boardDTO implements Serializable {

     private int id;
     private String title;
     private String content;
     private String nickname;
     private int viewcount;
     private LocalDateTime createdDate;

     public int getId() {
         return this.id;
     }

     public void setId(int id) {
         this.id = id;
     }

     public String getTitle() {
         return this.title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public String getContent() {
         return this.content;
     }

     public void setContent(String content) {
         this.content = content;
     }

     public String getNickname() {
         return this.nickname;
     }

     public void setNickname(String nickname) {
         this.nickname = nickname;
     }

     public int getViewcount() {
         return this.viewcount;
     }

     public void setViewcount(int viewcount) {
         this.viewcount = viewcount;
     }

     public LocalDateTime getCreatedDate() {
         return this.createdDate;
     }

     public void setCreatedDate(LocalDateTime createdDate) {
         this.createdDate = createdDate;
     }
    
 }
