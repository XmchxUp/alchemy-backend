package io.github.xmchxup.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xmchxup.backend.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Convert
public class ListAndJson implements AttributeConverter<List<Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            return mapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> convertToEntityAttribute(String s) {
        try {
            if (null == s) {
                return null;
            }
            return mapper.readValue(s, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}