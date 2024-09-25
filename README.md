This repository is a practice for creating applications with Java and Spring. The idea is pretty simple, is a deposit where you can store products.

For the moment it has 3 endpoints:
- GET /products/{uuid} : it finds a product by its uuid. If its not present it throws a 404 exception.
- GET /products/{uuid}/weight : it calculates the total weight with the product of the quantity and the weight of a simple unit.
- PUT /products : it receives a body with the data of a product, it adds the quantity to the existing one. If it does not exist, it creates it.

This application was tested with a local database in MySQL created with this simple script:

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity INT,
    unit_weight DOUBLE,
    UNIQUE (uuid),
    INDEX idx_uuid (uuid),
    INDEX idx_name (name)
);

Extra things:
- Mockito Unit Tests
- Lombok
- Jakarta Contraint Validations
- Exception handlings
- JPA repository with a transactional method built with a HQL query.
