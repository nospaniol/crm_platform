package com.crm.service;

import com.crm.model.TagType;
import com.crm.generic.GenericService;

public interface TagTypeService extends GenericService<TagType> {

    public TagType findByTagTypeName(String name);

    public TagType findByTagTypeId(Long id);

}
