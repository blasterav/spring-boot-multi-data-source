package com.phoosop.springbootmultidatasource.service;

import com.phoosop.springbootmultidatasource.model.command.UserCommand;
import com.phoosop.springbootmultidatasource.model.entity.UserEntity;
import com.phoosop.springbootmultidatasource.repository.read.UserRepositoryRead;
import com.phoosop.springbootmultidatasource.repository.write.UserRepositoryWrite;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPersistenceService {

    private final UserRepositoryRead userRepositoryRead;
    private final UserRepositoryWrite userRepositoryWrite;
    private final ConversionService conversionService;

    public List<UserCommand> findAll() {
        return userRepositoryRead.findAll()
                .stream()
                .map(item -> conversionService.convert(item, UserCommand.class))
                .collect(Collectors.toList());
    }

    public UserCommand save(UserCommand userCommand) {
        UserEntity userEntity = conversionService.convert(userCommand, UserEntity.class);
        userRepositoryWrite.save(userEntity);
        userCommand.setId(userEntity.getId());
        return userCommand;
    }

}
