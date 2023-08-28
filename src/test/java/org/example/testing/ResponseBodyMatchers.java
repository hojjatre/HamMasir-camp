package org.example.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;

public class ResponseBodyMatchers {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher containsObjectAsJson(
            Object expectedObject,
            Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            Assertions.assertThat(actualObject).isEqualToComparingFieldByField(expectedObject);
        };
    }

    static ResponseBodyMatchers responseBody(){
        return new ResponseBodyMatchers();
    }

}
