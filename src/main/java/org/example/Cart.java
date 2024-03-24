package org.example;

import java.util.List;

public class Cart {
    private int id;
    private List<CartItem> products;

    public int getId() {
        return id;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart: \n");
        sb.append("id=").append(id);
        sb.append(", products=");
        if (products == null) {
            sb.append("null");
        } else {
            sb.append('[');
            for (int i = 0; i < products.size(); i++) {
                sb.append(products.get(i).toString());
                if (i < products.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(']');
        }
        return sb.toString();
    }
}

