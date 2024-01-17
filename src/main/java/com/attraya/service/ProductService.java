package com.attraya.service;

import com.attraya.entity.Product;
import com.attraya.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    @PostConstruct // init method
//    public void initDB(){
//        List<Product> products = IntStream.rangeClosed(1, 10000)
//                .mapToObj(i -> new Product(i, "product" + i, new Random().nextInt(5000), "desc" + i, "type" + i))
//                .collect(Collectors.toList());
//        productRepository.saveAll(products);
//    }

    @Cacheable(cacheNames = "products")
    public List<Product> getProducts() {
        log.info("ProductService::getProducts() connecting to Database");
        return productRepository.findAll();
    }


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }
}
