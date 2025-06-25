package kr.ac.dankook.ace.whatsinmyref.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardComment {
    //댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmtno;
    //댓글을 달 게시글 번호 ->Foreign key
    private int bno;
    private String comment;
    //작성자
    private String nickname;
    private Date time;
}
