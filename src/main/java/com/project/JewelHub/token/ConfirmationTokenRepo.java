package com.project.JewelHub.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepo extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
