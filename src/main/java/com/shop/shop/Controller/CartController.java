package com.shop.shop.Controller;

import com.shop.shop.Entity.*;
import com.shop.shop.Service.Interface.CartItemService;
import com.shop.shop.Service.Interface.CartService;
import com.shop.shop.Service.Interface.ProductService;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
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


    @GetMapping("/showCartList")
    public String showCartList(Model model){


        return "cart";
    }


    @RequestMapping
    public String get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();
        return "redirect:/cart/"+1;
    }



    @RequestMapping(value="/{cartId}", method = RequestMethod.GET)
    public String getCart(@PathVariable(value = "cartId") int cartId, Model model) {
        model.addAttribute("cart", cartService.getCartById(cartId).getCartItems());
        List<CartItem> listka = new ArrayList<>();
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

        User user = userService.getUserByUsername("john");
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
        CartItem cartItem = cartItemService.getCartItemByProductId(id_product);
        cartItemService.removeCartItem(cartItem);
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable(value = "cartId") int cartId) {
        Cart cart = cartService.getCartById(cartId);
        //cartItemService.removeAllCartItems(cart);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload.")
    public void handleClientErrors (Exception e){}

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error.")
    public void handleServerErrors(Exception e){}

}
