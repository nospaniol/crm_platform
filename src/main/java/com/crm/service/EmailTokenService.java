package com.crm.service;

import com.crm.model.EmailToken;
import com.crm.generic.GenericService;

public interface EmailTokenService extends GenericService<EmailToken> {

    public EmailToken findByEmailAddress(String emailAddress);
}
