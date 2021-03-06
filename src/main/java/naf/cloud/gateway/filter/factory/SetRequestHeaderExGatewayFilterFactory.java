package naf.cloud.gateway.filter.factory;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriTemplate;
import org.springframework.web.util.pattern.PathPattern.PathMatchInfo;

/**
 * @author dyg
 */
public class SetRequestHeaderExGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

	public static final String TEMPLATE_KEY = "template";

	@Override
	public GatewayFilter apply(NameValueConfig config) {
		return (exchange, chain) -> {
			UriTemplate uriTemplate = new UriTemplate(config.getValue());

			PathMatchInfo variables = exchange.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			Map<String, String> uriVariables;

			if (variables != null) {
				uriVariables = variables.getUriVariables();
			} else {
				uriVariables = Collections.emptyMap();
			}

			URI uri = uriTemplate.expand(uriVariables);
			String newPath = uri.getRawPath();

			ServerHttpRequest request = exchange.getRequest().mutate()
					.headers(httpHeaders -> {
						if(newPath != null) httpHeaders.set(config.getName(), newPath);
					}).build();

			return chain.filter(exchange.mutate().request(request).build());
		};
		
	}
}
