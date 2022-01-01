package com.crm.service;

import com.crm.model.PhoneToken;
import com.crm.generic.GenericService;

public interface PhoneTokenService extends GenericService<PhoneToken> {

    public PhoneToken findByPhoneNumber(String phoneNumber);
}
