package com.attraya.service;

import com.attraya.entity.Product;
import com.attraya.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@CacheConfig(cacheNames = "products")
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

    @Cacheable
    public List<Product> getProducts() {
        log.info("ProductService::getProducts() connecting to Database...");
        return productRepository.findAll();
    }

    @CachePut(key = "#product.id")
    public Product saveProduct(Product product) {
        log.info("ProductService::saveProduct() connecting to Database...");
        return productRepository.save(product);
    }

    @Cacheable(key = "#id")
    public Product getProductById(int id) {
        log.info("ProductService::getProductsById() connecting to Database...");
        return productRepository.findById(id).get();
    }

    @CachePut(key = "#id")
    public Product updateProduct(int id, Product productRequest) {
        // get the product from DB by id
        // update with new value getting from request
        Product existingProduct = productRepository.findById(id).get(); // DB
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setProductType(existingProduct.getProductType());
        log.info("ProductService::updateProduct() connecting to Database...");
        return productRepository.save(existingProduct);
    }

    @CacheEvict(key = "#id")
    public long deleteProduct(int id) {
        log.info("ProductService::deleteProduct() connecting to Database...");
        productRepository.deleteById(id);
        return productRepository.count();
    }
}
