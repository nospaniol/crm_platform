package com.crm.service;

import com.crm.model.FeeType;
import com.crm.generic.GenericService;

public interface FeeTypeService extends GenericService<FeeType> {

    public FeeType findByFeeTypeName(String name);

    public FeeType findByFeeTypeId(Long id);

}
