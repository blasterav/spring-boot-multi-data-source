package com.phoosop.springbootmultidatasource.converter;


import com.phoosop.springbootmultidatasource.exception.ServiceException;
import com.phoosop.springbootmultidatasource.model.command.UserCommand;
import com.phoosop.springbootmultidatasource.model.enums.UserLevel;
import com.phoosop.springbootmultidatasource.model.enums.UserStatus;
import com.phoosop.springbootmultidatasource.model.enums.UserType;
import com.phoosop.springbootmultidatasource.model.request.CreateUserRequest;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import static com.phoosop.springbootmultidatasource.model.StatusConstants.HttpConstants;


@Mapper(componentModel = "spring")
public abstract class CreateUserRequestToUserCommandConverter implements Converter<CreateUserRequest, UserCommand> {

    public abstract UserCommand convert(CreateUserRequest source);

    public UserStatus intToUserStatus(Integer id) {
        return UserStatus.find(id)
                .orElseThrow(() -> new ServiceException(HttpConstants.FAILED_TO_CONVERT_VALUE_TO_ENUM));
    }

    public UserType stringToUserType(String id) {
        return UserType.find(id)
                .orElseThrow(() -> new ServiceException(HttpConstants.FAILED_TO_CONVERT_VALUE_TO_ENUM));
    }

    public UserLevel intToUserLevel(Integer id) {
        return UserLevel.find(id)
                .orElseThrow(() -> new ServiceException(HttpConstants.FAILED_TO_CONVERT_VALUE_TO_ENUM));
    }

}
