package bg.moviebox.web.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

  // Pointcut matches all methods who have annotation WarnIfExecutionExceeds
  @Pointcut("@annotation(bg.moviebox.web.aop.WarnIfExecutionExceeds)")
  void onWarnIfExecutionTimeExceeds(){}

}
