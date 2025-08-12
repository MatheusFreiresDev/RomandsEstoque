CREATE TABLE transferencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loja_id INT NOT NULL,
    FOREIGN KEY (loja_id) REFERENCES users(id)
);
