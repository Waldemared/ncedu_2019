package com.ncedu.nc_edu.security;

import com.ncedu.nc_edu.dto.resources.OwnableResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.*;

@RestControllerAdvice
public class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {
    private SecurityAccessResolver securityAccessResolver;

    public SecurityJsonViewControllerAdvice(@Autowired SecurityAccessResolver securityAccessResolver) {
        this.securityAccessResolver = securityAccessResolver;
    }

    @Override
    protected void beforeBodyWriteInternal(
            MappingJacksonValue bodyContainer,
            MediaType contentType,
            MethodParameter returnType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        if (bodyContainer.getValue() instanceof RepresentationModel
                && bodyContainer.getSerializationView() == null
                && securityAccessResolver.getUser() != null) {

            bodyContainer.setSerializationView(View.getView(securityAccessResolver.getHeadAuthority()));
            if (bodyContainer.getValue() instanceof CollectionModel) {
                    return;
            }
            if (bodyContainer.getValue() instanceof OwnableResource
                    && ((OwnableResource)bodyContainer.getValue()).getOwnerId().equals(securityAccessResolver.getUser().getId())) {
                bodyContainer.setSerializationView(View.Owner.class);
            }
        }
    }
}
