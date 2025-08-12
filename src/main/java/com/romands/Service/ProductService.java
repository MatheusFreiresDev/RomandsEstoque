package com.romands.Service;

import com.romands.Entity.Product;
import com.romands.Execeptions.InvalidQuantityException;
import com.romands.Execeptions.ProductNotFounded;
import com.romands.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(Long id) {
        if(repository.findById(id).isEmpty()){
            throw new ProductNotFounded();
        };
        return repository.findById(id);
    }

    public Product update(Long id, Product productDetails) {
        return repository.findById(id)
                .map(product -> {
                    product.setNome(productDetails.getNome());
                    product.setRef(productDetails.getRef());
                    product.setPreco(productDetails.getPreco());
                    product.setQuantidade(productDetails.getQuantidade());
                    return repository.save(product);
                })
                .orElseThrow(() -> new ProductNotFounded());
    }

    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProductNotFounded();
        }
        repository.deleteById(id);
    }
    public void diminuirQuantidade(Long id, int quantidade) {
       Product product =  repository.findById(id).orElseThrow(() -> new ProductNotFounded());
       if(quantidade > product.getQuantidade()){
           throw new InvalidQuantityException();
       }
       product.setQuantidade(product.getQuantidade() - quantidade);
       repository.save(product);
    }
}
