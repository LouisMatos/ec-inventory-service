package br.matosit.inventory_service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import br.matosit.inventory_service.application.ports.ProductRepository;
import br.matosit.inventory_service.domain.entities.Product;

@Repository
public interface MongoProductRepository
    extends MongoRepository<Product, String>, ProductRepository {

}
