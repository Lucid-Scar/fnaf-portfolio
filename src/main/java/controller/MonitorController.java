package com.example.fnaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MonitorController {

    @GetMapping("/")
    public String mainSecurityRoom() {
        return "index";
    }

    // Portfolio Routing
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

    // Jumpscare & Funny Scenario Server-Side Router
    @GetMapping("/jumpscare/{cam}")
    public String triggerJumpscare(@PathVariable String cam, Model model) {
        String camera = cam.toUpperCase();

        switch (camera) {
            case "CAM3" -> {
                model.addAttribute("title", "CAM 03 - GOLDEN FREDDY");
                model.addAttribute("message", "IT'S ME");
                model.addAttribute("image", "/images/golden-freddy.png");
                model.addAttribute("audio", "/audio/scream.mp3");
            }
            case "CAM4A" -> {
                model.addAttribute("title", "CAM 04A - KITCHEN");
                model.addAttribute("message", "Chica is making pizza at 3 AM!");
                model.addAttribute("image", "/images/chica-pizza.png");
                model.addAttribute("audio", "/audio/kitchen-noise.mp3");
            }
            case "CAM5" -> {
                model.addAttribute("title", "CAM 05 - BACKSTAGE");
                model.addAttribute("message", "Never Gonna Give you up" +
                        "Never Gonna let you down" +
                        "Never gonna run around" +
                        "and hurt youuuhhh.");
                model.addAttribute("image", "/images/endo.png");
                model.addAttribute("audio", "/audio/stare.mp3");
            }
            case "CAM6" -> {
                model.addAttribute("title", "CAM 06 - GENIUS");
                model.addAttribute("message", "Like,\n" +
                        "Baby you bugging\n" +
                        "Bu-bu-bugging\n" +
                        "Bu-bu-bugging");
                model.addAttribute("image", "/images/bugging.png");
                model.addAttribute("audio", "/audio/stare.mp3");
            }
            default -> {
                model.addAttribute("title", "CAM ?? - SIGNAL LOST");
                model.addAttribute("message", "Nothing but static...");
                model.addAttribute("image", "/images/static.gif");
                model.addAttribute("audio", "/audio/static.mp3");
            }
        }

        model.addAttribute("cameraName", camera);
        return "jumpscare"; // Renders templates/jumpscare.html
    }
}