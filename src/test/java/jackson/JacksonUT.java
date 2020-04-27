package jackson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.the.mild.project.server.jackson.JacksonTest;
import com.the.mild.project.server.jackson.ParamTest;
import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.TodoJson;

public class JacksonUT {

    @Test
    public void unmarshal() throws JsonProcessingException {
        final String username = "user";
        final String message = "message";
        final boolean completed = false;
        final String original = String.format("{\"username\":\"%s\",\"message\":\"%s\",\"completed\":%b}",
                                              username, message, completed);

        TodoJson expected = new TodoJson(username, message, completed);
        TodoJson result = JacksonHandler.unmarshal(original, TodoJson.class);


        assertEquals(String.format("\"%s\" did not match expected \"%s\"", result, expected), expected, result);
    }

    @Test
    public void jacksonTest() {
        final String key = "test";
        final String value = "resource";
        final String expected = String.format("{\"key\":\"%s\",\"value\":\"%s\"}", key, value);

        final JacksonTest test = new JacksonTest(key, value);
        final String result = JacksonHandler.stringify(test);

        assertEquals(String.format("\"%s\" did not match expected \"%s\"", result, expected), result, expected);
    }

    @Test
    public void paramTest() {
        final String id = "1";
        final String expected = String.format("{\"id\":\"%s\"}", id);

        final ParamTest test = new ParamTest(id);
        final String result = JacksonHandler.stringify(test);

        assertEquals(String.format("\"%s\" did not match expected \"%s\"", result, expected), result, expected);
    }
}
