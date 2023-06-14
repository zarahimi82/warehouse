package com.krieger.warehouse.repositories;

import com.krieger.warehouse.models.Bay;
import com.krieger.warehouse.models.BayType;
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
public class BayRepositoryTest {

    @Autowired
    private BayRepository bayRepository;

    private Bay bay1;
    private Bay bay2;

    @BeforeEach
    public void setup() {
        bay1 = new Bay(1l, 1, 1, 1, BayType.CART, 5, 1, "tag1;Tag2", new Warehouse(1l, "001", "warehouse1", "Berlin"));
        ;
        bay2 = new Bay(2l, 2, 1, 1, BayType.CART, 5, 1, "tag1;Tag2", new Warehouse(1l, "001", "warehouse1", "Berlin"));
    }

    @Test
    public void testFindById() {
        // Act
        Optional<Bay> foundBay = bayRepository.findById(1l);

        // Assert
        Assertions.assertTrue(foundBay.isPresent());
        assertBay(foundBay.get(), bay1);
    }

    @Test
    public void testFindAll() {
        // Act
        List<Bay> bays = bayRepository.findAll();

        // Assert
        Assertions.assertEquals(6, bays.size());
        Assertions.assertTrue(bays.contains(bay1));
        Assertions.assertTrue(bays.contains(bay2));
    }

    @Test
    public void testSave() {
        // Arrange
        Bay newBay = new Bay(20, 1, 1, BayType.CART, 5, 1, "tag1;Tag2", new Warehouse(1l, "001", "warehouse1", "Berlin"));
        ;

        // Act
        Bay savedBay = bayRepository.save(newBay);

        // Assert
        Assertions.assertNotNull(savedBay.getId());
        assertBay(newBay, savedBay);
    }

    @Test
    public void testDelete() {
        // Act
        bayRepository.deleteById(2l);

        // Assert
        Optional<Bay> deletedBay = bayRepository.findById(2l);
        Assertions.assertFalse(deletedBay.isPresent());
    }

    private void assertBay(Bay response, Bay bay1) {

        Assertions.assertEquals(bay1.getRowNumber(), response.getRowNumber());
        Assertions.assertEquals(bay1.getShelfNumber(), response.getShelfNumber());
        Assertions.assertEquals(bay1.getLevelNumber(), response.getLevelNumber());
        Assertions.assertEquals(bay1.getType(), response.getType());
        Assertions.assertEquals(bay1.getOccupiedHoldingPoint(), response.getOccupiedHoldingPoint());
        Assertions.assertEquals(bay1.getOccupiedHoldingPoint(), response.getOccupiedHoldingPoint());
        Assertions.assertEquals(bay1.getTags(), response.getTags());
        Assertions.assertEquals(bay1.getWarehouse().getCode(), response.getWarehouse().getCode());
        Assertions.assertEquals(bay1.getWarehouse().getName(), response.getWarehouse().getName());
        Assertions.assertEquals(bay1.getWarehouse().getAddress(), response.getWarehouse().getAddress());
    }

}
