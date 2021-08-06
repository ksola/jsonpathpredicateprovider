package cdq.jsonpathprovider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonProvider;

public class PredicateJsonProvider implements JsonProvider
{
    @Override
    public boolean isArray(Object obj)
    {
        return obj instanceof PredicateObject;
    }

    @Override
    public int length(Object obj)
    {
        if (obj instanceof PredicateObject)
        {
            PredicateObject predicateObject = (PredicateObject) obj;
            if (predicateObject.getArray() != null)
            {
                return predicateObject.getArray().size();
            }
            else if (predicateObject.getMap() != null)
            {
                return predicateObject.getMap().size();
            }
        }
        return 0;
    }

    @Override
    public Iterable<?> toIterable(Object obj)
    {
        return null;
    }

    @Override
    public Collection<String> getPropertyKeys(Object obj)
    {
        throw new UnsupportedOperationException("Wildcards not supported in json paths by setting values");
    }

    @Override
    public boolean isMap(Object obj)
    {
        return obj instanceof PredicateObject;
    }

    @Override
    public Object unwrap(Object obj)
    {
        return null;
    }

    @Override
    public Object getMapValue(Object obj, String key)
    {
        PredicateObject predicateObject = (PredicateObject) obj;
        if (predicateObject.getMap() == null)
        {
            predicateObject.setMap(new HashMap<>());
        }
        predicateObject.getMap().putIfAbsent(key, new PredicateObject());
        return predicateObject.getMap().get(key);
    }

    @Override
    public Object getArrayIndex(Object obj, int idx)
    {
        PredicateObject predicateObject = (PredicateObject) obj;
        if (predicateObject.getArray() == null)
        {
            predicateObject.setArray(new ArrayList<>());
        }
        for (int i = 0; i <= idx; i++)
        {
            if (predicateObject.getArray().size() <= i)
            {
                predicateObject.getArray().add(new PredicateObject());
            }
        }
        return predicateObject.getArray().get(idx);
    }

    @Override
    public Object getArrayIndex(Object obj, int idx, boolean unwrap)
    {
        if (obj instanceof PredicateObject)
        {
            PredicateObject predicateObject = (PredicateObject) obj;
            return Optional.ofNullable(predicateObject.getArray())
                    .map(array -> array.get(idx));
        }

        return null;
    }

    @Override
    public Object parse(String json)
    {
        return new PredicateObject();
    }

    @Override
    public Object parse(InputStream jsonStream, String charset) throws InvalidJsonException
    {
        return null;
    }

    @Override
    public String toJson(Object obj)
    {
        if (obj instanceof PredicateObject)
        {
            PredicateObject predicateObject = (PredicateObject) obj;
            return JsonPath.parse(predicateObject.toJsonMap()).jsonString();
        }
        return null;
    }

    @Override
    public Object createArray()
    {
        return new ArrayList<>();
    }

    @Override
    public Object createMap()
    {
        return new HashMap<>();
    }

    @Override
    public void setArrayIndex(Object array, int index, Object newValue)
    {
        if (array instanceof PredicateObject)
        {
            ((PredicateObject) array).getArray().set(index, newValue);
        }
    }

    @Override
    public void setProperty(Object obj, Object key, Object value)
    {
        if (obj instanceof PredicateObject && ((PredicateObject) obj).getMap() != null)
        {
            @SuppressWarnings("SuspiciousMethodCalls") Object predicateObject = ((PredicateObject) obj).getMap().get(key);
            if (predicateObject instanceof PredicateObject)
            {
                ((PredicateObject) predicateObject).setLeaf(value);
            }
        }
    }

    @Override
    public void removeProperty(Object obj, Object key)
    {
        // not used
    }
}
