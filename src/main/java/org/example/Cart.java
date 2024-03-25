package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cart{
    private int id;
    private int userId;
    private List<CartItem> products;
    private double total;
    private double discountedTotal;
    private int totalQuantity;

    public Cart() {
        this.products = new ArrayList<>();
        this.total = 0.0;
        this.discountedTotal = 0.0;
        this.totalQuantity = 0;
    }

    private void recalculateTotals() {
        total = 0.0;
        discountedTotal = 0.0;
        totalQuantity = 0;

        for (CartItem product : products) {
            total += product.getTotal();
            discountedTotal += product.getDiscountedPrice();
            totalQuantity += product.getQuantity();
        }
    }

    public void addItem(CartItem item) {
        this.products.add(item);
        recalculateTotals();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

