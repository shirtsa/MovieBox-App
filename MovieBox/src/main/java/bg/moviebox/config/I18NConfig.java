package bg.moviebox.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class I18NConfig {

  //LocalResolver has two functions
  //1.To remember the current locale and change it as need it
  //2.To resolve locale from http request that comes
  //and its notice by the LocaleChangeInterceptor
  @Bean
  public LocaleResolver localeResolver() {
    return new CookieLocaleResolver("lang");
  }

  //Listen for http requests, when param with name lang shows up, it's changing the current locale
  //and notify the localResolver to change it as well
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }


  //MessageSource shows us the Path to the messages in the i18n folder
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:i18n/messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setDefaultLocale(Locale.ENGLISH);
    return messageSource;
  }
}
