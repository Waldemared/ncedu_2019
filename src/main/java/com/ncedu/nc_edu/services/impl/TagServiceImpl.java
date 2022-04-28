package com.ncedu.nc_edu.services.impl;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.Tag;
import com.ncedu.nc_edu.repositories.TagRepository;
import com.ncedu.nc_edu.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    public TagServiceImpl(@Autowired TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findAllByNameContains(String name) {
        return this.tagRepository.findAllByNameContaining(name, PageRequest.of(0, 10)).getContent();
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findById(name).orElseThrow(()-> new EntityDoesNotExistsException("tag"));
    }

    @Override
    public boolean existsByName(String name) {
        return tagRepository.existsById(name);
    }

    @Override
    public Tag add(String name) {
        Optional<Tag> tagOptional = tagRepository.findById(name);

        if (tagOptional.isPresent()) {
            return tagOptional.get();
        }

        Tag tag = new Tag();
        tag.setName(name);

        return tagRepository.save(tag);
    }
}
