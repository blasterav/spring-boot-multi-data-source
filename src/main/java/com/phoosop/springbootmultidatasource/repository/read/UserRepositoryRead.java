package com.phoosop.springbootmultidatasource.repository.read;

import com.phoosop.springbootmultidatasource.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryRead extends JpaRepository<UserEntity, Long> {
}
