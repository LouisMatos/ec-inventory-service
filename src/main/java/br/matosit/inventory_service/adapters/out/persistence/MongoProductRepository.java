package br.matosit.inventory_service.adapters.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import br.matosit.inventory_service.application.ports.out.ProductRepository;
import br.matosit.inventory_service.domain.entities.Product;

@Repository
public interface MongoProductRepository
    extends MongoRepository<Product, String>, ProductRepository {
}
