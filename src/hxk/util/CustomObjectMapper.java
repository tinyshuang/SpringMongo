package hxk.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @description 解决SpringMVC使用@ResponseBody返回json时，日期格式默认显示为时间戳的问题。需配合<mvc:message-converters>使用  
 *2015-5-25  上午10:45:54
 */
@Component("customObjectMapper")  
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {  
        CustomSerializerFactory factory = new CustomSerializerFactory();  
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {  
            @Override  
            public void serialize(Date value, JsonGenerator jsonGenerator,SerializerProvider provider) throws IOException, JsonProcessingException {  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                jsonGenerator.writeString(sdf.format(value));  
            }  
        });  
        this.setSerializerFactory(factory);  
    }  
}
