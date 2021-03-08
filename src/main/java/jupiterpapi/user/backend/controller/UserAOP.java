package jupiterpapi.user.backend.controller;

import jupiterpapi.common.aop.BaseAOP;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAOP extends BaseAOP {

    @Around("execution(* jupiterpapi.user.backend.controller.UserController.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        handleSecurity();
        return log(joinPoint);
    }
}