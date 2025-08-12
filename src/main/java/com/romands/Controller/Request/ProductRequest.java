package com.romands.Controller.Request;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ProductRequest(String nome, String ref, BigDecimal preco,int quantidade) {
}
