package com.shop.shop.Repositories;

import com.shop.shop.Entity.Order;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    //Zwraca wszystkie zamowienia dla danego uzytkownika w celu sprawdzenia historii zamowien
    List<Order> getAllByUser(User user);

    Optional<Order> getAllByUserOrderByOrderDateDesc(User user);

    List<Order> findAllByOrderByOrderDateDesc();

}
