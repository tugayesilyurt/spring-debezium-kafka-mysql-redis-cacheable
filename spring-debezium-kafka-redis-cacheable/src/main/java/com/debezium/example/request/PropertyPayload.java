package com.debezium.example.request;

import lombok.Data;

@Data
public class PropertyPayload {

    private String op;
    private PropertyModel before;
    private PropertyModel after;
}
