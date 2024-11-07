package com.example.demo.member.dto;

import com.example.demo.member.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 만들어준다 , @AllArgsConstructor 와 비슷함
@ToString
public class MemberDTO { // 회원정보의 필요한 내용들을 필드에 정의하는 곳, 클라이언트에서 보낸 정보들
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) { // entity -> dto
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
