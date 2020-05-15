package com.backend.code.Repoistry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.code.Objects.UserDetails;

@Repository
public interface jpaRepository extends CrudRepository<UserDetails, Integer> {
    org.springframework.security.core.userdetails.UserDetails findByUsername(String username);
}