package org.example.util;

import java.util.UUID;

public class ValidationUtil {
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
}
