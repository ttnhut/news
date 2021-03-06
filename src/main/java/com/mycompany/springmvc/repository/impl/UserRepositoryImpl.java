/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.repository.impl;

import com.mycompany.springmvc.pojos.User;
import com.mycompany.springmvc.repository.UserRepository;
import java.util.List;
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
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public boolean addUser(User user) {
        try{
            Session session = sessionFactory.getObject().getCurrentSession();
            user.setUserRole("ROLE_USER");
            
            session.save(user);
            return true;
        }catch(Exception ex){
            System.err.println("=================ADD USER FAIL ============ " + ex.getMessage());
        }
        return false;
     }

    @Override
    public List<User> users(String username) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root root = query.from(User.class);
        query = query.select(root);
        Predicate p = builder.equal(root.get("username").as(String.class), username);
        query = query.where(p);
        Query q = session.createQuery(query);
        
        return q.getResultList();
        
    }
    
}
