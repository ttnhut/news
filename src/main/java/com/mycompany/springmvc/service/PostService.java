/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.service;

import com.mycompany.springmvc.pojos.Category;
import com.mycompany.springmvc.pojos.Post;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface PostService {
    boolean addPost(Post post);
    boolean updatePost(int id , Post post);
    List<Post> posts(String keyword);
    List<Post> getPostByCategory(int idCategory);
    Post getPostById(int id);
    boolean deletePostById(int id);
}
