package com.rootapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;
    Long fieldValue;

    // custom exception for resource not found
    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {

        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
