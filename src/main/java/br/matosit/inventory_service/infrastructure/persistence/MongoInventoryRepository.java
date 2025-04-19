package br.matosit.inventory_service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.matosit.inventory_service.application.ports.InventoryRepository;
import br.matosit.inventory_service.domain.entities.Inventory;

@Repository
public interface MongoInventoryRepository
    extends MongoRepository<Inventory, String>, InventoryRepository {

}
