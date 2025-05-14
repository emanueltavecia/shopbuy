package com.shop.buy.controller;

import com.shop.buy.dto.SupplierDTO;
import com.shop.buy.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<SupplierDTO>> getSuppliersByName(@RequestParam String name) {
        return ResponseEntity.ok(supplierService.getSuppliersByName(name));
    }

    @GetMapping("/search/cnpj")
    public ResponseEntity<SupplierDTO> getSupplierByCnpj(@RequestParam String cnpj) {
        return ResponseEntity.ok(supplierService.getSupplierByCnpj(cnpj));
    }

    @GetMapping("/search/email")
    public ResponseEntity<SupplierDTO> getSupplierByEmail(@RequestParam String email) {
        return ResponseEntity.ok(supplierService.getSupplierByEmail(email));
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
