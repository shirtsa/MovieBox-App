package bg.moviebox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  /* The class implements WebMvcConfigurer, an interface provided by Spring
  that allows customization of MVC (Model-View-Controller) configurations in a Spring Boot application.
  By implementing this interface, WebConfig can customize various aspects of the application's MVC setup,
  including interceptors, resource handling, view resolution, and more. */

  /* The class takes a LocaleChangeInterceptor as a dependency via its constructor.
  This interceptor is responsible for intercepting incoming HTTP requests to check
  if there is a locale change request (usually specified via a URL parameter, like ?lang=en).
  It changes the locale for the current user session based on the parameter value,
  allowing the application to dynamically switch languages. */
  private final LocaleChangeInterceptor localeChangeInterceptor;

  public WebConfig(LocaleChangeInterceptor localeChangeInterceptor) {
    this.localeChangeInterceptor = localeChangeInterceptor;
  }

  /* The addInterceptors method is overridden from WebMvcConfigurer.
  Here, the LocaleChangeInterceptor is added to the InterceptorRegistry.
  This means the interceptor will be executed for each HTTP request
  to check for any locale change parameters. */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor);
  }
}
