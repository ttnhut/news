/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.repository.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mycompany.springmvc.pojos.Category;
import com.mycompany.springmvc.pojos.Post;
import com.mycompany.springmvc.repository.PostRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Asus
 */
@Repository
@Transactional
public class PostRepositoryImpl implements PostRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public boolean addPost(Post post) {
       
        Session session = sessionFactory.getObject().getCurrentSession();
        try{
            if(post.getFile()!=null){
                System.out.println(post.getFile());
                Map r = cloudinary.uploader().upload(post.getFile().getBytes(), ObjectUtils.asMap("resource_type","auto"));
                post.setImage((String)r.get("secure_url"));
            }
            if(post.getCreatedDate()==null)
                post.setCreatedDate(new Date());
            
            session.saveOrUpdate(post);
            return true;
        }
        catch(Exception e){
            System.err.println("--------ERR ADD PRODUCT--------- "+ e.getMessage());
            
        }
        return false;
    }

    @Override
    public List<Post> posts(String keyword) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root root = query.from(Post.class);
        query = query.select(root);
        if(keyword!=null && !keyword.isEmpty()){
            Predicate p1 = builder.like(root.get("title").as(String.class), String.format("%%%s%%", keyword));
            Predicate p2 = builder.like(root.get("description").as(String.class), String.format("%%%s%%", keyword));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {               
                Predicate p3 = builder.equal(root.get("createdDate").as(Date.class),format.parse(keyword) );
                query = query.where(p3);
            } catch (ParseException ex) {
                System.err.println("===========Ngày không đúng định dạng================");
                query = query.where(builder.or(p1,p2));
            }
            
        }
        Query q = session.createQuery(query);
     
        return q.getResultList();
      }

    @Override
    public List<Post> getPostByCategory(int idCategory) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Category c = session.get(Category.class, idCategory);
        CriteriaBuilder builder= session.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root root = query.from(Post.class);
        query = query.select(root);
        Predicate p = builder.equal(root.get("category").as(Category.class), c);
        query = query.where(p);
        Query q = session.createQuery(query);
        
        return q.getResultList();
    }

    @Override
    public Post getPostById(int i) {
        Session session = sessionFactory.getObject().getCurrentSession();
        
        return session.get(Post.class, i);
    }

    @Override
    public boolean deletePostById(int i) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try{
            session.delete(session.get(Post.class, i));
            return true;
        }
        
        catch(Exception e) {
            System.err.println("============ERR DELETE PRODUCT=========" + e.getMessage());
        }
        return false;
    }

    
    
}
