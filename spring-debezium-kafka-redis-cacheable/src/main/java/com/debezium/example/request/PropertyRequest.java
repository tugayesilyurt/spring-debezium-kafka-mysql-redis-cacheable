package com.debezium.example.request;

import lombok.Data;

@Data
public class PropertyRequest {

    private String propertyKey;
    private String propertyValue;

}
