package com.tacos.taco_cloud.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.tacos.taco_cloud.TacoOrder;
import com.tacos.taco_cloud.models.User;

import java.util.*;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
  List<TacoOrder> findByDeliveryZip(String deliveryZip);

  List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(
      String deliveryZip, Date startDate, Date endDate);

  List<TacoOrder> findByUserOrderByPlacedAtDesc(
      User user, Pageable pageable);
}
