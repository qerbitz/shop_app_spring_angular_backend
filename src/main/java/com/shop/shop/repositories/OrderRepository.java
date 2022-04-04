package com.shop.shop.repositories;

import com.shop.shop.entity.Order;
import com.shop.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //Zwraca wszystkie zamowienia dla danego uzytkownika w celu sprawdzenia historii zamowien
    List<Order> getAllByUserOrderByOrderDateDesc(User user);

    Order getOrderByOrderTrackingNumber(String orderTrackingNumber);

    //List<Order> findAll();

}
