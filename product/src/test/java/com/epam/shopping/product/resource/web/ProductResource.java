package com.epam.shopping.product.resource.web;

import com.epam.shopping.product.model.Product;
import com.epam.shopping.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductResource {

    @Autowired
    private ProductService productService;


    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }
}
