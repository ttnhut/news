/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.springmvc.formatter;

import com.mycompany.springmvc.pojos.Category;
import java.text.ParseException;
import java.util.Locale;
import org.hibernate.Session;
import org.springframework.format.Formatter;


/**
 *
 * @author Asus
 */
public class CategoryFormatter implements Formatter<Category>{
    
    @Override
    public String print(Category object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public Category parse(String text, Locale locale) throws ParseException {
        Category c = new Category();
        c.setId(Integer.parseInt(text));
        return c;
    }
    
}
