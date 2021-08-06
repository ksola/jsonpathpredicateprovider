package cdq.jsonpathprovider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PredicateObject
{
    private Map<String, Object> map;
    private List<Object> array;
    private Object leaf;

    public Map<String, Object> getMap()
    {
        return map;
    }

    public void setMap(Map<String, Object> map)
    {
        this.map = map;
    }

    public List<Object> getArray()
    {
        return array;
    }

    public void setArray(List<Object> array)
    {
        this.array = array;
    }

    public Object getLeaf()
    {
        return leaf;
    }

    public void setLeaf(Object leaf)
    {
        this.leaf = leaf;
    }

    @Override
    public String toString()
    {
        return "PredicateObject{" +
                ((map != null) ? "map=" + map : "") +
                ((array != null) ? "array=" + array : "") +
                ((leaf != null) ? "leaf=" + leaf : "") +
                '}';
    }

    public Object toJsonMap()
    {
        if (map != null)
        {
            Map<String, Object> result = new LinkedHashMap<>();
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet())
            {
                Object value = stringObjectEntry.getValue();
                if (value instanceof PredicateObject)
                {
                    result.put(stringObjectEntry.getKey(), ((PredicateObject) value).toJsonMap());
                }
            }
            return result;
        }
        else if (array != null)
        {
            List<Object> result = new ArrayList<>();
            for (Object o : array)
            {
                if (o instanceof PredicateObject)
                {
                    result.add(((PredicateObject) o).toJsonMap());
                }
            }
            return result;
        }
        else
        {
            return leaf;
        }

    }

}
