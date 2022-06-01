/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.service.impl;

import com.mycompany.springmvc.pojos.Category;
import com.mycompany.springmvc.pojos.Post;
import com.mycompany.springmvc.repository.PostRepository;
import com.mycompany.springmvc.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean addPost(Post post) {
        return postRepository.addPost(post);
    }

    @Override
    public List<Post> posts(String keyword) {
        return postRepository.posts(keyword);
    }

    @Override
    public List<Post> getPostByCategory(int idCategory) {
        
        return postRepository.getPostByCategory(idCategory);
    }

    @Override
    public Post getPostById(int i) {
        return postRepository.getPostById(i);
     }

    @Override
    public boolean updatePost(int i , Post p) {
        Post post = postRepository.getPostById(i);
        post.setTitle(p.getTitle());
        post.setDescription(p.getDescription());
        post.setCategory(p.getCategory());
        if(p.getFile().getSize()!=0)
            post.setFile(p.getFile());
        return postRepository.addPost(post);
     }

    @Override
    public boolean deletePostById(int i) {
        return postRepository.deletePostById(i);
    }

  
  
    
    
}
