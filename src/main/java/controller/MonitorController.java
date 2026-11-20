package com.example.fnaf.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MonitorController {

    // Main Menu (this the new starting one)
    @GetMapping({"/", "/menu"})
    public String showMenu(HttpServletResponse response) {
        setMonitorOpen(response, false);
        return "menu";
    }

    // Security Cam
    @GetMapping("/index")
    public String mainSecurityRoom(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        setMonitorOpen(response, true);
        return "index";
    }

    // Changing this cookie on camera entry also evicts the no-store monitor
    // from Chromium's back/forward cache, so Back starts a fresh shift.
    private void setMonitorOpen(HttpServletResponse response, boolean open) {
        Cookie cookie = new Cookie("monitorOpen", "1");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        if (!open) {
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
    }

    @GetMapping("/six-am")
    public String endNight(HttpServletResponse response) {
        setMonitorOpen(response, false);
        return "six-am";
    }

    // Portfolio Routing
    @GetMapping("/portfolio/{id}")
    public String viewPortfolio(@PathVariable String id, HttpServletResponse response) {
        setMonitorOpen(response, false);
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
    public String triggerJumpscare(@PathVariable String cam, Model model, HttpServletResponse response) {
        setMonitorOpen(response, false);
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
            case "CAM7" -> {
                return "cam7";
            }
            case "CAM8" -> {
                model.addAttribute("title", "CAM 08 - STORAGE");
                model.addAttribute("message", "That was definitely not an animatronic.");
                model.addAttribute("image", "/images/spino.png");
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
