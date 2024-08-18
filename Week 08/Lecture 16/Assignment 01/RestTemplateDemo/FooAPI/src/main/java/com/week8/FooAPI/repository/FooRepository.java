package com.week8.FooAPI.repository;

import com.week8.FooAPI.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Long> {
}
