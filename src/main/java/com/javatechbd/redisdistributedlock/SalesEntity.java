package com.javatechbd.redisdistributedlock;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "sales")
@Getter
@Setter
public class SalesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "sales_no")
  private String salesNo;

  private String instace;

  public SalesEntity(String salesNo, String instace) {
    this.salesNo = salesNo;
    this.instace = instace;
  }

  public SalesEntity(String salesNo) {
    this.salesNo = salesNo;
  }

  public SalesEntity() {
  }
}
