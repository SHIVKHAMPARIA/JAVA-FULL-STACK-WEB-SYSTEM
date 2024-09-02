package com.example.usermanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.usermanagement.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/UserDashBoard")
    public String userDashboard(@RequestParam Long userId, Model model) {
        List<Map<String, Object>> dates = userService.getDates(userId);
        List<Map<String, Object>> checklist = userService.getChecklist(userId);
        List<Map<String, Object>> contacts = userService.getContacts(userId);

        model.addAttribute("dates", dates);
        model.addAttribute("checklist", checklist);
        model.addAttribute("contacts", contacts);
        model.addAttribute("userId", userId);  // Replace with actual username

        return "userDashBoard";  // This should match the name of your HTML template without the .html extension
    }

    @GetMapping("/viewDates")
    @ResponseBody
    public List<Map<String, Object>> viewDates(@RequestParam Long userId) {
        return userService.getDates(userId);
    }

    @GetMapping("/viewChecklist")
    @ResponseBody
    public List<Map<String, Object>> viewChecklist(@RequestParam Long userId) {
        return userService.getChecklist(userId);
    }

    @GetMapping("/viewContacts")
    @ResponseBody
    public List<Map<String, Object>> viewContacts(@RequestParam Long userId) {
        return userService.getContacts(userId);
    }
    @PostMapping("/saveChecklistItem")
    public ResponseEntity<Map<String, Object>> saveChecklistItem(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String checklistText = request.get("checklistText").toString();
            String enabled = request.get("enabled").toString();

            userService.saveChecklistItem(userId, checklistText, enabled.equals("Yes") ? enabled : null);  // Handle "Yes" and "No" accordingly

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Checklist item updated successfully");
            return ResponseEntity.ok(response);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Error saving checklist item");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

