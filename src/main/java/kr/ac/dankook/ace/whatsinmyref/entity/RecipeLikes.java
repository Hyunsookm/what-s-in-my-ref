package kr.ac.dankook.ace.whatsinmyref.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="RecipeLikes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RecipeLikesNo.class)
public class RecipeLikes implements Serializable {

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="memberNo")
    private User user;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="recipeNo")
    private Recipe recipe;
}

