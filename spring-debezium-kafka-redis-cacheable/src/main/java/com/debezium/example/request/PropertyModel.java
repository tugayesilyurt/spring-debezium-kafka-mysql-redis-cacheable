package com.debezium.example.request;

import lombok.Data;

@Data
public class PropertyModel {

    private Long id;
    private Long created_date;
    private String property_key;
    private String property_value;

}
