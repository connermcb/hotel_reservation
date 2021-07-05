package model;

import java.util.List;
import java.lang.String;

public enum RoomType {
    SINGLE,
    DOUBLE;

//    Code pattern for checking if value in RoomType Enum adapted from:
//    https://www.linuxcommands.site/java/java-tutorial-java-enum-check-if-value-exists/
    public static Boolean isValid(String userInput) {
        if (userInput.isEmpty()) {
            return false;
        }
        for (RoomType each : RoomType.values()) {
            if (userInput.equals(each.name())) {
                return true;
            }
        }
        return false;
    }
}
