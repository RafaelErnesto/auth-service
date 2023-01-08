package dev.com.presentation.dtos;

import javax.validation.constraints.NotNull;

public class ConfirmUserCreationRequestDto {
    @NotNull
    public String hash;
}
