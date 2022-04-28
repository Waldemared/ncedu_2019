package com.ncedu.nc_edu.exceptions;

public class EntityDoesNotExistsException extends RuntimeException {
    /**
     *
     * @param entityName name of entity, will be added to exception message
     */
    public EntityDoesNotExistsException(String entityName) {
        super(entityName + " does not exists");
    }
}
