package com.javatechbd.redisdistributedlock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

  private final RedissonClient redissonClient;
  private final SalesRepository salesRepository;

  public String lock() {

    RLock rLock = redissonClient.getLock("myLock3");

    try {
      log.info(" Into the method ");
      RBucket<String> bucket = redissonClient.getBucket("stringObject3");
      //  Lock ,30 Seconds later, the lock will be released automatically
      rLock.lock(30, TimeUnit.SECONDS);
      log.info("For the lock ");

      if (bucket.get() != null) {
        int newSl = Integer.parseInt(bucket.get().substring(3));
        bucket.set("INV" + (++newSl));
        log.warn("Bucket Value: {}", bucket.get());
      } else {
        int totalCount = salesRepository.findAllBySalesNoNotNull().size();
        bucket.set("INV" + (++totalCount));
        log.warn("Bucket Value: {}", bucket.get());
      }
      Thread.sleep(1000);

      return bucket.get();
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      throw new RuntimeException("eroor whiel creatting id");
    } finally {
      rLock.unlock();
      log.info("Lock released ");
    }
  }

  public void resetBucket() {
    RBucket<String> bucket = redissonClient.getBucket("stringObject3");
    bucket.delete();
  }


  public String getSalesId() {
    String salesId = null;
    while (salesId == null) {
      salesId = this.lock();
    }
    return salesId;
  }
}
