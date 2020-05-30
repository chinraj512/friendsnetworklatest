package com.backend.code.Repoistry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.code.Entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByEmailIgnoreCase(String emailId);
}
