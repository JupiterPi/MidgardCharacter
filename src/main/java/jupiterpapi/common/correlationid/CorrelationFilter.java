package jupiterpapi.common.correlationid;

import org.slf4j.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@Configuration
public class CorrelationFilter {
	@Bean
	public FilterRegistrationBean<Filter> registerFilter() {
		FilterRegistrationBean<Filter> reg = new FilterRegistrationBean<>();
		reg.setFilter(someFilter());
		reg.addUrlPatterns("*");
		reg.addInitParameter(CorrelationContext.CORRELATION_ID, "Anonymous");
		reg.setName("CorrelationFilter");
		reg.setOrder(1);
		return reg;
	}

	private javax.servlet.Filter someFilter() {
		return new IDFilter();
	}

	public static class IDFilter implements javax.servlet.Filter {
		private final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
		private final Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
				throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) req;

			String id = request.getHeader(CorrelationContext.CORRELATION_ID);
			if (id == null || id.equals("")) {
				id = request.getRequestURI();
				CorrelationContext.determineCorrelationID(id);
				id = CorrelationContext.getCorrelationID();
			}
			MDC.put(CorrelationContext.CORRELATION_ID, id);
			CorrelationContext.setCorrelationID(id);
			logger.info(TECHNICAL, "Correlation ID: {}", id);

			chain.doFilter(request, res);
		}

		@Override
		public void init(FilterConfig arg0) {
		}

		@Override
		public void destroy() {
		}
	}
}
