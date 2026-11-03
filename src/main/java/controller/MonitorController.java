package com.example.fnaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MonitorController {

    // Main Menu (this the new starting one)
    @GetMapping({"/menu"})
    public String showMenu() { return "menu"; }

    // Security Cam
    @GetMapping("/index")
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

    // Jumpscare boo
    @GetMapping("/jumpscare/{cam}")
    public String triggerJumpscare(@PathVariable String cam, Model model) {
        String camera = cam.toUpperCase();

        switch (camera) {
            case "CAM3" -> {
                model.addAttribute("title", "CAM 03 - GOLDEN FREDDY");
                model.addAttribute("message", "IT'S ME");
                model.addAttribute("image", "/images/frezzy.jpg");
                model.addAttribute("audio", "/audio/frezzy.mp3");
            }
            case "CAM4A" -> {
                model.addAttribute("title", "CAM 04A - KITCHEN");
                model.addAttribute("message", "hehe");
                model.addAttribute("image", "/images/bluelobster.jpg");
                model.addAttribute("audio", "/audio/bluelobster.mp3");
            }
            case "CAM5" -> {
                model.addAttribute("title", "CAM 05 - BACKSTAGE");
                model.addAttribute("message", "sorry po hwahahaha");
                model.addAttribute("videoUrl", "https://www.youtube.com/embed/dQw4w9WgXcQ?autoplay=1");
            }
            case "CAM6" -> {
                model.addAttribute("title", "CAM 06 - GENIUS");
                model.addAttribute("message", "Like,\n" +
                        "Baby you bugging\n" +
                        "Bu-bu-bugging\n" +
                        "Bu-bu-bugging");
                model.addAttribute("image", "/images/bugging.png");
                model.addAttribute("audio", "/audio/bugging.mp3");
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