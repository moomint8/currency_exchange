package moomint.toy.currency_exchange.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health-check")
public class HealthCheck {

    @GetMapping
    public String healthCheck() { return "Server is up and running"; }

    @GetMapping("/is-loggedin")
    public String isLoggedIn() { return "You are logged in"; }
}