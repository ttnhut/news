/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.controllers;

import com.mycompany.springmvc.service.CategoryService;
import com.mycompany.springmvc.service.PostService;
import com.mycompany.springmvc.service.UserService;
import java.util.Arrays;
import javax.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Asus
 */
@Controller
@ControllerAdvice
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @ModelAttribute
    public void attachingCategories(Model model,@AuthenticationPrincipal UserDetails currentUser){
        if(currentUser!=null)
            model.addAttribute("user",userService.users(currentUser.getUsername()).get(0));
        model.addAttribute("categories",categoryService.categories());
    }
    
    
    @RequestMapping("/")
    public String homePath(Model model){
    
        model.addAttribute("posts",postService.posts(""));
        
        return "index";
    }
}
