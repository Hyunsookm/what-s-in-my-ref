package kr.ac.dankook.ace.whatsinmyref.service;

import kr.ac.dankook.ace.whatsinmyref.dto.UserDTO;
import kr.ac.dankook.ace.whatsinmyref.entity.User;
import kr.ac.dankook.ace.whatsinmyref.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserDTO userDTO){
        User user = User.toUser(userDTO);
        userRepository.save(user);
    }
    public UserDTO login(UserDTO userDTO){ 
        Optional<User> byUserId = userRepository.findByMemberId(userDTO.getMemberId());
        if(byUserId.isPresent()){
            User memberEntity = byUserId.get();
            if(memberEntity.getMemberPw().equals(userDTO.getMemberPw())) {
                UserDTO dto = UserDTO.toUserDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    @Transactional
    public boolean existsByMemberId(String memberId){
        return userRepository.existsByMemberId(memberId);
    }
    @Transactional
    public boolean existsByMemberNick(String memberNick){
        return userRepository.existsByMemberNick(memberNick);
    }
    @Transactional
    public boolean existsByMemberEmail(String memberEmail){
        return userRepository.existsByMemberEmail(memberEmail);
    }
    @Transactional
    public boolean existsByMemberIdAndMemberEmail(String memberId, String memberEmail){
        return userRepository.existsByMemberIdAndMemberEmail(memberId, memberEmail);
    }

    public String findMemberIdByMemberEmail(String memberEmail){
        User user = userRepository.findByMemberEmail(memberEmail);
        return user != null ? user.getMemberId() : null;
    }

    public UserDTO findByMemberIdAndMemberEmail(String memberId, String memberEmail) {
        return UserDTO.toUserDTO(userRepository.findByMemberIdAndMemberEmail(memberId, memberEmail));
    }

    public UserDTO findByMemberNo(int memberNo){
        return UserDTO.toUserDTO(userRepository.findByMemberNo(memberNo));
    }

    public User updateUser(UserDTO updateUser){

        User user=User.toUser(updateUser);

        return userRepository.save(user);
    }

    public UserDTO getByMemberNick(String memberNick) {
        return UserDTO.toUserDTO(userRepository.findByMemberNick(memberNick));
    }
    
}
