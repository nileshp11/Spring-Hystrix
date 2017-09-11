# Circuit Breaker

[https://drive.google.com/file/d/0B2FBK4i9ZwG3TGtkdkhzeF9jSlk/view]

   Circuit Breaker is not mere try-catch block. Its a stateful and it monitors the pathway between client and the
downstream service. If it sees enough successive attempts have failed, it'll sort of switch the train tracks - directing traffic to the fallback directly (Circuit : OPEN). This gives time to our downstream service to recover.
    Circuit Breaker also has a RESET timeout. It attempts to reintroduce traffic at if possible.

# Hystrix Dashboard :
  - Every Circuit breaker provides a service and event Heartbeat stream if you register the spring boot actuator in classpath. { http://localhost:8080/hystrix.stream }
  - http://localhost:8010/hystrix.html

This code contains Spring demo of Circuit Breaker. Circuit Breaker using HystrixCommand and Spring Retry is commented

### How To Run :

```sh
$ Run RobustClientApplication [http://localhost:8080/boom]
$ Run HystrixDashboardApplication [http://localhost:8010/hystrix.html]
$ Paste [http://localhost:8080/hystrix.stream] in Hystrix Dashboard 
$ Hit Monitor Stream.
$ Keep refreshing [http://localhost:8080/boom]
```

### Plugins
    mvn, SpringBoot, Web, Actuator, Hystrix 

### Development
https://www.youtube.com/watch?v=Kc7dDxn9cUg&t=507s

