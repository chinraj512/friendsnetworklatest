package com.backend.code.Repoistry;

import org.springframework.data.repository.CrudRepository;

import com.backend.code.Entity.ConfirmationToken;



public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
