package org.example.util;

import org.example.exception.InvalidParameterException;

import java.util.UUID;

public class ValidationUtil {

    public static void validate(String playerOneName, String playerTwoName) {

        if (isParameterMissed(playerOneName)) {
            throw new InvalidParameterException("Invalid request: Missing Player one name");
        }
        if (isParameterMissed(playerTwoName)) {
            throw new InvalidParameterException("Invalid request: Missing Player two name");
        }
        if (isNameInvalid(playerOneName) && isNameInvalid(playerTwoName)) {
            throw new InvalidParameterException("Invalid players names. Name: up to 20 characters - Latin letters or numbers");
        }
        if (isNameInvalid(playerOneName)) {
            throw new InvalidParameterException("Invalid Player one name. Name: up to 20 characters - Latin letters or numbers");
        }
        if (isNameInvalid(playerTwoName)) {
            throw new InvalidParameterException("Invalid Player two name. Name: up to 20 characters - Latin letters or numbers");
        }
        if (playerOneName.equals(playerTwoName)) {
            throw new InvalidParameterException("Invalid request: Player names must be different");
        }
    }
    public static int getValidPageNumber(String pageParam) {
        int page = 1;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }
        return Math.max(page, 1);
    }

    public static UUID getValidUuid(String uuidStr) {
        if (uuidStr == null || uuidStr.isBlank()) {
            throw new IllegalArgumentException("Missing or empty uuid parameter");
        }
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
    }

    public static Long getValidPointWinnerId(String idStr) {
        if (idStr == null || idStr.isBlank()) {
            throw new IllegalArgumentException("Missing or empty player_id parameter");
        }
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid player_id format");
        }
    }

    private static boolean isNameInvalid(String name) {
        return name.length() > 20 || !name.matches("[a-zA-Z0-9 ]+");
    }
    private static boolean isParameterMissed(String parameter) {
        return parameter == null || parameter.isBlank();
    }


}

