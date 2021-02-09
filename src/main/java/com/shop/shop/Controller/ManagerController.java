package com.shop.shop.Controller;

import com.shop.shop.Service.Interface.CartService;
import com.shop.shop.Service.Interface.OrderService;
import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class ManagerController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @RequestMapping("/panel")
    public String panel(Model model) {

        String month = "Luty";

        model.addAttribute("month", month);
        model.addAttribute("productList", productService.getListOfProductsOrderBySaleDesc(2));
        return "manager/manager_view";
    }

    @PostMapping("/panel")
    public String panel2(@RequestParam("month") String value,
                         Model model) {



        String month = changeMonth(value);


        model.addAttribute("month", month);
        model.addAttribute("productList", productService.getListOfProductsOrderBySaleDesc(Integer.valueOf(value)));
        return "manager/manager_view";
    }

    @RequestMapping("/ordersList")
    public String ordersList(Model model) {


        model.addAttribute("orders_list", orderService.getAllOrders());
        return "manager/manager_view_orders";
    }

    @GetMapping("/detailsOrder/{cartId}")
    public String detailsOrder(@PathVariable(value = "cartId") int cartId, @RequestParam(value = "orderId") int orderId, Model model, RedirectAttributes redirectAttributes){



        if(cartId!=orderService.getOrderById(orderId).getCart().getId_cart())
        {
            redirectAttributes.addAttribute("cartId", orderService.getOrderById(orderId).getCart().getId_cart());
            redirectAttributes.addAttribute("orderId", orderId);
            return "redirect:/order/detailsOrder/{cartId}";
        }

        model.addAttribute("quantity", cartService.getQuantityofCart(cartId));
        model.addAttribute("cart", cartService.getCartById(cartId).getCartItems());
        model.addAttribute("total_order", cartService.getTotalPrice(cartId));
        return "manager/manager_view_orders_details";
    }

    public String changeMonth(String value){
        String month = "";

        if(value.contains("1")){
            month = "Styczeń";
        }
        if(value.contains("2")){
            month = "Luty";
        }
        if(value.contains("3")){
            month = "Marzec";
        }
        if(value.contains("4")){
            month = "Kwiecień";
        }
        if(value.contains("5")){
            month = "Maj";
        }
        if(value.contains("6")){
            month = "Czerwiec";
        }
        if(value.contains("7")){
            month = "Lipiec";
        }
        if(value.contains("8")){
            month = "Sierpień";
        }
        if(value.contains("9")){
            month = "Wrzesień";
        }
        if(value.contains("10")){
            month = "Październik";
        }
        if(value.contains("11")){
            month = "Listopad";
        }
        if(value.contains("12")){
            month = "Grudzień";
        }


        return month;
    }

}
