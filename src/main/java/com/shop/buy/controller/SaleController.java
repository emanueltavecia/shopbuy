package com.shop.buy.controller;

import com.shop.buy.dto.SaleDTO;
import com.shop.buy.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SaleDTO>> getSalesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(saleService.getSalesByCustomerId(customerId));
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<SaleDTO>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(saleService.getSalesByDateRange(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@Valid @RequestBody SaleDTO saleDTO) {
        return new ResponseEntity<>(saleService.createSale(saleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> updateSale(
            @PathVariable Long id,
            @Valid @RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.updateSale(id, saleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
