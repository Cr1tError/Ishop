package org.mk.model;

import org.mk.Constants;
import org.mk.exception.ValidationException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private Map<Integer, ShoppingCartItem> products = new HashMap<>();
    private int totalCount = 0;

    private void validateShoppingCartSize(int idProduct){
        if(products.size() > Constants.MAX_PRODUCTS_PER_SHOPPING_CART ||
                (products.size() == Constants.MAX_PRODUCTS_PER_SHOPPING_CART && !products.containsKey(idProduct))) {
            throw new ValidationException("Limit for ShoppingCart size reached: size="+products.size());
        }
    }

    private void validateProductCount(int count) {
        if(count > Constants.MAX_PRODUCT_COUNT_PER_SHOPPING_CART){
            throw new ValidationException("Limit for product count reached: count="+count);
        }
    }

}
