package kr.ac.dankook.ace.whatsinmyref.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode
public class RecipeLikesNo implements Serializable {

    private int user;
    private int recipe;

    public RecipeLikesNo(){}
    public RecipeLikesNo(int user, int recipe){
        super();
        this.user = user;
        this.recipe =recipe;
    }
}
