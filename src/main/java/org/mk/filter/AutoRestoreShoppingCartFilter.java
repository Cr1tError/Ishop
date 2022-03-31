package org.mk.filter;

import org.mk.model.ShoppingCart;
import org.mk.model.ShoppingCartItem;
import org.mk.util.SessionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter extends AbstractFilter {
    private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "SHOPPING_CARD_DESERIALIZATION_DONE";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if(req.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null){
            if(!SessionUtils.isCurrentShoppingCartCreated(req)) {
                Cookie cookie = SessionUtils.findShoppingCartCookie(req);
                if(cookie != null) {
                    ShoppingCart shoppingCart = shoppingCartFromString(cookie.getValue());
                    SessionUtils.setCurrentShoppingCart(req, shoppingCart);
                }
            }
            req.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
        }

        chain.doFilter(req, resp);
    }

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
