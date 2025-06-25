package kr.ac.dankook.ace.whatsinmyref.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class MyRecipeNo implements Serializable {

    private int user;
    private int recipe;

    public MyRecipeNo(){};
    public MyRecipeNo(int user, int recipe){
        super();
        this.user = user;
        this.recipe = recipe;
    }
}
