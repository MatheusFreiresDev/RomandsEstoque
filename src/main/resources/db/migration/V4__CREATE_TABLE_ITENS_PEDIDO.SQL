CREATE TABLE itens_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    transferencia_id INT NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (transferencia_id) REFERENCES transferencias(id)
);
