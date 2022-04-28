package com.ncedu.nc_edu.dto.assemblers;

import com.ncedu.nc_edu.dto.resources.TagResource;
import com.ncedu.nc_edu.models.Tag;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TagAssembler extends RepresentationModelAssemblerSupport<Tag, TagResource> {
    public TagAssembler() {
        super(Tag.class, TagResource.class);
    }

    @Override
    public TagResource toModel(Tag entity) {
        TagResource resource = new TagResource();

        resource.setName(entity.getName());

        return resource;
    }
}
