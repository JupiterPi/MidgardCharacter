package jupiterpapi.common.aop;

import jupiterpapi.common.correlationid.CorrelationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class BaseAOP {

    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void handleSecurity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            try {
                User user = (User) auth.getPrincipal();
                if (user != null) {
                    logger.info(TECHNICAL, " User {} ({})", user.getUsername(), user.getPassword());

                    CorrelationContext.setUser(user);
                    MDC.put("user", user.getUsername());
                }
            } catch (ClassCastException exp) {
                //do nothing
            }
        }
    }

    protected Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        MDC.put("endpoint", joinPoint.getSignature().toString());
        logger.info(TECHNICAL, " Service {} ({})", joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            if (result != null) {
                logger.info(TECHNICAL, " -> Result: {}", result.toString());
            } else {
                logger.info(TECHNICAL, " -> Done");
            }
            logger.info(TECHNICAL, "");
            return result;
        } catch (Throwable e) {
            logger.error(TECHNICAL, "  => " + e);
            e.printStackTrace();
            throw e;
        }
    }
}