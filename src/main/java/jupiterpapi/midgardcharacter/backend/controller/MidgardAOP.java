package jupiterpapi.midgardcharacter.backend.controller;

import jupiterpapi.common.aop.BaseAOP;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MidgardAOP extends BaseAOP {

    @Around("execution(* jupiterpapi.midgardcharacter.backend.controller.MidgardController.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        handleSecurity();
        return log(joinPoint);
    }
}