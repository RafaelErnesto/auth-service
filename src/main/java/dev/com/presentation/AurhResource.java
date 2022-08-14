package dev.com.presentation;

import dev.com.presentation.dtos.AddUserRequestDto;
import dev.com.application.usecases.AddUserUseCase;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AurhResource {

    @Inject
    AddUserUseCase addUserUseCase;

    @POST
    @Path("/user")
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(@Valid AddUserRequestDto addUserRequest) {
        return addUserUseCase.execute(addUserRequest);
    }
}