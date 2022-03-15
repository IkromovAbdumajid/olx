package com.comapny.controller;

import com.comapny.dto.auth.AuthorizationDTO;
import com.comapny.dto.auth.RegistrationDTO;
import com.comapny.dto.profile.ProfileDTO;
import com.comapny.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login", notes = "Some Noted of ")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error", response = ProfileDTO.class),
            @ApiResponse(code = 404, message = "Service not found", response = ProfileDTO.class),
            @ApiResponse(code = 200, message = "Successful retrieval", response = ProfileDTO.class, responseContainer = "List")}
    )
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authService.authorization(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Registration", notes = "Some Noted of ")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error", response = ProfileDTO.class),
            @ApiResponse(code = 404, message = "Service not found", response = ProfileDTO.class),
            @ApiResponse(code = 200, message = "Successful retrieval", response = ProfileDTO.class, responseContainer = "List")}
    )
    @PostMapping("/registration")
    public ResponseEntity<ProfileDTO> registration(@Valid @RequestBody RegistrationDTO dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    @ApiOperation(value = "Register verification")
    public ResponseEntity<ProfileDTO> verification(@PathVariable("jwt") String jwt) {
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
