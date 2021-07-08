package com.phoosop.springbootmultidatasource.component;

import com.phoosop.springbootmultidatasource.model.command.UserCommand;
import com.phoosop.springbootmultidatasource.model.enums.UserLevel;
import com.phoosop.springbootmultidatasource.model.request.CreateUserRequest;
import com.phoosop.springbootmultidatasource.service.UserPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserComponent {

    private final UserPersistenceService userPersistenceService;
    private final ConversionService conversionService;

    public UserCommand createUser(CreateUserRequest request) {
        UserCommand userCommand = conversionService.convert(request, UserCommand.class);
        userCommand.setLevel(UserLevel.LEVEL_1);
        userPersistenceService.save(userCommand);
        return userCommand;
    }

    public List<UserCommand> getUserList() {
        return userPersistenceService.findAll();
    }

}
