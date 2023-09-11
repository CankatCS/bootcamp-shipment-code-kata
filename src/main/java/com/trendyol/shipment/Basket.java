package com.trendyol.shipment;

import java.util.*;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        Map<ShipmentSize, Long> shipmentSizeCounts = products.stream()
                .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));
        Optional<Map.Entry<ShipmentSize, Long>> mostRepeatedEntry = shipmentSizeCounts.entrySet().stream()
                .filter(entry -> entry.getValue() >= 3)
                .max(Map.Entry.comparingByValue());
        if (mostRepeatedEntry.isPresent()) {
            return upscaleCargoSize(mostRepeatedEntry.get().getKey());
        } else {
            ShipmentSize largestSize = products.stream()
                    .map(Product::getSize)
                    .max(Comparator.naturalOrder())
                    .orElse(null);
            return largestSize;
        }
    }

    public ShipmentSize upscaleCargoSize(ShipmentSize shipmentSize) {
        switch (shipmentSize) {
            case SMALL:
                return ShipmentSize.MEDIUM;
            case MEDIUM:
                return ShipmentSize.LARGE;
            case LARGE:
                return ShipmentSize.X_LARGE;
            case X_LARGE:
                return ShipmentSize.X_LARGE;
            default:
                return null;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
