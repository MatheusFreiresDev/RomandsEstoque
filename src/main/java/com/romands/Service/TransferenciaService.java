package com.romands.Service;

import com.romands.Controller.Request.TransferenciaRequest;
import com.romands.Entity.*;
import com.romands.Execeptions.TransfrenciaNotFoundedException;
import com.romands.Repository.TransferenciaRepository;
import com.romands.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;
    public Transferencia create(TransferenciaRequest request) {
        User loja = userRepository.findById(request.lojaId())
                .orElseThrow(() -> new UsernameNotFoundException("Loja nao encontrada."));

        // Monta a lista de ItemPedido a partir do request
        List<ItemPedido> itens = request.produtos().stream()
                .map(dto -> {
                    // Troca aqui pelo nome correto do método no seu DTO para pegar o id do produto
                    Product product = productService.findById(dto.getId())  // se o método for getId()
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + dto.getId()));

                    ItemPedido item = new ItemPedido();
                    item.setProduct(product);
                    item.setQuantidade(dto.getQuantidade());
                    // Não setamos transferencia aqui ainda
                    return item;
                }).collect(Collectors.toList());

        // Cria a transferência com os itens
        Transferencia transferencia = Transferencia.builder()
                .loja(loja)
                .produtos(itens)
                .status(request.transferenciaStatus())
                .createdAt(LocalDateTime.now())
                .build();

        itens.forEach(item -> item.setTransferencia(transferencia));

        // Agora salva tudo junto. O Hibernate vai saber o que fazer
        return transferenciaRepository.save(transferencia);
    }



    public List<Transferencia> findAll() {
        return transferenciaRepository.findAllByOrderByCreatedAt();
    }

    public Transferencia findById(Long id) {
        return transferenciaRepository.findById(id)
                .orElseThrow(() -> new TransfrenciaNotFoundedException("Transferência não encontrada"));
    }

    public Transferencia update(Long id, Transferencia transferenciaDetails) {
        return transferenciaRepository.findById(id)
                .map(transferencia -> {
                    transferencia.setLoja(transferenciaDetails.getLoja());
                    transferencia.setProdutos(transferenciaDetails.getProdutos());
                    return transferenciaRepository.save(transferencia);
                })
                .orElseThrow(() -> new TransfrenciaNotFoundedException("Transferência não encontrada"));
    }

    public Transferencia update(Long id, TransferenciaStatus status) {
        Transferencia transferencia = transferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transferência não encontrada"));

        transferencia.setStatus(status);

        if (status == TransferenciaStatus.FEITA) {
            transferencia.getProdutos().forEach(product ->
                    productService.diminuirQuantidade(product.getId(), product.getQuantidade()));
        }

        return transferenciaRepository.save(transferencia);
    }

    public void delete(Long id) {
        transferenciaRepository.deleteById(id);
    }
}
