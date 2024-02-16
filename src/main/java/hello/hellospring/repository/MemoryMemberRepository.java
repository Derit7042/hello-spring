package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); // 실무에서 공유되는 변수일 때 동시성 문제가 있다.
    private static long sequence = 0L; // Sequence 는 0, 1, 2 이렇게 키 값을 생성해주는 애다. (Long 으로 하면 동시성 문제가 있다.)

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //setId 할 때 sequence 값을 하나 올려준다.
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return  Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 얘를 루프를 돌리고
                .filter(member -> member.getName().equals(name)) // filter 이라는 람다가 사용되는데, member.getName 이 파라미터로 넘어온 name 이랑 같은지 확인한다.
                .findAny(); // 같은 경우에 필터링 된 후에 그 중에서 찾으면 반환을 하는 것이다. findAny 는 하나로 찾는 것이다. 결과는 Optional 로 반환된다.
                // 맵에서 루프를 돌면서 하나를 찾으면 걔를 그냥 반환하고, 끝까지 돌렸는데 없으면 옵셔널에 null 이 포함되서 반환된다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store 에 있는 values 가 멤버들이다. 그래서 멤버들이 쫙 반환된다.
    }

    public void clearStore() {
        store.clear();
    }
}
