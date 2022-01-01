package com.crm.service;

import com.crm.model.UserOtp;
import com.crm.generic.GenericService;
import com.crm.model.User;

public interface UserOtpService extends GenericService<UserOtp> {

    UserOtp findByUser(User user);
}
