package com.epam.shopping.product.service;

import com.epam.shopping.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    ArrayList<Product> database = new ArrayList<>();
    private Long lastId = 0L;

    {
        create(new Product(1L, "Anor", 20.0F, 15000F));
        create(new Product(1L, "Olma", 15.0F, 24000F));
        create(new Product(1L, "Behi", 24.0F, 35000F));
        create(new Product(1L, "Anjir", 28.0F, 45000F));
        create(new Product(1L, "Xurmo", 16.0F, 18000F));
    }


    public List<Product> getAll(){
        return (List<Product>) database.clone();
    }

    public Product create(Product product){
        product.setId(lastId++);
        database.add(product);
        return product.clone();
    }
    public Product getById(Long id){
        for (Product p: database) {
            if(p.getId().equals(id)) return p.clone();
        }
        throw new RuntimeException("not found");
    }

    public Product update(Product product){
        Product old = getById(product.getId());
        old.setName(product.getName());
        old.setPrice(product.getPrice());
        old.setAvailable(product.getAvailable());

        return old.clone();
    }



}
