package com.crm.service;

import com.crm.model.State;
import com.crm.generic.GenericService;

public interface StateService extends GenericService<State> {

    public State findByStateName(String name);

    public State findByStateId(Long id);

}
