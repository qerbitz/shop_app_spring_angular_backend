package com.shop.shop.Controller;


import com.shop.shop.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ManagerController {

    @Autowired
    ProductService productService;

    @RequestMapping("/panel")
    public String panel(Model model) {


        model.addAttribute("productList", productService.getListOfProductsOrderBySaleDesc());
        return "manager/manager_view";
    }
}
