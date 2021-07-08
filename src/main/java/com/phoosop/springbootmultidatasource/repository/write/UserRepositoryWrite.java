package com.phoosop.springbootmultidatasource.repository.write;

import com.phoosop.springbootmultidatasource.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryWrite extends JpaRepository<UserEntity, Long> {
}
