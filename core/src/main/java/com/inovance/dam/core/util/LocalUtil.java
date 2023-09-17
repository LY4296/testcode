package com.inovance.dam.core.util;

import com.inovance.dam.core.entity.inovance.BTProductInformation;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing BTProductInformation objects using ThreadLocal.
 * @author Leon
 */
@Component
public class LocalUtil {

    /**
     * Shared list to store BTProductInformation objects.
     */
    private static final List<BTProductInformation> list = new ArrayList<>();

    /**
     * ThreadLocal variable that stores a List of BTProductInformation objects for each thread.
     * It is initialized with an empty ArrayList.
     */
    private static final ThreadLocal<List<BTProductInformation>> threadLocal = ThreadLocal.withInitial(ArrayList::new);


    /**
     * Add a BTProductInformation object to the thread-local and shared list.
     *
     * @param btProductInformation The BTProductInformation to add.
     */
    public static void addBTProductInformation(BTProductInformation btProductInformation) {
        List<BTProductInformation> currentList = threadLocal.get();
        currentList.add(btProductInformation);
        list.add(btProductInformation);
    }

    /**
     * Get a copy of the shared list of BTProductInformation.
     *
     * @return A copy of the shared list.
     */
    public static List<BTProductInformation> getListCopy() {
        return new ArrayList<>(list);
    }

    /**
     * Clear the thread-local storage.
     */
    public static void clearBTProductInformation() {
        threadLocal.remove();
    }
}
