package com.phoosop.springbootmultidatasource.controller;

import com.phoosop.springbootmultidatasource.component.UserComponent;
import com.phoosop.springbootmultidatasource.model.Response;
import com.phoosop.springbootmultidatasource.model.command.UserCommand;
import com.phoosop.springbootmultidatasource.model.request.CreateUserRequest;
import com.phoosop.springbootmultidatasource.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController implements BaseController {

    private final UserComponent userComponent;
    private final ConversionService conversionService;

    @PostMapping(path = "/v1/users")
    public Response<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserCommand userCommand = userComponent.createUser(request);
        UserResponse response = conversionService.convert(userCommand, UserResponse.class);
        return success(response);
    }

    @GetMapping(path = "/v1/users")
    public Response<List<UserResponse>> getUserList() {
        List<UserResponse> userShortResponseList = userComponent.getUserList()
                .stream()
                .map(item -> conversionService.convert(item, UserResponse.class))
                .collect(Collectors.toList());
        return success(userShortResponseList);
    }

}
