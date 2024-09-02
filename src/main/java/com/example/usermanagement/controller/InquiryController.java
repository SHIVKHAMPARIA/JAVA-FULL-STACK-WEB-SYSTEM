package com.example.usermanagement.controller;

import com.example.usermanagement.model.InquiryItem;
import com.example.usermanagement.repository.InquiryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InquiryController {

    @Autowired
    private InquiryItemRepository inquiryItemRepository;

    @GetMapping("/Inquiry")
    public String Inquiry() {
        return "Inquiry";
    }
    
    @GetMapping("/viewInquiries")
    public String viewInquiries(Model model,
                                @RequestParam(name = "city", required = false) String city,
                                @RequestParam(name = "state", required = false) String state,
                                @RequestParam(name = "fullName", required = false) String fullName,
                                @RequestParam(name = "id", required = false) Long id,
                                @RequestParam(name = "district", required = false) String district) {

        List<InquiryItem> inquiries = inquiryItemRepository.findInquiries(city, state, fullName, id, district);
        model.addAttribute("inquiries", inquiries);

        return "Inquiry";
    }
}
