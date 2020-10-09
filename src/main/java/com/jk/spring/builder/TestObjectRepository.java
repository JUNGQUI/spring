package com.jk.spring.builder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Propagation.REQUIRED
 * Propagation.REQUIRES_NEW
 * Propagation.SUPPORTS
 * Propagation.NOT_SUPPORTED
 * Propagation.NEVER
 * Propagation.NESTED
 * Propagation.MANDATORY
 *
 * Isolation.DEFAULT
 * Isolation.READ_COMMITTED
 * Isolation.READ_UNCOMMITTED
 * Isolation.REPEATABLE_READ
 * Isolation.SERIALIZABLE
 */

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public interface TestObjectRepository extends JpaRepository<TestObject, Long>, TestObjectRepositoryCustom, JpaSpecificationExecutor<TestObject> {
    List<TestObject> findByName(String name);
    List<TestObject> findByContentLike(String content);
}
