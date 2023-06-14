package com.krieger.warehouse.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueNumbersAndWarehouse", columnNames = {"rowNumber", "shelfNumber", "levelNumber", "warehouse_id"})})
public class Bay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowNumber;

    private int shelfNumber;

    private int levelNumber;

    @Enumerated(EnumType.ORDINAL)
    private BayType type;

    @Min(value = 1, message = "Value must be greater than or equal to 1")
    @Max(value = 9, message = "Value must be less than or equal to 9")
    private int holldingPoint;

    private int occupiedHoldingPoint;

    private String tags;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public Bay() {
        warehouse = new Warehouse();
    }

    public Bay(int rowNumber, int shelfNumber, int levelNumber, BayType type, int holldingPoint, int occupiedHoldingPoint, String tags, Warehouse warehouse) {
        this.rowNumber = rowNumber;
        this.shelfNumber = shelfNumber;
        this.levelNumber = levelNumber;
        this.type = type;
        this.holldingPoint = holldingPoint;
        this.occupiedHoldingPoint = occupiedHoldingPoint;
        this.tags = tags;
        this.warehouse = warehouse;
    }

    public void setOccupiedHoldingPoint(int occupiedHoldingPoint) {
        this.occupiedHoldingPoint = occupiedHoldingPoint;
        /// TODO: 6/4/2023   validator.setHoldingPoint(holldingPoint); // Set the holdingPoint value in the validator
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bay bay = (Bay) o;
        return Objects.equals(id, bay.id);
    }

    // Override hashCode() method
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bay [id=" + id + ", rowNumber=" + rowNumber + "]";
    }
}

