import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectUtils {
    public static <T> Map<Object, T> convertListToMap(List<T> list, String keyFieldName) throws NoSuchFieldException, IllegalAccessException {
        Map<Object, T> map = new HashMap<>();
        if (list.isEmpty()) {
            return map;
        }

        Field keyField = list.get(0).getClass().getDeclaredField(keyFieldName);
        keyField.setAccessible(true);

        for (T item : list) {
            Object key = keyField.get(item);
            map.put(key, item);
        }

        return map;
    }
}
