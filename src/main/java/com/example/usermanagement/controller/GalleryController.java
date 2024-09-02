package com.example.usermanagement.controller;

import com.example.usermanagement.model.GalleryItem;
import com.example.usermanagement.repository.GalleryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/EditGallery")
public class GalleryController {

    @Autowired
    private GalleryItemRepository galleryItemRepository;

    @GetMapping
    public String getGallery(Model model) {
        List<GalleryItem> galleryItems = galleryItemRepository.findAll();
        model.addAttribute("galleryItems", galleryItems);
        return "EditGallery"; // Assuming this matches the HTML file name
    }

    @PostMapping("/add")
    public String addGalleryItem(@RequestParam("image") MultipartFile image,
                                 @RequestParam("text") String text,
                                 @RequestParam("enabled") String enabled,
                                 RedirectAttributes redirectAttributes) throws IOException {
        // Directory to save images
        String uploadDir = "src/main/resources/static/images/";

        // Create directory if it does not exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Check if the file size is too big (for example, more than 5MB)
        if (image.getSize() > 5 * 1024 * 1024) {
            redirectAttributes.addFlashAttribute("error", "File size exceeds the limit of 5MB.");
            return "redirect:/EditGallery"; // Redirect back to the gallery page
        }

        // Save image to the file system
        byte[] bytes = image.getBytes();
        Path imagePath = uploadPath.resolve(image.getOriginalFilename());
        Files.write(imagePath, bytes);

        // Create and save gallery item
        GalleryItem galleryItem = new GalleryItem();
        galleryItem.setImageUrl("/images/" + image.getOriginalFilename()); // Example path
        galleryItem.setText(text);
        galleryItem.setEnabled("0x01".equals(enabled));
        galleryItemRepository.save(galleryItem);

        return "redirect:/EditGallery"; // Redirect to GET /EditGallery after adding
    }

    @PostMapping("/edit")
    public String editGalleryItem(@RequestParam("id") Long id,
                                  @RequestParam(value = "enabledYes", required = false) boolean enabledYes,
                                  @RequestParam(value = "enabledNo", required = false) boolean enabledNo) {
        GalleryItem galleryItem = galleryItemRepository.findById(id).orElseThrow();

        if (enabledYes && !enabledNo) {
            galleryItem.setEnabled(true); // Enable
        } else if (!enabledYes && enabledNo) {
            galleryItem.setEnabled(false); // Disable
        } else {
            // Handle invalid state or both checked (if needed)
        }

        galleryItemRepository.save(galleryItem);
        return "redirect:/EditGallery"; // Redirect to GET /EditGallery after editing
    }

    @PostMapping("/delete")
    public String deleteGalleryItem(@RequestParam("id") Long id) {
        galleryItemRepository.deleteById(id);
        return "redirect:/EditGallery"; // Redirect to GET /EditGallery after deleting
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Error occurred while processing the file: " + ex.getMessage());
        return "redirect:/EditGallery"; // Redirect back to the gallery page
    }
}

