# Json Path Predicate Provider

## What is this project about? 

This small library allows setting values in a json with jsonpath and also fill missing properties in the path.

## Detailed problem description
Json path is able to set values to json objects when the properties are filled e.g: we have following json:

```
{
    "name": 
    {
        "value": "name to change"
    }
}
```
Then with JsonPath we can change the value with following code

```
JsonPath jsonPath = JsonPath.compile("$.name.value");
Configuration configuration = Configuration.defaultConfiguration();
Object parse = configuration.jsonProvider().parse("{\n"
                + "    \"name\": \n"
                + "    {\n"
                + "        \"value\": \"name to change\"\n"
                + "    }\n"
                + "}");

jsonPath.set(parse, "new value", configuration);
```

The result of this code will be following json: 

```
{
    "name": 
    {
        "value": "new value"
    }
}
```

The problem occur when in the json the object `name` is missing e.g.:

```
JsonPath jsonPath = JsonPath.compile("$.name.value");
Configuration configuration = Configuration.defaultConfiguration();
Object parse = configuration.jsonProvider().parse("{}");

jsonPath.set(parse, "new value", configuration);
```

In such case an exception will be thrown, that the property does not exist.

With the `PredicateJsonProvider` it is possible to fill the missing inner object. 

```
JsonPath jsonPath = JsonPath.compile("$.name.value");

Configuration configuration = Configuration
                                .defaultConfiguration()
                                .jsonProvider(new PredicateJsonProvider());
PredicateObject parse = new PredicateObject();

jsonPath.set(parse, "new value", configuration);

JsonPath.parse(predicateObject.toJsonMap()).jsonString(); // get back the result

```
