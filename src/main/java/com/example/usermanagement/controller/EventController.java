package com.example.usermanagement.controller;

import com.example.usermanagement.model.EventItem;
import com.example.usermanagement.repository.EventItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/EditEvents")
public class EventController {

    @Autowired
    private EventItemRepository eventItemRepository;

    @GetMapping
    public String getEvents(Model model) {
        List<EventItem> eventItems = eventItemRepository.findAll();
        model.addAttribute("eventItems", eventItems);
        return "EditEvents"; // Assuming this matches the HTML file name
    }

    @PostMapping("/add")
    public String addEventItem(@RequestParam("image") MultipartFile image,
                               @RequestParam("text") String text,
                               @RequestParam("date") String date,
                               @RequestParam("location") String location,
                               @RequestParam("enabled") String enabled) throws IOException {
        // Directory to save images
        String uploadDir = "src/main/resources/static/images/";

        // Create directory if it does not exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save image to the file system
        byte[] bytes = image.getBytes();
        Path imagePath = uploadPath.resolve(image.getOriginalFilename());
        Files.write(imagePath, bytes);

        // Create and save event item
        EventItem eventItem = new EventItem();
        eventItem.setImageUrl("/images/" + image.getOriginalFilename()); // Example path
        eventItem.setText(text);
        eventItem.setDate(date);
        eventItem.setLocation(location);
        eventItem.setEnabled("0x01".equals(enabled));
        eventItemRepository.save(eventItem);

        return "redirect:/EditEvents"; // Redirect to GET /EditEvents after adding
    }

    @PostMapping("/edit")
    public String editEventItem(@RequestParam("id") Long id,
                                @RequestParam(value = "enabledYes", required = false) boolean enabledYes,
                                @RequestParam(value = "enabledNo", required = false) boolean enabledNo) {
        EventItem eventItem = eventItemRepository.findById(id).orElseThrow();

        if (enabledYes && !enabledNo) {
            eventItem.setEnabled(true); // Enable
        } else if (!enabledYes && enabledNo) {
            eventItem.setEnabled(false); // Disable
        } else {
            // Handle invalid state or both checked (if needed)
        }

        eventItemRepository.save(eventItem);
        return "redirect:/EditEvents"; // Redirect to GET /EditEvents after editing
    }


    @PostMapping("/delete")
    public String deleteEventItem(@RequestParam("id") Long id) {
        eventItemRepository.deleteById(id);
        return "redirect:/EditEvents"; // Redirect to GET /EditEvents after deleting
    }
}


