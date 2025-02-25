package earlybird.earlybird.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class GlobalExceptionHandlerAspect {

    @Before(value = "execution(* earlybird.earlybird.error.GlobalExceptionHandler.*(..)) && args(e, request)", argNames = "joinPoint,e,request")
    public void setMDCBeforeExceptionHandler(JoinPoint joinPoint, Exception e, HttpServletRequest request) {
        setCommonMDC(request, e);
    }

    private void setCommonMDC(HttpServletRequest request, Exception e) {
        MDC.put("exception-type", e.getClass().getSimpleName());
        MDC.put("exception-message", e.getMessage());
        MDC.put("request-uri", request.getRequestURI());
    }
}
