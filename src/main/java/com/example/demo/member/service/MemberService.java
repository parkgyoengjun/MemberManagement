package com.example.demo.member.service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.MemberEntity;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/*
    서비스 계층의 컴포넌트를 나타내는 데 사용
    해당 클래스가 비즈니스 로직을 수행하는 서비스 컴포넌트임을 명시
    Spring이 이 클래스를 스캔하여 빈으로 등록하게 됩니다.
 */
@RequiredArgsConstructor // final 필드나 @NonNull 어노테이션이 적용된 필드에 대한 생성자를 자동으로 생성
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {// repository의 save 메서드 호출(조건. entity객체를 넘겨줘야함)
        // save.html 에서 받은 정보를 MemberController의 save를 통해 MemberDTO에 담겨 옮겨지고
        // memberService 에서 받은 정보를 Entitiy(DB에 저장할수있게)로 전환 후 Repository 에 저장

        // 1. dto -> entity 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);

        // 2. repository 의 save 메서드 호출
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회를 함
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        // 2. DB 에서 조회한 이메일이 존재하는지, 비밀번호와 입력한 비밀번호가 일치하는지 판단
        if (byMemberEmail.isPresent()) {
            // isPresent()는 Optional 클래스에서 사용되는 메서드로,
            // 특정 값이 존재하는지를 확인하는 데 사용

            // 조회 결과가 있다(해당 이메일을 가지 회원 정보가 있다
            // Optional get() : 그 안의 값을 가져올때
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
//                return memberDTO; 받은 값 그대로 돌려줘도 아무 문제 없음 아이디랑 비밀번호 정도만 사용해서
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }

    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // EntityList 객체여서 근데 우리는 DTOList 객체를 Controller 로 보내줘야함(변환필요)
        List<MemberDTO> memberDTOList = new ArrayList<>();
        // Entity 가 여러개 담긴 List 를 DTO 여러개 List 에 담아야함
        for (MemberEntity a : memberEntityList) {
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
            memberDTOList.add(MemberDTO.toMemberDTO(a));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO 이 줄을 밑에 할줄로 표현한것

            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
            // Optional 객체의 포장을 벗겨야 할때는 .get() 사용
        } else return null;
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }
}
