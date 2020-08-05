package com.jk.spring.builder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TestObjectRepository extends JpaRepository<TestObject, Long>, JpaSpecificationExecutor<TestObject> {
    List<TestObject> findByName(String name);
    List<TestObject> findByContentLike(String content);
}
