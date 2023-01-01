package dev.com.common.exception.mappers;

import dev.com.common.exception.customexceptions.UserExistsException;
import dev.com.infrastructure.ResponsePayload;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExistsExceptionMapper implements ExceptionMapper<UserExistsException> {
    @Override
    public Response toResponse(UserExistsException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ResponsePayload(e.getMessage()))
                .build();
    }
}
