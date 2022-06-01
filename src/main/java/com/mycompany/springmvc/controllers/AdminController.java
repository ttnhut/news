/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.controllers;

import com.mycompany.springmvc.pojos.Post;
import com.mycompany.springmvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Asus
 */
@Controller
public class AdminController {
    @Autowired
    private PostService postService;
    @GetMapping("/admin/manager")
    public String pageManager(){
        return "manager";
    }
    
    @GetMapping("/admin/postmanager")
    public String postManager(Model model){
        model.addAttribute("posts" , postService.posts(""));
        return "postmanager";
    }
    
    @PostMapping("/admin/editpost/{id}")
    public String editPost(Model model, @PathVariable(value = "id") int id , @ModelAttribute(value = "post") Post post){
        if(postService.updatePost(id , post)) {
            return "redirect:/admin/postmanager";
        }
        else model.addAttribute("alert","Thất bại");
        
        return "editpost";
    }
    
    @GetMapping("/admin/editpost/{id}")
    public String editPostPage(Model model , @PathVariable(value = "id") int id){
        
        model.addAttribute("post",postService.getPostById(id));
        
        return "editpost";
       
    }
    
    @GetMapping("/admin/deletepost/{id}")
    public String deletePost(@PathVariable(value = "id") int id , Model model){
        if(postService.deletePostById(id)){
            model.addAttribute("alert","Thành công");
        }
        else
            model.addAttribute("alert","Thất bại");
        
        return "forward:/admin/postmanager";
        
    }
    
    
}
