package com.example.springdemo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 接收LocalDate和LocalDateTime类型
 * 将返回的LocalDate和LocalDateTime类型转换成时间戳
 * @Author:苏征
 * @Date: 2020/1/15
 */
@Configuration
public class LocalDateTimeConfig {

    /**
     * 默认日期格式
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 默认日期时间格式
     */
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * SpringMVC转换器
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
            }
        };
    }

    /**
     * SpringMVC转换器
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
            }
        };
    }

    /**
     * SpringMVC转换器
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (source.length() > 10) {
                    return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
                } else {
                    return LocalDate.parse(source).atStartOfDay();
                }
            }
        };
    }

    /**
     * 驼峰json
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        mapper.registerModule(timeModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * 下划线json
     * @return
     */
    @Bean
    public ObjectMapper jsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        mapper.registerModule(timeModule);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * 时间戳类型和字符串类型反序列化成LocalDateTime类型
     */
    static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken.isNumeric()) {  //时间戳
                long value = jsonParser.getLongValue();
                if (value > Integer.MAX_VALUE) {  //适配秒和毫秒时间戳
                    value = value / 1000;
                }
                return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.ofHours(8));
            } else { //字符串
                return LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
            }
        }
    }

    static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(time(localDateTime));
        }
    }

    static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(time(localDate.atStartOfDay()));
        }
    }

    /**
     * LocalDateTime类型序列化转时间戳
     * @param localDateTime
     * @return
     */
    private static long time(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

}
