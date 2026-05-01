package ro.mycode.system.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiResponse(
        int status,
        String message



) {
}
