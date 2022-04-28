package com.ncedu.nc_edu.exceptions;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {
    private final String entity;
    private final String field;

    /**
     * Generates exception with format: *Entity* with given *field* is already exists.
     * @param entity string value of entity
     * @param field string value of field
     */
    public AlreadyExistsException(String entity, String field) {
        super(entity + " with given " + field + " is already exists");
        this.entity = entity;
        this.field = field;
    }
}