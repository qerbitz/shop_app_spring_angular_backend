package com.shop.shop.Controller;

import com.shop.shop.Algorithm.Weka;
import com.shop.shop.Entity.*;
import com.shop.shop.Repositories.CartItemRepository;
import com.shop.shop.Repositories.CartRepository;
import com.shop.shop.Service.Interface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CartItemService cartItemService;


    Weka weka = new Weka();


    @RequestMapping
    public String get() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();
        return "redirect:/cart/" + cartId;
    }


    @GetMapping("/{cartId}")
    public String getCart(@PathVariable(value = "cartId") int cartId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        if (cartId != user.getCart().getId_cart()) {
            return "redirect:/cart/" + user.getCart().getId_cart();
        }

        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
        model.addAttribute("cart", cartService.getCartById(cartId).getCartItems());
        model.addAttribute("cart_id", cartService.getCartById(cartId));
        model.addAttribute("total", cartService.getTotalPrice(user.getCart().getId_cart()));
        return "cart/cart";
    }


    @RequestMapping("/{cartId}")
    public @ResponseBody
    Cart getCartById(@PathVariable(value = "cartId") int cartId) {
        return cartService.getCartById(cartId);
    }

    @RequestMapping(value = "/add/{id_product}", method = RequestMethod.GET)
    public String addItem(
            @RequestParam(value = "listOffCategoryChecked", required = false) List<Integer> listOfCategoryChecked,
            @RequestParam(value = "listOffAgesChecked", required = false) List<String> listOfAgesChecked,
            @RequestParam(value = "price_min", required = false) String price_min,
            @RequestParam(value = "price_max", required = false) String price_max,
            @PathVariable(value = "id_product") String id_product,
            RedirectAttributes redirectAttributes) throws Exception {


        List<Integer> listRecommended = weka.Apriori(id_product);

        if (listRecommended != null) {
            List<Product> listRecommendedProducts = new ArrayList<>();
            for (int i = 0; i < listRecommended.size(); i++) {
                listRecommendedProducts.add(productService.getProductById(listRecommended.get(i)));
            }
        }


        int ajdi = Integer.parseInt(id_product);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByUsername(authentication.getName());
        Cart cart = user.getCart();

        Product product = productService.getProductById(ajdi);
        List<CartItem> cartItems = cart.getCartItems();


        for (int i = 0; i < cartItems.size(); i++) {
            if (productService.changeQuantityOfProduct(product, 1) == true) {
                //System.out.println("Jest ok");
                if (product.getId_product() == cartItems.get(i).getProduct().getId_product()) {
                    CartItem cartItem = cartItems.get(i);
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItem.setTotal_price(product.getPrice() * cartItem.getQuantity());
                    cartItemService.addCartItem(cartItem);

                    redirect(listOfCategoryChecked, listOfAgesChecked, price_min, price_max, redirectAttributes, id_product);
                    return "redirect:/product/productList";
                }
            } else {
                //System.out.println("Brak towaru");

                redirect(listOfCategoryChecked, listOfAgesChecked, price_min, price_max, redirectAttributes, id_product);
                return "redirect:/product/productList";
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotal_price(product.getPrice() * cartItem.getQuantity());
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);

        System.out.println(Integer.parseInt(id_product));

        redirect(listOfCategoryChecked, listOfAgesChecked, price_min, price_max, redirectAttributes, id_product);
        return "redirect:/product/productList";
    }

    @GetMapping("/remove/{id_product}")
    public String removeItem(@PathVariable(value = "id_product") int id_product) {
        Product product = new Product();
        product.setId_product(id_product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        int cartId = user.getCart().getId_cart();

        Cart cart = new Cart();
        cart.setId_cart(cartId);

        CartItem cartItem = cartItemService.getCartItemByProductId(product, cart);
        cartItemService.removeCartItem(cartItem);

        return "redirect:/cart/" + user.getCart().getId_cart();  //odswiezenie strony
    }


    @GetMapping("/clear/{cartId}")
    public String clearCart(@PathVariable(value = "cartId") String cartId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        int change = Integer.parseInt(cartId);
        Cart cart = cartService.getCartById(change);
        cartItemService.removeAllCartItems(cart);

        return "redirect:/cart/" + user.getCart().getId_cart(); //odswiezenie strony
    }

    @PostMapping("/changeQuantity/{id_cart_item}")
    public String changeQuantity(@PathVariable(value = "id_cart_item") int id_product,
                                 @RequestParam(value = "new_quantity") int quantity) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        System.out.println(id_product);
        System.out.println(quantity);

        CartItem cart_item = cartItemService.getCartItemByProductId(productService.getProductById(id_product), cartService.getCartById(user.getCart().getId_cart()));
        cart_item.setTotal_price(productService.getProductById(id_product).getPrice() * quantity);
        cart_item.setQuantity(quantity);
        cartItemRepository.save(cart_item);

        return "redirect:/cart/" + user.getCart().getId_cart(); //odswiezenie strony
    }


    public void redirect(List<Integer> listOfCategoryChecked, List<String> listOfAgesChecked, String price_min, String price_max, RedirectAttributes redirectAttributes, String id_product) {
        redirectAttributes.addAttribute("categoryCheckedList", listOfCategoryChecked);
        redirectAttributes.addAttribute("listOfAgesChecked", listOfAgesChecked);
        redirectAttributes.addAttribute("price_min", price_min);
        redirectAttributes.addAttribute("price_max", price_max);
        redirectAttributes.addAttribute("id_product", id_product);
    }
}
