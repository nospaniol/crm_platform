package com.crm.service;

import com.crm.model.Blacklist;
import com.crm.generic.GenericService;

public interface BlacklistService extends GenericService<Blacklist> {

    Blacklist findByEmail(String user);
}
