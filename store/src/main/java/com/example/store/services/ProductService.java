package com.example.store.services;

import com.example.store.dto.ProductDTO;
import com.example.store.entities.Product;
import com.example.store.exceptions.MyException;
import com.example.store.mapper.ProductMapper;
import com.example.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO productDTO) throws MyException {
        validate(productDTO);
        Product product = ProductMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toProductDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productsDTO = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = ProductMapper.toProductDTO(product);
            productsDTO.add(productDTO);
        }
        return productsDTO;
    }

    public List<ProductDTO> getActiveProducts() {
        List<ProductDTO> productsDTO = getAllProducts();

        return productsDTO.stream().filter(ProductDTO::isActive).collect(Collectors.toList());
    }

    public ProductDTO findProductById(long id_product) {

        Product product = productRepository.findById(id_product).orElse(null);

        if (product != null) {
            return ProductMapper.toProductDTO(product);
        } else {
            System.out.println("It wasn´t possible to find a product with the ID: " + id_product);
            return null;
        }
    }

    public ProductDTO findProductByName(String productName) {

        Product product = productRepository.findByProductNameAndIsActiveIsTrue(productName);

        if (product != null) {
            return ProductMapper.toProductDTO(product);
        } else {
            System.out.println("It wasn´t possible to find a product with the name: " + productName);
            return null;
        }
    }

    public ProductDTO updateProduct(Long id_product, ProductDTO updatedProductDTO) throws MyException {

        validate(updatedProductDTO);

        Product product = productRepository.findById(id_product).orElse(null);

        if (product != null) {

            product.setProductName(updatedProductDTO.getProductName());
            product.setPrice(updatedProductDTO.getPrice());
            product.setColor(updatedProductDTO.getColor());

            Product savedProduct = productRepository.save(product);

            return ProductMapper.toProductDTO(savedProduct);
        } else {
            System.out.println("It wasn't possible to find a product with the ID: " + id_product);
            return null;
        }
    }

    public ProductDTO deactivateProduct(Long id_product) {

        Product product = productRepository.findById(id_product).orElse(null);

        if (product != null) {
            product.setActive(false);
            Product savedProduct = productRepository.save(product);
            ProductDTO productDTO = ProductMapper.toProductDTO(savedProduct);
            return productDTO;
        } else {
            System.out.println("It wasn´t possible to find a product with the ID: " + id_product);
            return null;
        }
    }



    public boolean onlySpaces(String input) {
        return input.trim().isEmpty();
    }

    private void validate(ProductDTO productDTO) throws MyException {

        if (productDTO.getProductName() == null || onlySpaces(productDTO.getProductName())) {
            throw new MyException("Product's name can't be null or empty");
        }

        if (productDTO.getPrice() == null) {
            throw new MyException("Product's price can't be null or empty");
        }
    }
}
