package com.comapny.dto.profile;

import com.comapny.enums.ProfileRole;
import com.comapny.enums.ProfileStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class ProfilefilterDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Email
    private String email;
    @NotEmpty
    private ProfileRole role;
    @NotBlank
    private ProfileStatus status;
    @NotEmpty
    private Integer profileId;
    private LocalDate fromDate;
    private LocalDate toDate;
}
