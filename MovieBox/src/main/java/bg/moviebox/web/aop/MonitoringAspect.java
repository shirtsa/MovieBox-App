package bg.moviebox.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Aspect
@Component
public class MonitoringAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringAspect.class);

  @Around("bg.moviebox.web.aop.Pointcuts.onWarnIfExecutionTimeExceeds()")
  Object monitorExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

    //Around advise of this pointcut - Pointcuts.onWarnIfExecutionTimeExceeds()
    //gets the annotation from ProceedingJoinPoint (pjp)
    WarnIfExecutionExceeds annotation = getAnnotation(pjp);

    long threshold = annotation.threshold();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // before
    var result = pjp.proceed();
    // after
    stopWatch.stop();
    long methodExecutionTime = stopWatch.lastTaskInfo().getTimeMillis();

    if (methodExecutionTime > threshold) {
      LOGGER.warn("The method {} executed in {} millis which is more than " +
          "the acceptable threshold of {} millis. Threshold exceeded by {}.",
          pjp.getSignature(),
          methodExecutionTime,
          threshold,
          methodExecutionTime - threshold);
    }

    return result;
  }

  private static WarnIfExecutionExceeds getAnnotation(ProceedingJoinPoint pjp) {

    Method method = ((MethodSignature)pjp.getSignature()).getMethod();

    //   @GetMapping("/api/convert")
    //  public ResponseEntity<ConversionResultDTO> convert(
    //      @RequestParam("from") String from,
    //      @RequestParam("to") String to,
    //      @RequestParam("amount") BigDecimal amount
    //  ) {
    // Is encapsulate in method

    return method.getAnnotation(WarnIfExecutionExceeds.class);
  }
}
