package br.com.guilhermebarros.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class CopyNonNullProperties {
    public static void execute(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertiesNames(source));
    }

    private static String[] getNullPropertiesNames(Object source) {
        final BeanWrapper BEAN_WRAPPER = new BeanWrapperImpl(source);

        PropertyDescriptor[] properties = BEAN_WRAPPER.getPropertyDescriptors();

        Set<String> nullPropertiesNames = new HashSet<>();

        for (PropertyDescriptor property : properties) {
            var propertyValue = BEAN_WRAPPER.getPropertyValue(property.getName());

            if (propertyValue == null) {
                nullPropertiesNames.add(property.getName());
            }
        }

        String[] result = new String[nullPropertiesNames.size()];
        return nullPropertiesNames.toArray(result);
    }
}
