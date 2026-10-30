package com.example.fnaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MonitorController {

    @GetMapping("/")
    public String mainSecurityRoom() {
        return "index";
    }

    @GetMapping("/portfolio/{id}")
    public String viewPortfolio(@PathVariable String id) {
        return switch (id.toLowerCase()) {
            case "josh" -> "portfolios/josh";
            case "ken" -> "portfolios/ken";
            case "klarc" -> "portfolios/klarc";
            case "rey" -> "portfolios/rey";
            default -> "redirect:/";
        };
    }
}