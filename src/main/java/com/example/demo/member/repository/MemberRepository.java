package com.example.demo.member.repository;

import com.example.demo.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // JpaRepository<EntityClassName, EntityPkType>

    // 이메일로 회원 정보 조회(select * from member_table where member_email=? )
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    // Optional은 null을 방지하고, 값의 존재 여부를 명확하게 처리할 수 있도록 도와주는 컨테이너
    // findByMemberEmail 이라는 메서드 이름도 다른것으로하면 못찾음


}
