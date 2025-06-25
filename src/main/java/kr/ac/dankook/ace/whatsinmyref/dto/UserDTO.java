package kr.ac.dankook.ace.whatsinmyref.dto;

import kr.ac.dankook.ace.whatsinmyref.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

    private int memberNo;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberNick;

    public static UserDTO toUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setMemberNo(user.getMemberNo());
        userDTO.setMemberId(user.getMemberId());
        userDTO.setMemberPw(user.getMemberPw());
        userDTO.setMemberEmail(user.getMemberEmail());
        userDTO.setMemberNick(user.getMemberNick());

        return userDTO;
    }
}
