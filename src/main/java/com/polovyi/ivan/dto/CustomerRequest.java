package com.polovyi.ivan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotEmpty
    private String email;

    private String phoneNumber;

    private String fullName;

    private LocalDate createdAt;
}
