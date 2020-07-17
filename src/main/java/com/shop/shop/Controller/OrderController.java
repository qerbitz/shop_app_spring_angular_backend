package com.shop.shop.Controller;

import com.shop.shop.Entity.*;
import com.shop.shop.Service.Interface.OrderService;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "cart")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @RequestMapping(value = "shopping-detail", method = RequestMethod.GET)
    public String index() {
        return "shopping-detail";
    }

    @RequestMapping(value = "buy/{id}", method = RequestMethod.GET)
    public String buy(
            @PathVariable("id") int id,
            Model model,
            HttpSession session){

        if(session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<Item>();


            Item item = new Item();

            item.setProduct(productService.getSingleProduct(id));
            item.setQuantity(1);
            cart.add(item);

            session.setAttribute("cart", cart);


        }else{
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index = isExist(id, cart);
            if(index == -1){
                cart.add(new Item(productService.getSingleProduct(id), 1));
            } else{
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }

        return "redirect:/cart/shopping-detail";
    }

    private int isExist(int id, List<Item> cart){
        for(int i = 0; i < cart.size(); i++){
            if(cart.get(i).getProduct().getId_product() == id){
                return i;
            }
        }
        return -1;
    }

}
