package com.javatechbd.redisdistributedlock;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {

  private final RedisService redisService;

  @GetMapping("/reset")
  public void resetRedis() {
    redisService.resetBucket();
  }
}
