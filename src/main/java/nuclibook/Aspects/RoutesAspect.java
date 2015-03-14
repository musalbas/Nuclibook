package nuclibook.Aspects;

import nuclibook.routes.DefaultRoute;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import spark.Request;

@Aspect
public class RoutesAspect {

    @Before("execution(public Object nuclibook.routes.*.handle(..))")
    public void routeHandleAdvice(JoinPoint joinPoint) {
        DefaultRoute route = (DefaultRoute)joinPoint.getTarget();
        route.prepareToHandle();
    }
}
