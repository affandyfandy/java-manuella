import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ObjectUtils {
    public static <T> void sortByField(List<T> list, String fieldName) throws NoSuchFieldException {
        Field field = list.get(0).getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                try {
                    Comparable value1 = (Comparable) field.get(o1);
                    Comparable value2 = (Comparable) field.get(o2);
                    return value1.compareTo(value2);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static <T> T findMaxByField(List<T> list, String fieldName) throws NoSuchFieldException {
        Field field = list.get(0).getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return Collections.max(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                try {
                    Comparable value1 = (Comparable) field.get(o1);
                    Comparable value2 = (Comparable) field.get(o2);
                    return value1.compareTo(value2);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
