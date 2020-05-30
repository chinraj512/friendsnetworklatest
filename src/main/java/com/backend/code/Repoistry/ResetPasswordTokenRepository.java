package com.backend.code.Repoistry;

import org.springframework.data.repository.CrudRepository;


import com.backend.code.Entity.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, String> {
    ResetPasswordToken findByConfirmationToken(String confirmationToken);
}
