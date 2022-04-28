package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.dto.assemblers.TagAssembler;
import com.ncedu.nc_edu.dto.resources.TagResource;
import com.ncedu.nc_edu.models.Recipe;
import com.ncedu.nc_edu.models.Tag;
import com.ncedu.nc_edu.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TagController {
    private TagService tagService;
    private TagAssembler tagAssembler;

    public TagController(@Autowired TagService tagService, @Autowired TagAssembler tagAssembler) {
        this.tagAssembler = tagAssembler;
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public CollectionModel<TagResource> getAll(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<Tag> tagEntities;

        if (name == null) {
            tagEntities = this.tagService.findAll();
        } else {
            tagEntities = this.tagService.findAllByNameContains(name);
        }

        if (tagEntities.size() == 0) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(TagResource.class);
            List<EmbeddedWrapper> list = Collections.singletonList(wrapper);
            return new CollectionModel(list);
        }

        List<TagResource> tags = tagEntities.stream()
                .map(tag -> tagAssembler.toModel(tag))
                .collect(Collectors.toList());

        return new CollectionModel<>(tags);
    }

    @GetMapping("/tags/{id}/recipes")
    public PagedModel<Recipe> getAllByTag(@PathVariable UUID id, Authentication auth) {
        return null;
    }
}
