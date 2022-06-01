/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.controllers;



import com.mycompany.springmvc.pojos.Post;
import com.mycompany.springmvc.pojos.User;
import com.mycompany.springmvc.service.PostService;
import com.mycompany.springmvc.service.UserService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 *
 * @author Asus
 */
@Controller
public class PostController {
   @Autowired
   private PostService postService;
   @Autowired
   private UserService userService;
    @GetMapping("/admin/addpost")
    public String addPostView(Model model){
        model.addAttribute("post",new Post());
        return "addpost";
    }
    
    @PostMapping("/admin/addpost")
    public String addPost(@ModelAttribute(value = "post") @Valid Post post,
            BindingResult result , Model model, @AuthenticationPrincipal UserDetails currentUser){
       
       if(!result.hasErrors()){
           if(currentUser!=null){         
                post.setUser(userService.users(currentUser.getUsername()).get(0));
           }
           
           if(postService.addPost(post) == true) 
               model.addAttribute("alert","Thành công");
           else 
               model.addAttribute("alert","Lỗi không xác định");
           
       }
        
        return "addpost";
    }
    
    @GetMapping("/category/{id}")
    public String postByCateegoryPage(Model model , @PathVariable(value = "id") int id){
        model.addAttribute("posts",postService.getPostByCategory(id));
        
        return "index";
    }
    
    @GetMapping("/post/{id}")
    public String post(Model model , @PathVariable(value = "id") int id ){
        model.addAttribute("post",postService.getPostById(id));
        return "post";
    }
    
    @GetMapping("/search")
    public String post(Model model , @RequestParam(value = "q") String query){
        model.addAttribute("posts",postService.posts(query));
        return "index";
    }
}
