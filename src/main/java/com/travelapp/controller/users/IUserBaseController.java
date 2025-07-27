package com.travelapp.controller.users;

import com.travelapp.dto.users.UserBaseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IUserBaseController {

    ResponseEntity<UserBaseDto> createUserBase(@Valid @NotNull UserBaseDto userBaseDto);

    UserBaseDto updateUserBase(Long id, UserBaseDto userBaseDto);

    Set<UserBaseDto> getAllUserBases();

    UserBaseDto getUserBaseById(Long id);

    void deleteUserBase(Long id);
}
