package com.example.usermanagement.controller;

import com.example.usermanagement.model.CentreItem;
import com.example.usermanagement.model.CourseItem;
import com.example.usermanagement.model.EventItem;
import com.example.usermanagement.model.GalleryItem;
import com.example.usermanagement.repository.CentreItemRepository;
import com.example.usermanagement.repository.CourseItemRepository;
import com.example.usermanagement.repository.EventItemRepository;
import com.example.usermanagement.repository.GalleryItemRepository;
import com.example.usermanagement.repository.TestimonialItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UltimateController {

    @Autowired
    private CourseItemRepository courseItemRepository;
    @Autowired
    private GalleryItemRepository galleryItemRepository;

    @Autowired
    private EventItemRepository eventItemRepository;

    @Autowired
    private TestimonialItemRepository testimonialItemRepository;
    
    @Autowired
    private CentreItemRepository centreItemRepository;


    @GetMapping("/Courses")
    public String getCourses(Model model) {
        List<CourseItem> courseItems = courseItemRepository.findByEnabled(true);
        model.addAttribute("courseItems", courseItems);
        return "Courses";
    }

    public CourseItemRepository getCourseItemRepository() {
        return courseItemRepository;
    }

    public void setCourseItemRepository(CourseItemRepository courseItemRepository) {
        this.courseItemRepository = courseItemRepository;
    }
    
    @GetMapping("/Contact")
    public String contact() {
        return "contact";
    }
    
    @GetMapping("/Events")
    public String getCourses2(Model model) {
        List<EventItem> eventItems = eventItemRepository.findByEnabled(true);
        model.addAttribute("eventItems", eventItems);
        return "Events";
    }
    @GetMapping("/Gallery")
    public String getCourses1(Model model) {
        List<GalleryItem> galleryItems = galleryItemRepository.findAll();
        model.addAttribute("galleryItems", galleryItems);
        return "Gallery";
    }
    
    @GetMapping("/OurCentres")
    public String getCentres(Model model) {
        List<CentreItem> centreItems = centreItemRepository.findByEnabled(true);
        model.addAttribute("centreItems", centreItems);
        return "OurCentres";
    }

    

	public EventItemRepository getEventItemRepository() {
		return eventItemRepository;
	}

	public void setEventItemRepository(EventItemRepository eventItemRepository) {
		this.eventItemRepository = eventItemRepository;
	}

	public GalleryItemRepository getGalleryItemRepository() {
		return galleryItemRepository;
	}

	public void setGalleryItemRepository(GalleryItemRepository galleryItemRepository) {
		this.galleryItemRepository = galleryItemRepository;
	}

	public TestimonialItemRepository getTestimonialItemRepository() {
		return testimonialItemRepository;
	}

	public void setTestimonialItemRepository(TestimonialItemRepository testimonialItemRepository) {
		this.testimonialItemRepository = testimonialItemRepository;
	}

}
