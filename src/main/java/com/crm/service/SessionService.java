package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Sessions;

public interface SessionService extends GenericService<Sessions> {

    Sessions findBySessionName(String sessionName);

    Sessions findBySessionLatest();
}
