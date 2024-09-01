package com.fuenteadictos.fuenteadictos.dto;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class UserDTO {
    
    private String uuid;

    private String email;

    private String username;

}
