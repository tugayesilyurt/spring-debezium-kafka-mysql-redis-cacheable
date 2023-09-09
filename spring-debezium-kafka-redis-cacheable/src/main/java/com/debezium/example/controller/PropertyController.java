package com.debezium.example.controller;

import com.debezium.example.request.PropertyRequest;
import com.debezium.example.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping()
    public ResponseEntity<?> createProperty(@RequestBody PropertyRequest propertyRequest){
        propertyService.createProperty(propertyRequest.getPropertyKey(),propertyRequest.getPropertyValue());
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updateProperty(@RequestBody PropertyRequest propertyRequest){
        propertyService.updateProperty(propertyRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getProperty(@RequestParam(name = "key") String key) throws InterruptedException {
        return new ResponseEntity<String>(propertyService.getProperty(key),HttpStatus.OK);
    }


}
