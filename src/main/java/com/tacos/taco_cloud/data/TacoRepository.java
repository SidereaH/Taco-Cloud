package com.tacos.taco_cloud.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacos.taco_cloud.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long> {

}
