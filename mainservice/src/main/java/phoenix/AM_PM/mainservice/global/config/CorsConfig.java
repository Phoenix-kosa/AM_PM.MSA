package phoenix.AM_PM.mainservice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOrigin("http://localhost:5173"); // e.g. http://domain1.com
      config.addAllowedOrigin("http://localhost:8001");
      config.addAllowedOrigin("http://192.168.3.84:5173");
      config.addAllowedOrigin("http://ampm.com:5173");
      config.addAllowedHeader("*");
      config.addExposedHeader("Authorization");
      config.addExposedHeader("RefreshToken");
      config.addAllowedMethod("*");

      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }

}
