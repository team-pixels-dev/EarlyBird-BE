package earlybird.earlybird.log;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class MdcLogTraceFilter implements Filter {
    private static final String TRACE_ID_KEY = "traceId";
    private static final String REQUEST_URI_KEY = "requestUri";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            MDC.put(TRACE_ID_KEY, UUID.randomUUID().toString());

            if (request instanceof HttpServletRequest httpRequest) {
                MDC.put(REQUEST_URI_KEY, httpRequest.getRequestURI());
            }

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
