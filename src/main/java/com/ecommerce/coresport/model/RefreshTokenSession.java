package com.ecommerce.coresport.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenSession implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    String refreshToken;
    String userAgent;
    String ipAddress;
    Instant createdAt;
}
