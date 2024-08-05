package com.bm.transfer.dto.response;

import lombok.Builder;

@Builder
public record FavoriteGetResponse(

         String recipientName
) {
}
