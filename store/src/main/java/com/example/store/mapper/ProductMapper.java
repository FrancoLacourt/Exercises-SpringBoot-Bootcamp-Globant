package com.example.store.mapper;

import com.example.store.dto.ProductDTO;
import com.example.store.entities.Product;

public class ProductMapper {

    /*
    private String productName;
    private Integer price;
     */

    public static ProductDTO toProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
        productDTO.setColor(product.getColor());
        productDTO.setId_product(product.getId_product());
        productDTO.setActive(product.isActive());

        return productDTO;
    }

    public static Product toProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setColor(productDTO.getColor());
        product.setId_product(productDTO.getId_product());
        product.setActive(productDTO.isActive());

        return product;
    }

}
