package com.example.robustclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableCircuitBreaker  // (1)
@EnableRetry
@SpringBootApplication
public class RobustClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(RobustClientApplication.class, args);
  }
}


@RestController
class ShakyRestController {
  private final ShakyBusinessService shakyBusinessService;

  @Autowired
  public ShakyRestController(ShakyBusinessService shakyBusinessService) {
    this.shakyBusinessService = shakyBusinessService;
  }

  @GetMapping("/boom")
  public int boom() throws Exception {
    return this.shakyBusinessService.derivedNumber();
  }
}


class BoomException extends RuntimeException {
  BoomException(String message) {
    super(message);
  }
}

@Service
class ShakyBusinessService {

  @Recover   // (2)
  public int fallback(BoomException ex) {   // circuit breaker
    return 2;
  }

  @CircuitBreaker(include = BoomException.class)  // more convient way of Hystrix circuit breaker
  //@Retryable(include = BoomException.class)    // (2)
  //@HystrixCommand(fallbackMethod = "fallback")      // ** enable for Circuit Breaker (1)
  public int derivedNumber() throws InterruptedException {
    System.out.println("calling derivedNumber()");
    if (Math.random() > .5) {
      Thread.sleep(1000 * 3);
      throw new BoomException("BOOM!!");
    }
    return 1;    // hesitation
  }
}
