package com.example.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final Service2ClientService service;

    public DataController(Service2ClientService service) {
        this.service = service;
    }

    @GetMapping("/proxy")
    public String getData(){
       return service.fetchData();
   }
}
