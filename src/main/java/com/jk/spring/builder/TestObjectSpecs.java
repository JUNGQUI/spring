package com.jk.spring.builder;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestObjectSpecs {

    @Getter
    public enum SearchKey {
        ID("id"),
        NAME("name"),
        CONTENT("content");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }
    }

    public static Specification<TestObject> withName(String name) {
        return (Specification<TestObject>) ((root, query, builder) -> builder.equal(root.get("name"), name));
    }

    public static Specification<TestObject> withSearchKey(Map<SearchKey, String> searchKeyWithKeyword) {
        return (Specification<TestObject>) ((root, query, builder) -> {
            List<Predicate> predicates = getPredicateWithKeyword(searchKeyWithKeyword, root, builder);

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, String> searchKeyWithKeyword, Root<TestObject> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchKey searchKey : searchKeyWithKeyword.keySet()) {
            switch (searchKey) {
                case ID:
                case NAME:
                    predicates.add(builder.equal(
                            root.get(searchKey.value), searchKey.getValue()
                    ));
                    break;
                case CONTENT:
                    // like 검색
                    predicates.add(builder.like(
                            root.get(searchKey.value), "%" + searchKeyWithKeyword.get(searchKey) + "%"
                    ));
                    break;
            }
        }
        // 검색 조건은 무조건 하나이며, keyword 또한 하나이기 때문
        // 여러 검색조건에 대한 withName 식이 아니기에 만들어진 1개만 반환
        return predicates;
    }
}
