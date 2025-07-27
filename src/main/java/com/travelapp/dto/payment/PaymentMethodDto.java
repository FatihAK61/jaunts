package com.travelapp.dto.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMethodDto {
    private Long id;

    private Integer expiryMonth;
    private Integer expiryYear;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Boolean isDefault;
    private Boolean active;

    private String accountNumber;
    private String bankName;
    private String cardBrand;
    private String cardHolderName;
    private String cardToken;
    private String lastFourDigits;
    private String type;

    private Long userId;
}
