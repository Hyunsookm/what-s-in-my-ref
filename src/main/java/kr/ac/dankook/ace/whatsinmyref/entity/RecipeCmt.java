package kr.ac.dankook.ace.whatsinmyref.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RecipeCmt {
    //댓글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmtno;
    //댓글을 달 recipe 번호 ->Foreign key
    private int rno;

    private String comment;

    private String nickname;

    private Date time;

}
