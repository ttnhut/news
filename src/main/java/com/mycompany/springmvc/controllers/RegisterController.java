/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.controllers;

import com.mycompany.springmvc.pojos.User;
import com.mycompany.springmvc.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Asus
 */
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
            
    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user1",new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute(value = "user1") @Valid User user , BindingResult result,Model model){
        if(!result.hasErrors()){
            if(!user.getPassword().equals(user.getConfirmPassword())){
                model.addAttribute("msg","Mật khẩu không trùng khớp");
                
            }
            else{
                userService.addUser(user);
                model.addAttribute("msg","Đăng ký thành công");
                
            }
        }
        return "register";
    }
}
