package com.shop.shop.Repositories;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    //Zwraca wszystkie zamowienia dla danego uzytkownika w celu sprawdzenia historii zamowien
    List<Order> getAllByUser(User user);
}
