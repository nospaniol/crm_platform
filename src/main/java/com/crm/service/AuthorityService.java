package com.crm.service;

import com.crm.model.Authority;
import com.crm.generic.GenericService;

public interface AuthorityService extends GenericService<Authority> {

    Authority findByRole(String role);
}
