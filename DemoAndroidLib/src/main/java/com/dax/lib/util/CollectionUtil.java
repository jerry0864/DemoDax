package com.dax.lib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtil {
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     *
     * @param coll the collection to check, may be null
     * @return true if empty or null
     * @since Commons Collections 3.2
     */
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static <E, T> List<T> transfer(Collection<E> list, ItemTransfer<E, T> transfer) {
        List<T> newList = new ArrayList<>();
        if (isEmpty(list)) {
            return newList;
        }
        for (E e : list) {
            newList.add(transfer.transfer(e));
        }
        return newList;
    }

    public static <T> List<T> filter(Collection<T> list, ItemFilter<T> filter) {
        List<T> newList = new ArrayList<>();
        if (isEmpty(list)) {
            return newList;
        }
        for (T t : list) {
            if (filter.filter(t)) {
                newList.add(t);
            }
        }
        return newList;
    }

    public static <T> void delete(Collection<T> list, ItemFilter<T> filter) {
        List<T> tempList = new ArrayList<>();
        if (isEmpty(list)) {
            return;
        }
        for (T t : list) {
            if (filter.filter(t)) {
                tempList.add(t);
            }
        }
        if (tempList.size() > 0) {
            list.removeAll(tempList);
        }
    }

    public static <T> int size(Collection<T> collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> boolean compare(Collection<T> collection, Collection<T> collection2) {
        if (isEmpty(collection) && isEmpty(collection2)) {
            return true;
        } else if (size(collection) != size(collection2)) {
            return false;
        }
        List<T> list1 = new ArrayList<>(collection);
        List<T> list2 = new ArrayList<>(collection2);
        for (T t : list1) {
            if (!list2.remove(t)) {
                return false;
            }
        }
        return true;
    }

    public interface ItemFilter<T> {
        boolean filter(T t);
    }

    public interface ItemTransfer<E, T> {
        T transfer(E data);
    }

    public interface StringTransfer<T> extends ItemTransfer<T, String> {
        String transfer(T data);
    }
}
