package com.workintech.s19d2.controller;

import com.workintech.s19d2.AuthenticationService;
import com.workintech.s19d2.repository.RegisterResponse;
import com.workintech.s19d2.repository.RegistrationMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegistrationMember request){

        var member = authenticationService.register(
                request.email(),
                request.password()
        );

        return new RegisterResponse(
                member.getEmail(),
                "kayıt başarılı bir şekilde gerçekleşti."
        );
    }
}
