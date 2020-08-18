package com.shop.shop.Controller;

import com.shop.shop.Entity.*;
import com.shop.shop.Service.Interface.CartItemService;
import com.shop.shop.Service.Interface.CartService;
import com.shop.shop.Service.Interface.ProductService;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartItemService cartItemService;




    @RequestMapping
    public String get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();
        return "redirect:/cart/"+cartId;
    }

    @RequestMapping(value="/{cartId}", method = RequestMethod.GET)
    public String getCart(@PathVariable(value = "cartId") int cartId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        if(cartId!=user.getCart().getId_cart())
        {
            return "redirect:/cart/"+user.getCart().getId_cart();
        }

        model.addAttribute("cart", cartService.getCartById(cartId).getCartItems());
        model.addAttribute("cart_id", cartService.getCartById(cartId));
        return "cart";
    }


    @RequestMapping("/{cartId}")
    public @ResponseBody Cart getCartById (@PathVariable(value = "cartId") int cartId) {
        return cartService.getCartById(cartId);
    }

    @RequestMapping(value = "/add/{id_product}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addItem (@PathVariable(value = "id_product") String id_product) {

        int produkcik = Integer.parseInt(id_product);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByUsername(authentication.getName());
        Cart cart = user.getCart();

        Product product = productService.getProductById(produkcik);
        List<CartItem> cartItems = cart.getCartItems();

        for (int i=0; i<cartItems.size(); i++) {
            if (product.getId_product() == cartItems.get(i).getProduct().getId_product()) {
                CartItem cartItem = cartItems.get(i);
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setTotal_price(product.getPrice()*cartItem.getQuantity());
                cartItemService.addCartItem(cartItem);
                return;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotal_price(product.getPrice()*cartItem.getQuantity());
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);

    }

    @RequestMapping(value = "/remove/{id_product}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable(value = "id_product") int id_product) {
        Product product = new Product();
        product.setId_product(id_product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        Cart cart = new Cart();
        cart.setId_cart(cartId);

        CartItem cartItem = cartItemService.getCartItemByProductId(product, cart);
        cartItemService.removeCartItem(cartItem);

    }

    @RequestMapping(value = "/clear/{cartId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable(value = "cartId") String cartId) {
        int zmiana = Integer.parseInt(cartId);
        Cart cart = cartService.getCartById(zmiana);
        cartItemService.removeAllCartItems(cart);
    }

}
