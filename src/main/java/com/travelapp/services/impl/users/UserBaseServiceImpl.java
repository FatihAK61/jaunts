package com.travelapp.services.impl.users;

import com.travelapp.dto.users.UserBaseDto;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.users.mappers.UserBaseMapperService;
import com.travelapp.services.users.IUserBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserBaseServiceImpl implements IUserBaseService {

    private final UserBaseRepository userBaseRepository;
    private final UserBaseMapperService userBaseMapperService;

    @Override
    public UserBaseDto createUserBase(UserBaseDto userBaseDto) {
        UserBase userBase = userBaseMapperService.toEntity(userBaseDto);
        UserBase savedUserBase = userBaseRepository.save(userBase);
        return userBaseMapperService.toDto(savedUserBase);
    }

    @Override
    public UserBaseDto updateUserBase(Long id, UserBaseDto userBaseDto) {
        return null;
    }

    @Override
    public Set<UserBaseDto> getAllUserBases() {
        return Set.of();
    }

    @Override
    public UserBaseDto getUserBaseById(Long id) {
        return null;
    }

    @Override
    public void deleteUserBase(Long id) {

    }
}
