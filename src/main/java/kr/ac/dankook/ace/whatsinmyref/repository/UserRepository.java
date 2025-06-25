package kr.ac.dankook.ace.whatsinmyref.repository;

import kr.ac.dankook.ace.whatsinmyref.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< User, Integer> {
  Optional<User> findByMemberId(String memberId);
  boolean existsByMemberId(String memberId);
  boolean existsByMemberNick(String memberNick);
  boolean existsByMemberEmail(String memberEmail);
  boolean existsByMemberIdAndMemberEmail(String memberId, String memberEmail);
  User findByMemberEmail(String memberEmail);
  User findByMemberNo(int memberNo);
  User findByMemberNick(String memberNick);
  User findByMemberIdAndMemberEmail(String memberId, String memberEmail);
}

