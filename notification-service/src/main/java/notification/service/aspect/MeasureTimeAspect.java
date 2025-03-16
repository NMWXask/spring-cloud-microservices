package notification.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MeasureTimeAspect {

    @Around("@annotation(notification.service.annotation.MeasureTime)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        System.out.println("Метод " + joinPoint.getSignature().getName() + " выполнился за " + (endTime - startTime) + " наносекунд.");
        return result;
    }
}