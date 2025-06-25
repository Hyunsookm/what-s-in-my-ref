package kr.ac.dankook.ace.whatsinmyref.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode
public class MyBoardNo implements Serializable {

    private int user;
    private int board;

    public MyBoardNo(){};
    public MyBoardNo(int user, int board){
        super();
        this.user = user;
        this.board = board;
    }}
