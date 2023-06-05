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
    log.info(" Into the method ");
    RBucket<String> bucket = redissonClient.getBucket("stringObject3");
    try {
      //  Lock ,30 Seconds later, the lock will be released automatically
      rLock.lock(30, TimeUnit.SECONDS);
      log.info("For the lock ");

      if(bucket.get()!=null) {
        int newSl = Integer.parseInt(bucket.get().substring(3));
        bucket.set("INV"+ (++newSl));
        log.warn("Bucket Value: {}", bucket.get());
      } else {
        int totalCount = salesRepository.findAllBySalesNoNotNull().size();
        bucket.set("INV"+totalCount);
        log.warn("Bucket Value: {}", bucket.get());
      }
      Thread.sleep(1000);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    } finally {
      //  Release the lock
//            RMap<String, String> map = redissonClient.getMap("theMap");
//            String mapValue = map.get("mapKey");
//            if(map.get("mapKey")!=null) {
//                Integer sl = Integer.parseInt(mapValue);
//                map.put("mapKey", Integer.toString(++sl));
//            }
//            System.out.println("The map value is: " + map.get("mapKey"));
      rLock.unlock();
      log.info("Lock released ");
    }
    return bucket.get();
  }

  public void resetBucket() {
    RBucket<String> bucket = redissonClient.getBucket("stringObject3");
    bucket.delete();
  }
}
