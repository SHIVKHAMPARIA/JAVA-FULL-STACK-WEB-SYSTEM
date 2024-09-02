package com.example.usermanagement.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.usermanagement.model.CourseItem;
import com.example.usermanagement.model.EventItem;
//import com.example.usermanagement.model.EventItem;
import com.example.usermanagement.model.GalleryItem;
import com.example.usermanagement.model.InquiryItem;
import com.example.usermanagement.model.TestimonialItem;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.CourseItemRepository;
import com.example.usermanagement.repository.EventItemRepository;
//import com.example.usermanagement.repository.EventItemRepository;
import com.example.usermanagement.repository.GalleryItemRepository;
import com.example.usermanagement.repository.InquiryItemRepository;
import com.example.usermanagement.repository.TestimonialItemRepository;
import com.example.usermanagement.repository.UserItemRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.repository.Userrepooo;
//import com.example.usermanagement.repository.userrepooo;
import com.example.usermanagement.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Userrepooo userrepooo;
    
    @Autowired
    private GalleryItemRepository galleryItemRepository;

    @Autowired
    private CourseItemRepository courseItemRepository;

    @Autowired
    private EventItemRepository eventItemRepository;

    @Autowired
    private TestimonialItemRepository testimonialItemRepository;
    
    @Autowired
	private InquiryItemRepository inquiryItemRepository;
    
    
    @Autowired
	private UserItemRepository userItemRepository;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/")
    public String index(Model model) {
        List<GalleryItem> galleryItems = galleryItemRepository.findByEnabled(true); // Fetch only enabled gallery items
        List<CourseItem> courseItems = courseItemRepository.findByEnabled(true);
        List<EventItem> eventItems = eventItemRepository.findByEnabled(true);
        List<TestimonialItem> testimonialItems = testimonialItemRepository.findAll();
        model.addAttribute("testimonialItems", testimonialItems);
        model.addAttribute("galleryItems", galleryItems);
        model.addAttribute("eventItems", eventItems);
        model.addAttribute("courseItems", courseItems);
        return "index";
    }
    @GetMapping("/user")
    public String user(Model model) {
        List<GalleryItem> galleryItems = galleryItemRepository.findByEnabled(true); // Fetch only enabled gallery items
        List<CourseItem> courseItems = courseItemRepository.findByEnabled(true);
        List<EventItem> eventItems = eventItemRepository.findByEnabled(true);
        List<TestimonialItem> testimonialItems = testimonialItemRepository.findAll();
        model.addAttribute("testimonialItems", testimonialItems);
        model.addAttribute("galleryItems", galleryItems);
        model.addAttribute("eventItems", eventItems);
        model.addAttribute("courseItems", courseItems);
        return "user";
    }
    
    @GetMapping("/UserProfile")
    public String UserProfile() {
        return "UserProfile";
    }
    @GetMapping("/ViewContacts")
    public String ViewContacts() {
        return "ViewContacts";
    }
    @GetMapping("/ViewDate")
    public String ViewDate() {
        return "ViewDate";
    }
    @GetMapping("/ViewChecklist")
    public String ViewChecklist() {
        return "ViewChecklist";
    }
    
    

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    

    @GetMapping("/logo")
    public String logo() {
        return "logo";
    }
    @GetMapping("/yoyoyo")
    public String yoyoyo() {
        return "yoyoyo";
    }

    @GetMapping("/bussinessman")
    public String bussinessman() {
        return "bussinessman";
    }

    
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/AdminLogin")
    public String AdminLogin() {
        return "AdminLogin";
    }

    @GetMapping("/UserRegistration")
    public String UserRegistration() {
        return "UserRegistration";
    }

    @GetMapping("/userlogin")
    public String userlogin() {
        return "userlogin";
    }

    @GetMapping("/AdminOptions")
    public String AdminOptions() {
        return "AdminOptions";
    }

    @PostMapping("/userlogin")
    public String handleUserLogin(@RequestParam("username") String usernameOrEmail,
                                  @RequestParam("password") String password,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("userId", user.getId());
            return "userDashboard";  // The name of your HTML file without the extension
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username/email or password. Please try again.");
            return "redirect:/userlogin";
        }
    }



    @PostMapping("/AdminLogin")
    public String adminLogin(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        if ("admin@sanitytech.in".equals(email) && "12345".equals(password)) {
            return "AdminOptions";
        } else {
            redirectAttributes.addFlashAttribute("error", "Incorrect email or password. Please try again.");
            return "redirect:/AdminLogin";
        }
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("gender") String gender,
                                 @RequestParam("state") String state,
                                 @RequestParam("city") String city,
                                 @RequestParam("pincode") String pincode,
                                 @RequestParam("address") String address,
                                 RedirectAttributes redirectAttributes) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);
        user.setState(state);
        user.setCity(city);
        user.setPincode(pincode);
        user.setAddress(address);

        userRepository.save(user);
        userService.registerUser(user);

            

        redirectAttributes.addFlashAttribute("message", "Registration successful!");
        return "redirect:/userlogin"; // Redirect to your login page after successful registration
    }
    @GetMapping("/manager")
    public String manageUser(@RequestParam("userId") Long userId, Model model) {
        // Fetch user details from the database
        Optional<User> userOpt = userrepooo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("userId", user.getId());
            model.addAttribute("username", user.getUsername());
        }
        return "manager"; // Name of the Thymeleaf template to render
    }
    
    @GetMapping("/viewUsers")
    public String viewUsers(Model model,
                            @RequestParam(name = "city", required = false) String city,
                            @RequestParam(name = "state", required = false) String state,
                            @RequestParam(name = "username", required = false) String username,
                            @RequestParam(name = "id", required = false) Long id,
                            @RequestParam(name = "gender", required = false) String gender) {

        List<User> users = userItemRepository.findUsers(city, state, username, id, gender);
        model.addAttribute("users", users);

        return "admin";
    }
    

	public TestimonialItemRepository getTestimonialItemRepository() {
		return testimonialItemRepository;
	}
	public void setTestimonialItemRepository(TestimonialItemRepository testimonialItemRepository) {
		this.testimonialItemRepository = testimonialItemRepository;
	}
	
	
	@PostMapping("/saveInquiry")
	public String saveInquiry(@RequestParam("fullName") String fullName,
	                          @RequestParam("phoneNumber") String phoneNumber,
	                          @RequestParam("email") String email,
	                          @RequestParam("state") String state,
	                          @RequestParam("district") String district,
	                          @RequestParam("city") String city,
	                          @RequestParam("pincode") String pincode,
	                          @RequestParam("message") String message,
	                          RedirectAttributes redirectAttributes) {

	    InquiryItem inquiryItem = new InquiryItem();
	    inquiryItem.setFullName(fullName);
	    inquiryItem.setPhoneNumber(phoneNumber);
	    inquiryItem.setEmail(email);
	    inquiryItem.setState(state);
	    inquiryItem.setDistrict(district);
	    inquiryItem.setCity(city);
	    inquiryItem.setPincode(pincode);
	    inquiryItem.setMessage(message);

	    inquiryItemRepository.save(inquiryItem);

	    //redirectAttributes.addFlashAttribute("message", "Inquiry submitted successfully!");
	    return "redirect:/"; // Redirect to the homepage or any other appropriate page
	}




	public UserItemRepository getUserItemRepository() {
		return userItemRepository;
	}




	public void setUserItemRepository(UserItemRepository userItemRepository) {
		this.userItemRepository = userItemRepository;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

    @PostMapping("/saveDates")
    public String saveDates(@RequestParam("userId") Long userId,
                            @RequestParam("dates") String dates,
                            @RequestParam("text") String text,
                            RedirectAttributes redirectAttributes) {
        userService.saveDates(userId, dates, text);
        redirectAttributes.addFlashAttribute("message", "Dates saved successfully!");
        return "redirect:/manager?userId=" + userId;
    }

    @PostMapping("/saveChecklist")
    public String saveChecklist(@RequestParam("userId") Long userId,
                                @RequestParam("checklist") String checklist,
                                RedirectAttributes redirectAttributes) {
        userService.saveChecklist(userId, checklist);
        redirectAttributes.addFlashAttribute("message", "Checklist saved successfully!");
        return "redirect:/manager?userId=" + userId;
    }

    @PostMapping("/saveContacts")
    public String saveContacts(@RequestParam("userId") Long userId,
                               @RequestParam("contactPortal") String contactPortal,
                               @RequestParam("contactPerson") String contactPerson,
                               RedirectAttributes redirectAttributes) {
        userService.saveContacts(userId, contactPortal, contactPerson);
        redirectAttributes.addFlashAttribute("message", "Contacts saved successfully!");
        return "redirect:/manager?userId=" + userId;
    }

    @PostMapping("/uploadResources")
    public String uploadResources(@RequestParam("userId") Long userId,
                                  @RequestParam("resourceFile") MultipartFile resourceFile,
                                  RedirectAttributes redirectAttributes) {
        userService.saveResources(userId, resourceFile);
        redirectAttributes.addFlashAttribute("message", "Resources uploaded successfully!");
        return "redirect:/manager?userId=" + userId;
    }

    @GetMapping("/viewAllRows")
    public String viewAllRows(@RequestParam("userId") Long userId, Model model) {
        List<Map<String, Object>> rows = userService.getAllRows(userId);
        model.addAttribute("rows", rows);
        model.addAttribute("userId", userId);
        return "viewRows";
    }
    
	

}
