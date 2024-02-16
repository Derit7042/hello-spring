package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // 추천하는 문법이 있는데, given when then 문법이다.

        // given  뭔가가 주어졌다.
        Member member = new Member();
        member.setName("spring");

        // when  이거를 실행했을 때
        Long saveId = memberService.join(member); // return 이 저장한 id가 튀어나오기로 했다.

        //then  결과가 이게 나와야 한다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        // 테스트가 길 때는 given 을 보고 "아 이 데이터를 기반으로 하는구나" when 을 보고 "아 이걸 검증하는구나" then 을 보고 "여기가 검증부구나"
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");  // 이름을 똑같이 spring 으로 하기

        // when
        memberService.join(member1);

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
        // 위 방법처럼 하는 방법이 있는데, 이것 때문에 try - catch 넣는게 되게 애매하다. 그래서 좋은 문법을 제공한다.

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// member2 를 넣으면 예외가 터져야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}