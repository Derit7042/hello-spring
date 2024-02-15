package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원을 저장하면 저장된 회원이 반환된다.
    Optional<Member> findById(Long id); // id로 회원을 찾는 것이다.
    Optional<Member> findByName(String name);
    // Optional 을 간단하게 설명하면 findById 또는 findByName 로 가져오는데, 없으면 null 을 가져오는데,
    // null 을 그대로 반환하는 방법 대신에 Optional 이라는 것으로 감싸서 반환하는 방법을 많이 선호한다. 나중에 자세히 알아본다.
    List<Member> findAll();
    // 이렇게 repository 의 4가지 기능을 만들었다.
    // save 하면 회원이 저장소에 저장된다.
    // 그 다음부터는 findById 또는 findByName 으로 찾아올 수 있다.
    // findAll 하면 지금까지 저장된 모든 회원 리스트를 다 반환해준다.
}
