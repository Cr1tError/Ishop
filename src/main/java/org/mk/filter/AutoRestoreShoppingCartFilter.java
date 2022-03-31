package org.mk.filter;

import org.mk.model.ShoppingCart;
import org.mk.model.ShoppingCartItem;

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

    protected String shoppingCartToString(ShoppingCart shoppingCart){
        StringBuilder result = new StringBuilder();
        for(ShoppingCartItem item : shoppingCart.getItems()){
            result.append(item.getIdProduct()).append("-").append(item.getCount()).append("|");
        }
        if (result.length() > 0){
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }
}
