package kr.ac.dankook.ace.whatsinmyref.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
// import kr.ac.dankook.ace.whatsinmyref.dto.boardDTO;
import lombok.Data;
// import lombok.EqualsAndHashCode;

@Table(name = "board")
@Data
@Entity
@Embeddable
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String title;
    private String content;
    private String nickname;
    private int viewcount;
    private LocalDateTime created_date;
    private int likes;

    public int getBno() {
        return this.bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
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

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreated_date() {
        return this.created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    

    // 엔티티가 저장되기 전에 초기화
    @PrePersist
    public void prePersist() {
        this.created_date = LocalDateTime.now(); // 현재 날짜 및 시간으로 초기화
    }
    
}
