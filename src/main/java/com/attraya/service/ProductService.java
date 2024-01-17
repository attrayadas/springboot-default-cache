package com.attraya.service;

import com.attraya.entity.Product;
import com.attraya.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct // init method
    public void initDB(){
        List<Product> products = IntStream.rangeClosed(1, 10000)
                .mapToObj(i -> new Product(i, "product" + i, new Random().nextInt(5000), "desc" + i, "type" + i))
                .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }


}
