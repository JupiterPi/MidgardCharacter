package jupiterpapi.midgardcharacter.backend.controller;

import org.slf4j.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@Configuration
public class ControllerFilter {
	@Bean
	public FilterRegistrationBean registerFilter() {
		FilterRegistrationBean reg = new FilterRegistrationBean();
		reg.setFilter(someFilter());
		reg.addUrlPatterns("*");
		reg.addInitParameter(HttpContext.CORRELATION_ID, "Anonymous");
		reg.setName("CorrelationFilter");
		reg.setOrder(1);
		return reg;
	}

	private javax.servlet.Filter someFilter() {
		return new CorrelationFilter();
	}

	public class CorrelationFilter implements javax.servlet.Filter {
		private final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
		private final Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
				throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) req;

			String id = request.getHeader(HttpContext.CORRELATION_ID);
			if (id == "" || id == null) {
				id = request.getRequestURI();
				HttpContext.determineCorrelationID(id);
				id = HttpContext.getCorrelationID();
			}
			MDC.put(HttpContext.CORRELATION_ID, id);
			HttpContext.setCorrelationID(id);
			logger.info(TECHNICAL, "Correlation ID: {}", id);

			chain.doFilter(request, res);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
		}

		@Override
		public void destroy() {
		}
	}
}
