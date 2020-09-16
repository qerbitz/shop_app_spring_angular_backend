package com.shop.shop.Controller;

import com.shop.shop.Algorithm.Weka;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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


    Weka weka = new Weka();

    @RequestMapping
    public String get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();
        return "redirect:/cart/"+cartId;
    }


    @GetMapping("/{cartId}")
    public String getCart(@PathVariable(value = "cartId") int cartId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        if(cartId!=user.getCart().getId_cart())
        {
            return "redirect:/cart/"+user.getCart().getId_cart();
        }

        model.addAttribute("cart", cartService.getCartById(cartId).getCartItems());
        model.addAttribute("cart_id", cartService.getCartById(cartId));
        return "cart/cart";
    }


    @RequestMapping("/{cartId}")
    public @ResponseBody Cart getCartById (@PathVariable(value = "cartId") int cartId) {
        return cartService.getCartById(cartId);
    }

    @GetMapping("/add/{id_product}")
    public String addItem (@PathVariable(value = "id_product") String id_product, RedirectAttributes redirectAttributes) throws Exception {

        int ajdi = Integer.parseInt(id_product);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByUsername(authentication.getName());
        Cart cart = user.getCart();

        Product product = productService.getProductById(ajdi);
        List<CartItem> cartItems = cart.getCartItems();

        for (int i=0; i<cartItems.size(); i++) {
            if(productService.changeQuantityOfProduct(product, 1)==true){
                System.out.println("Jest git");
                if (product.getId_product() == cartItems.get(i).getProduct().getId_product()) {
                    CartItem cartItem = cartItems.get(i);
                    cartItem.setQuantity(cartItem.getQuantity()+1);
                    cartItem.setTotal_price(product.getPrice()*cartItem.getQuantity());
                    cartItemService.addCartItem(cartItem);
                    return null;
                }
            }
            else {
                System.out.println("Brak towaru");
                return null;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotal_price(product.getPrice()*cartItem.getQuantity());
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);

        List<Integer> listRecommended = weka.Apriori(id_product);
        System.out.println(listRecommended);

        if(productService.changeQuantityOfProduct(product, 1)==true){
            System.out.println("Jest git");
        }
        else {
            System.out.println("Brak towaru");
        }


        redirectAttributes.addAttribute("id_product", id_product);
        return "redirect:/product/test";
    }

    @GetMapping("/remove/{id_product}")
    public String removeItem(@PathVariable(value = "id_product") int id_product){
        Product product = new Product();
        product.setId_product(id_product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        Cart cart = new Cart();
        cart.setId_cart(cartId);

        CartItem cartItem = cartItemService.getCartItemByProductId(product, cart);
        cartItemService.removeCartItem(cartItem);

        return "redirect:/cart/"+user.getCart().getId_cart();  //odswiezenie strony
    }


    @GetMapping("/clear/{cartId}")
    public String clearCart(@PathVariable(value = "cartId") String cartId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        int change = Integer.parseInt(cartId);
        Cart cart = cartService.getCartById(change);
        cartItemService.removeAllCartItems(cart);

        return "redirect:/cart/"+user.getCart().getId_cart(); //odswiezenie strony
    }
}
