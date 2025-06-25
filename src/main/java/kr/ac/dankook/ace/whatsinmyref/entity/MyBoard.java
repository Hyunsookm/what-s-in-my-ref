package kr.ac.dankook.ace.whatsinmyref.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="myBoard")
@IdClass(MyBoardNo.class)
public class MyBoard implements Serializable {

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="memberNo")
    private User user;

    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonBackReference
    @JoinColumn(name="bno")
    private Board board;

}
