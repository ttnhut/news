/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.repository;

import com.mycompany.springmvc.pojos.Category;
import com.mycompany.springmvc.pojos.Post;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface PostRepository {
    boolean addPost(Post post);
    List<Post> posts(String keyword);
    List<Post> getPostByCategory(int idCategory);
    Post getPostById(int id);
    boolean deletePostById(int id);
}
