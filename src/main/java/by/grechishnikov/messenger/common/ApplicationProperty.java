package by.grechishnikov.messenger.common;

import java.util.ResourceBundle;

/**
 * @author - Evgeniy Grechishnikov
 */
public class ApplicationProperty {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    public static String getStringProperty(String name) {
        return resourceBundle.getString(name);
    }

    public static int getIntegerProperty(String name) {
        return Integer.parseInt(resourceBundle.getString(name));
    }

}