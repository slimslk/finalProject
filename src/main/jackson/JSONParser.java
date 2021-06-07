package main.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JSONParser {
    public String generateJSONFromList(List<?> list) {
        String jsonString="";
        ObjectMapper objMapper = new ObjectMapper();
        try {
            jsonString=objMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
