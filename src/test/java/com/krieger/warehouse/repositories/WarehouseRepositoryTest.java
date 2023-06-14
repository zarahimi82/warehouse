package com.krieger.warehouse.repositories;

import com.krieger.warehouse.models.Warehouse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class WarehouseRepositoryTest {

        @Autowired
        private WarehouseRepository warehouseRepository;

        private Warehouse warehouse1;
        private Warehouse warehouse2;

        @BeforeEach
        public void setup() {
            warehouse1 = new Warehouse(1l,"001","warehouse1","Berlin");
            warehouse2 = new Warehouse(2l,"002","warehouse2","Berlin");
        }

        @Test
        public void testFindById() {
            // Act
            Optional<Warehouse> foundWarehouse = warehouseRepository.findById(1l);

            // Assert
            Assertions.assertTrue(foundWarehouse.isPresent());
            Assertions.assertEquals(warehouse1.getName(), foundWarehouse.get().getName());
            Assertions.assertEquals(warehouse1.getAddress(), foundWarehouse.get().getAddress());
        }

        @Test
        public void testFindAll() {
            // Act
            List<Warehouse> warehouses = warehouseRepository.findAll();

            // Assert
            Assertions.assertEquals(6, warehouses.size());
            Assertions.assertTrue(warehouses.contains(warehouse1));
            Assertions.assertTrue(warehouses.contains(warehouse2));
        }

        @Test
        public void testSave() {
            // Arrange
            Warehouse newWarehouse = new Warehouse("103","New Warehouse","New Address");

            // Act
            Warehouse savedWarehouse = warehouseRepository.save(newWarehouse);

            // Assert
            Assertions.assertNotNull(savedWarehouse.getId());
            Assertions.assertEquals(newWarehouse.getName(), savedWarehouse.getName());
            Assertions.assertEquals(newWarehouse.getAddress(), savedWarehouse.getAddress());
        }

        @Test
        public void testDelete() {
            // Act
            warehouseRepository.deleteById(2l);

            // Assert
            Optional<Warehouse> deletedWarehouse = warehouseRepository.findById(2l);
            Assertions.assertFalse(deletedWarehouse.isPresent());
        }

}
