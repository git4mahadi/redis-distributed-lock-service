package com.javatechbd.redisdistributedlock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
  List<SalesEntity> findAllBySalesNoNotNull();
}
