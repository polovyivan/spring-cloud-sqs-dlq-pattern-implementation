package com.polovyi.ivan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String email;

    private String fullName;

    private String phoneNumber;

}
