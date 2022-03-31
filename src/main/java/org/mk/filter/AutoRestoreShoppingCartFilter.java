package org.mk.filter;

import org.mk.model.ShoppingCart;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter {


    protected ShoppingCart shoppingCartFromString(String cookieValue){
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = cookieValue.split("\\|");
        for(String  s : items){
            String[] data = s.split("-");
            try{
                int productId = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                shoppingCart.addProduct(productId, count);
            } catch (RuntimeException e ){
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }
}
