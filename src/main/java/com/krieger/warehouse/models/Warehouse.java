package com.krieger.warehouse.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WAREHOUSE")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Code is required")
    @Pattern(regexp = "\\d{3}", message = "Code must contain exactly 3 digits")
    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = true)
    private String name;

    @NotNull(message = "address is required")
    @Column(nullable = false)
    private String address;

    @JsonManagedReference
    @OneToMany(mappedBy = "warehouse",fetch=FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Bay> Bays;

    public Warehouse(String code, String name, String address) {
        this.code = code;
        this.name = name;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(id, warehouse.id) &&
                Objects.equals(code, warehouse.code);
    }

    // Override hashCode() method
    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
    @Override
    public String toString() {
        return "Warehouse [id=" + id + ", code=" + code + "]";
    }
}
