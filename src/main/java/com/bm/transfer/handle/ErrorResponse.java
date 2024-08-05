package com.bm.transfer.handle;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
