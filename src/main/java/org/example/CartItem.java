package org.example;

public class CartItem {
    private int id;
    private String title;
    private double price;
    private int quantity;
    private double total;
    private double discountPercentage;
    private double discountedPrice;

    public CartItem(Product product, int quantity) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.discountPercentage = product.getDiscountPercentage();
        recalculateTotals();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        recalculateTotals();
    }

    private void recalculateTotals() {
        this.total = this.price * this.quantity;
        this.discountedPrice = this.total - (this.total * this.discountPercentage / 100.0);
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
