package com.shop.buy.controller;

import com.shop.buy.dto.SaleItemDTO;
import com.shop.buy.service.SaleItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-items")
public class SaleItemController {

    private final SaleItemService saleItemService;

    @Autowired
    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @GetMapping
    public ResponseEntity<List<SaleItemDTO>> getAllSaleItems() {
        return ResponseEntity.ok(saleItemService.getAllSaleItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleItemDTO> getSaleItemById(@PathVariable Long id) {
        return ResponseEntity.ok(saleItemService.getSaleItemById(id));
    }

    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItemsBySaleId(@PathVariable Long saleId) {
        return ResponseEntity.ok(saleItemService.getSaleItemsBySaleId(saleId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItemsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(saleItemService.getSaleItemsByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<SaleItemDTO> createSaleItem(@Valid @RequestBody SaleItemDTO saleItemDTO) {
        return new ResponseEntity<>(saleItemService.createSaleItem(saleItemDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleItemDTO> updateSaleItem(
            @PathVariable Long id,
            @Valid @RequestBody SaleItemDTO saleItemDTO) {
        return ResponseEntity.ok(saleItemService.updateSaleItem(id, saleItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleItem(@PathVariable Long id) {
        saleItemService.deleteSaleItem(id);
        return ResponseEntity.noContent().build();
    }
}
