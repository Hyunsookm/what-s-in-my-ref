package kr.ac.dankook.ace.whatsinmyref.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeno;
//    대표 사진
    private String picture;

    private String title;
    //재료
    private String ingredient;
    //열량
    private String calories;
    //탄수화물
    private String carbohydrates;
    //단백질
    private String protein;
    //지방
    private String fat;
    //나트륨
    private String sodium;


    @ElementCollection
    private Map<String, String> manual;

    @ElementCollection
    private Map<String, String> manualImg;

//    @Column(columnDefinition = "default 'asdf'")
//    private String nickname;

    private int likecount;
}
