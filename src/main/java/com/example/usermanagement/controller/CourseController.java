package com.example.usermanagement.controller;

import com.example.usermanagement.model.CourseItem;
import com.example.usermanagement.repository.CourseItemRepository;
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
@RequestMapping("/EditCourses")
public class CourseController {

    @Autowired
    private CourseItemRepository courseItemRepository;

    @GetMapping
    public String getCourses(Model model) {
        List<CourseItem> courseItems = courseItemRepository.findAll();
        model.addAttribute("courseItems", courseItems);
        return "EditCourses"; // Assuming this matches the HTML file name
    }

    @PostMapping("/add")
    public String addCourseItem(@RequestParam("image") MultipartFile image,
                                @RequestParam("text") String text,
                               
                                @RequestParam("enabled") String enabled) throws IOException {
    	
        // Directory to save images
        String uploadDir = "path/to/your/upload/directory/";

        // Create directory if it does not exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save image to the file system
        byte[] bytes = image.getBytes();
        Path imagePath = uploadPath.resolve(image.getOriginalFilename());
        Files.write(imagePath, bytes);

        // Create and save course item
        CourseItem courseItem = new CourseItem();
        courseItem.setImageUrl("/images/" + image.getOriginalFilename()); // Example path
        courseItem.setText(text);

        courseItem.setEnabled("0x01".equals(enabled));
        courseItemRepository.save(courseItem);

        return "redirect:/EditCourses"; // Redirect to GET /EditCourses after adding
    }

    @PostMapping("/edit")
    public String editCourseItem(@RequestParam("id") Long id,
                                 @RequestParam(value = "enabledYes", required = false) boolean enabledYes,
                                 @RequestParam(value = "enabledNo", required = false) boolean enabledNo) {
        CourseItem courseItem = courseItemRepository.findById(id).orElseThrow();
        

        if (enabledYes && !enabledNo) {
            courseItem.setEnabled(true); // Enable
        } else if (!enabledYes && enabledNo) {
            courseItem.setEnabled(false); // Disable
        } else {
            // Handle invalid state or both checked (if needed)
        }

        courseItemRepository.save(courseItem);
        return "redirect:/EditCourses"; // Redirect to GET /EditCourses after editing
    }

    @PostMapping("/delete")
    public String deleteCourseItem(@RequestParam("id") Long id) {
        courseItemRepository.deleteById(id);
        return "redirect:/EditCourses"; // Redirect to GET /EditCourses after deleting
    }
}

