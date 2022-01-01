package com.crm.service.impl;

import com.crm.model.TagType;
import com.crm.repository.TagTypeRepository;
import com.crm.service.TagTypeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagTypeServiceImpl implements TagTypeService {

    @Autowired
    private TagTypeRepository bizRepository;

    @Override
    public TagType save(TagType entity) {
        return bizRepository.save(entity);
    }

    @Override
    public TagType update(TagType entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(TagType entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public TagType find(Long id) {
        Optional<TagType> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<TagType> findAll() {
        return (List<TagType>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<TagType> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public TagType findByTagTypeName(String name) {
        return bizRepository.findByTagTypeName(name);
    }

    @Override
    public TagType findByTagTypeId(Long id) {
        return bizRepository.findByTagTypeId(id);
    }

}
