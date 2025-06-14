package br.matosit.inventory_service.adapters.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import br.matosit.inventory_service.application.ports.out.InventoryRepository;
import br.matosit.inventory_service.domain.entities.Inventory;

@Repository
public interface MongoInventoryRepository
    extends MongoRepository<Inventory, Long>, InventoryRepository {
}
