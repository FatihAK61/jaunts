package com.travelapp.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelapp.helper.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingParticipantDto {
    
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate passportExpiryDate;

    private String dietaryRequirements;
    private String email;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String firstName;
    private String lastName;
    private String medicalConditions;
    private String nationality;
    private String passportNumber;
    private String phoneNumber;

    private Gender gender;

    private Long bookingId;
}
