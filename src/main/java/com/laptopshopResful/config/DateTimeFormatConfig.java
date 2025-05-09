// package com.laptopshopResful.config;

// import org.springframework.format.FormatterRegistry;
// import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// public class DateTimeFormatConfig implements WebMvcConfigurer {
//     @Override
//     public void addFormatters(FormatterRegistry registry) {
//         DateTimeFormatterRegistrar registar = new DateTimeFormatterRegistrar();
//         registar.setUseIsoFormat(true);
//         registar.registerFormatters(registry);
//     }
// }

package com.laptopshopResful.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

public class DateTimeFormatConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        DateTimeFormatterRegistrar registar = new DateTimeFormatterRegistrar();
        registar.setUseIsoFormat(true);
        registar.registerFormatters(registry);
    }
}
