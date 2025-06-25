package kr.ac.dankook.ace.whatsinmyref.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="myRecipe")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MyRecipeNo.class)
public class MyRecipe {

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="memberNo")
    private User user;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="recipeno")
    private Recipe recipe;
}
