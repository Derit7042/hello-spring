package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository(); // 일단 이렇게 만든다. 다형성 문제로 MemoryMemberRepository 말고 MemberRepository 으로 함

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    // 만들었던 save 기능이 동작하는지 확인해보면 된다.
    @Test // 테스트 하는건 @Test 를 붙여준다. 그러면 이제부터 이걸 실행할 수 있다.
    public void save() { // 멤버 저장이 잘 되는지 테스트 해볼 것이다.
        Member member = new Member();
        member.setName("spring"); // 이름을 세팅한다.

        repository.save(member); // 리포지토리에 멤버를 저장한다.

        Member result = repository.findById(member.getId()).get(); // 마지막에 검증을 한다. 반환 타입이 Optional 이다. Optional 에서 값을 꺼낼 때는 .get() 으로 꺼낼 수 있다. 좋은 방법은 아니다.

        // 이제 검증을 해야 하는데, 검증을 어떻게 해야 할까?
        // 저장한 new Member 에서 한 것과, 내가 db 에서 꺼낸 것(member.getId()) 과 똑같으면 참이 된다.
        // System.out.println("result = " + (result == member));
        // Assertions.assertEquals(member, result); // org.junit.jupiter.api
        assertThat(member).isEqualTo(result); // 요즘에는 이거를 조금 더 많이 쓴다.
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
