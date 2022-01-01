package com.crm.service;

import com.crm.model.CitationType;
import com.crm.generic.GenericService;

public interface CitationTypeService extends GenericService<CitationType> {

    public CitationType findByCitationTypeName(String name);

    public CitationType findByCitationTypeId(Long id);

}
