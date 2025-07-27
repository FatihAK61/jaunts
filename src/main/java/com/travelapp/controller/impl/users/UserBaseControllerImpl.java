package com.travelapp.controller.impl.users;

import com.travelapp.controller.users.IUserBaseController;
import com.travelapp.dto.users.UserBaseDto;
import com.travelapp.services.users.IUserBaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserBaseControllerImpl implements IUserBaseController {

    private final IUserBaseService userBaseService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<UserBaseDto> createUserBase(@RequestBody @Valid @NotNull UserBaseDto userBaseDto) {
        UserBaseDto createdUserBase = userBaseService.createUserBase(userBaseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserBase);
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
