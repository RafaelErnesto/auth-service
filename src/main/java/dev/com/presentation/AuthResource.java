package dev.com.presentation;

import dev.com.application.services.CreateUserService;
import dev.com.application.usecases.*;
import dev.com.application.usecases.dtos.CreateUserRequestDto;
import dev.com.application.usecases.dtos.LoginRequestDto;
import dev.com.application.usecases.dtos.LoginResponseDto;

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

    @Inject
    CreateUserService createUserService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponseDto login(@Valid LoginRequestDto loginRequest) {
       return loginUseCase.execute(loginRequest);
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void create(@Valid CreateUserRequestDto request) {
        createUserService.execute(request.toUser());
    }
}