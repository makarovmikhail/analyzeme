package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sergey
 */
public class JsonParserTest {
    private JsonParser jsonParser;
    private DataArray<Double> points;

    @Test(expected = NullPointerException.class)
    public void testNullArgumentInConstructor() throws Exception {
        jsonParser = new JsonParser();
        jsonParser.parse(null);
    }

    @Test
    public void testIncorrectFile() throws InvalidFileException {
        String s = join("\n", new String[]{
                "{",
                "\"x\":"
        });

        InputStream is = new ByteArrayInputStream(s.getBytes());
        jsonParser = new JsonParser();
        try {
            points = jsonParser.parse(is);
            Assert.fail();
        } catch (JsonParserException ex) {
            Assert.assertEquals(JsonParserException.ExceptionType.PARSE_FILE,
                    ex.getExType());
        }
    }


    @Test
    public void testPointsDoubleWithInteger() throws InvalidFileException {
        String s = join("\n", new String[]{
                "{\"Data\":[{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"20\",\"y\": \"20\"}]}"
        });

        InputStream is = new ByteArrayInputStream(s.getBytes());
        jsonParser = new JsonParser();
        points = jsonParser.parse(is);
        DataArray<Double> d = new DataArray<Double>();
        Map<String, Double> d1 = new HashMap<>();
        d1.put("x", 1.);
        d1.put("y", 1.);
        Map<String, Double> d2 = new HashMap<>();
        d2.put("x", 20.);
        d2.put("y", 20.);
        d.addData(new Data<Double>(d1));
        d.addData(new Data<Double>(d2));
        assertTrue(points.getByKey("x").equals(d.getByKey("x")) && points.getByKey("y").equals(d.getByKey("y")));
    }

    static public String join(String delimiter, String[] list) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : list) {
            if (first)
                first = false;
            else
                sb.append(delimiter);
            sb.append(item);
        }
        return sb.toString();
    }
}
