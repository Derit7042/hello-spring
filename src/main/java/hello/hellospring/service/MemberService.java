package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입부터 만들어 볼 것이다.
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 X
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {  //  ifPresent : 만약 값이 있으면
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });    // ifPresent 는 얘가 null 이 아니라 어떤 값이 있으면 동작을 하는 것이다. 옵셔널이기 때문에 가능한거다. 기존에는 if null 이 아니면 까지 해야한다.



        // 옵셔널 쓸 때 팁인데, 옵셔널을 바로 반환하는게 별로 좋지 않다. 어차피 result 가 반환되었기 떄문에 바로 ifPresent 가 들어갈 수 있다.
//        memberRepository.findByName(member.getName())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                }); //이런 식으로 코드를 짤 수 있다.

        // 근데 잘 보면 findByName 에서 로직이 쭉 나온다. 이런 경우에는 method 로 뽑는 게 좋다.
        // 컨트롤 알트 M 누르면 extract method 라는 것이 있다. 그렇게 뽑으면 된다.
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
