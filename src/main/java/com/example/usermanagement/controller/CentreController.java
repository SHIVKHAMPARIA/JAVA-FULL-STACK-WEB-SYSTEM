package com.example.usermanagement.controller;

import com.example.usermanagement.model.CentreItem;
import com.example.usermanagement.repository.CentreItemRepository;
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
@RequestMapping("/EditCentres")
public class CentreController {


    @Autowired
    private CentreItemRepository centreItemRepository;


    @GetMapping
    public String getCentres(Model model) {
        List<CentreItem> centreItems = centreItemRepository.findAll();
        model.addAttribute("centreItems", centreItems);
        return "EditCentres"; // Assuming this matches the HTML file name
    }

    @PostMapping("/add")
    public String addCentreItem(@RequestParam("image") MultipartFile image,
                                @RequestParam("address") String address,
                                @RequestParam("location") String location,
                                @RequestParam("enabled") String enabled) throws IOException {
    	if (location.length() > 500) {
            throw new IllegalArgumentException("Location URL is too long");
        }
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

        // Create and save centre item
        CentreItem centreItem = new CentreItem();
        centreItem.setImageUrl("/images/" + image.getOriginalFilename()); // Example path
        centreItem.setAddress(address);
        centreItem.setLocation(location);
        centreItem.setEnabled("0x01".equals(enabled));
        centreItemRepository.save(centreItem);

        return "redirect:/EditCentres"; // Redirect to GET /EditCentres after adding
    }

    @PostMapping("/edit")
    public String editCentreItem(@RequestParam("id") Long id,
                                 @RequestParam(value = "enabledYes", required = false) boolean enabledYes,
                                 @RequestParam(value = "enabledNo", required = false) boolean enabledNo) {
        CentreItem centreItem = centreItemRepository.findById(id).orElseThrow();
        

        if (enabledYes && !enabledNo) {
            centreItem.setEnabled(true); // Enable
        } else if (!enabledYes && enabledNo) {
            centreItem.setEnabled(false); // Disable
        } else {
            // Handle invalid state or both checked (if needed)
        }

        centreItemRepository.save(centreItem);
        return "redirect:/EditCentres"; // Redirect to GET /EditCentres after editing
    }


    @PostMapping("/delete")
    public String deleteCentreItem(@RequestParam("id") Long id) {
        centreItemRepository.deleteById(id);
        return "redirect:/EditCentres"; // Redirect to GET /EditCentres after deleting
    }
}

