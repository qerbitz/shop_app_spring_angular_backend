package com.shop.shop.Repositories;

import com.shop.shop.Entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query(
            value = "select * " +
                    " from OrderDetail od, Order o, User u" +
                    " where o.id_order = od.id_order_detail" +
                    " and o.id_user = 1",
            nativeQuery = true
    )
    List<OrderDetail> findAllByUser();

}
