package com.travelapp.services.users;

import com.travelapp.dto.users.UserBaseDto;

import java.util.Set;

public interface IUserBaseService {

    UserBaseDto createUserBase(UserBaseDto userBaseDto);

    UserBaseDto updateUserBase(Long id, UserBaseDto userBaseDto);

    Set<UserBaseDto> getAllUserBases();

    UserBaseDto getUserBaseById(Long id);

    void deleteUserBase(Long id);
}
