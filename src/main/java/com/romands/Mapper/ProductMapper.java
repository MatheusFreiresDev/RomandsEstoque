package com.romands.Mapper;

import com.romands.Controller.Request.ProductRequest;
import com.romands.Entity.Product;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ProductMapper {

    public static Product toEntity(String nome, String ref, BigDecimal preco, int quantidade) {
        return Product.builder()
                .nome(nome)
                .ref(ref)
                .preco(preco)
                .quantidade(quantidade)
                .build();
    }

    public static ProductRequest toDTO(Product product) {
        if (product == null) return null;

        return ProductRequest.builder()
                .nome(product.getNome())
                .ref(product.getRef())
                .preco(product.getPreco())
                .quantidade(product.getQuantidade())
                .build();
    }
}

