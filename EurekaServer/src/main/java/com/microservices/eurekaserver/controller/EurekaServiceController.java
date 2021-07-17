package com.microservices.eurekaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/service")
    public int getServiceList(){
        return discoveryClient.getInstances("UserServiceApplication").get(0).getPort();
    }
}
