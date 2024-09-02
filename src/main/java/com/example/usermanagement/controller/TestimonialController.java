package com.example.usermanagement.controller;

import com.example.usermanagement.model.TestimonialItem;
import com.example.usermanagement.repository.TestimonialItemRepository;
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
@RequestMapping("/EditTestimonial")
public class TestimonialController {

    @Autowired
    private TestimonialItemRepository testimonialItemRepository;

    @GetMapping
    public String getTestimonials(Model model) {
        List<TestimonialItem> testimonialItems = testimonialItemRepository.findAll();
        model.addAttribute("testimonialItems", testimonialItems);
        return "EditTestimonial"; // Assuming this matches the HTML file name
    }

    @PostMapping("/add")
    public String addTestimonialItem(@RequestParam("image") MultipartFile image,
                                     @RequestParam("text") String text,
                                     @RequestParam("name") String name,
                                     @RequestParam("domain") String domain) throws IOException {
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

        // Create and save testimonial item
        TestimonialItem testimonialItem = new TestimonialItem();
        testimonialItem.setImageUrl("/images/" + image.getOriginalFilename()); // Example path
        testimonialItem.setText(text);
        testimonialItem.setName(name);
        testimonialItem.setDomain(domain);
        testimonialItemRepository.save(testimonialItem);

        return "redirect:/EditTestimonial"; // Redirect to GET /EditTestimonial after adding
    }

    @PostMapping("/delete")
    public String deleteTestimonialItem(@RequestParam("id") Long id) {
        testimonialItemRepository.deleteById(id);
        return "redirect:/EditTestimonial"; // Redirect to GET /EditTestimonial after deleting
    }
}
