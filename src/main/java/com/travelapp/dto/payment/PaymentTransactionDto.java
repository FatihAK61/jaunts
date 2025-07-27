package com.travelapp.dto.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.helper.enums.TransactionStatus;
import com.travelapp.helper.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentTransactionDto {

    private Long id;

    private BigDecimal amount;

    private TransactionType type;
    private TransactionStatus status;

    private String gatewayTransactionId;
    private String description;
    private String gatewayResponse;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private Long paymentId;
}
