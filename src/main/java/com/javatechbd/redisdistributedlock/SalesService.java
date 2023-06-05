package com.javatechbd.redisdistributedlock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesService {

  private final SalesRepository salesRepository;
}
