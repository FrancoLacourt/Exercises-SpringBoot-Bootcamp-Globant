package com.example.store.controllers;

import com.example.store.dto.ClientDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.entities.Product;
import com.example.store.exceptions.MyException;
import com.example.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class  ProductController{

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<ProductDTO> register(@RequestBody ProductDTO productDTO) {

        try {
            ProductDTO savedProductDTO = productService.createProduct(productDTO);

            if (productDTO.getProductName() == null || productDTO.getPrice() == null
                    || productDTO.getColor() == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO);

        } catch (MyException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listOfProducts")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> productsDTO = productService.getActiveProducts();

        if (productsDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsDTO);
    }

    @GetMapping("/{id_product}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id_product) {
        ProductDTO productDTO = productService.findProductById(id_product);

        if (productDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findByName")
    public ResponseEntity<ProductDTO> findByName(@RequestParam String productName) {
        ProductDTO productDTO = productService.findProductByName(productName);

        if (productDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id_product}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id_product, @RequestBody ProductDTO updatedProductDTO) throws MyException {

        ProductDTO productDTO = productService.updateProduct(id_product, updatedProductDTO);

        if (updatedProductDTO.getProductName() == null || updatedProductDTO.getPrice() == null
                || updatedProductDTO.getColor() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        if (productDTO.getId_product() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PutMapping("/deactivate/{id_product}")
    public ResponseEntity<ProductDTO> deactivateProduct(@PathVariable Long id_product) throws MyException {

        ProductDTO productDTO = productService.findProductById(id_product);

        if (productDTO != null) {
            ProductDTO deactivatedProductDTO = productService.deactivateProduct(id_product);
            return ResponseEntity.status(HttpStatus.OK).body(deactivatedProductDTO);
        } else {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(null);
        }
    }
}
