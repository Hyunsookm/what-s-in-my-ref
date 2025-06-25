package kr.ac.dankook.ace.whatsinmyref.entity;

import jakarta.persistence.*;
import kr.ac.dankook.ace.whatsinmyref.dto.UserDTO;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String memberPw;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String memberNick;


    public static User toUser(UserDTO userDTO) {
        User user = new User();

        user.setMemberNo(userDTO.getMemberNo());
        user.setMemberId(userDTO.getMemberId());
        user.setMemberPw(userDTO.getMemberPw());
        user.setMemberNick(userDTO.getMemberNick());
        user.setMemberEmail(userDTO.getMemberEmail());

        return user;
    }
}
