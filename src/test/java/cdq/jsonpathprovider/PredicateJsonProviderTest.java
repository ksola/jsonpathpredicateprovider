package cdq.jsonpathprovider;

import org.junit.Test;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;

public class PredicateJsonProviderTest
{
    @Test
    public void shouldFillProperty() throws Exception
    {
        // given
        JsonPath jsonPath = JsonPath.compile("$.name.value");
        Configuration configuration = Configuration.defaultConfiguration();
        Object parse = configuration.jsonProvider().parse("{\n"
                + "    \"name\": \n"
                + "    {\n"
                + "        \"value\": \"name to change\"\n"
                + "    }\n"
                + "}");

        // when
        jsonPath.set(parse, "new value", configuration);

        // then
        System.out.println(JsonPath.parse(parse).jsonString());
    }

    @Test(expected = PathNotFoundException.class)
    public void shouldNotFillPropertyWithException() throws Exception
    {
        // given
        JsonPath jsonPath = JsonPath.compile("$.name.value");
        Configuration configuration = Configuration.defaultConfiguration();
        Object parse = configuration.jsonProvider().parse("{}");

        // when
        jsonPath.set(parse, "new value", configuration);

        // then
    }

    @Test()
    public void shouldNotFillPropertyWithoutException() throws Exception
    {
        // given
        JsonPath jsonPath = JsonPath.compile("$.name.value");
        Configuration configuration = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);
        Object parse = configuration.jsonProvider().parse("{}");

        // when
        jsonPath.set(parse, "new value", configuration);

        // then
        System.out.println(JsonPath.parse(parse).jsonString());
    }

    @Test
    public void shouldFillPropertyWithoutException() throws Exception
    {
        // given
        JsonPath jsonPath = JsonPath.compile("$.name.value");
        Configuration configuration = Configuration.defaultConfiguration()
                .jsonProvider(new PredicateJsonProvider());
        PredicateObject predicateObject = new PredicateObject();

        // when
        jsonPath.set(predicateObject, "new value", configuration);

        // then
        System.out.println(JsonPath.parse(predicateObject.toJsonMap()).jsonString());
    }

}