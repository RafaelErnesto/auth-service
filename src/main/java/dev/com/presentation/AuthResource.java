package dev.com.presentation;

import dev.com.application.usecases.LoginResponseDto;
import dev.com.application.usecases.LoginUseCase;
import dev.com.application.usecases.LoginRequestDto;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AuthResource {

    @Inject
    LoginUseCase loginUseCase;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponseDto login(@Valid LoginRequestDto loginRequest) {
       return loginUseCase.execute(loginRequest);
    }
}