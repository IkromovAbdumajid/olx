package com.comapny.controller;


import com.comapny.dto.profile.ProfileDTO;
import com.comapny.dto.profile.ProfileJwtDTO;
import com.comapny.dto.profile.ProfilefilterDTO;
import com.comapny.entity.ProfileEntity;
import com.comapny.enums.ProfileRole;
import com.comapny.service.ProfileService;
import com.comapny.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "Create profile", notes = "Some Noted of ")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error", response = ProfileDTO.class),
            @ApiResponse(code = 404, message = "Service not found", response = ProfileDTO.class),
            @ApiResponse(code = 200, message = "Successful retrieval", response = ProfileDTO.class, responseContainer = "List")}
    )
    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid  @RequestBody ProfileDTO dto, HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-id/{email}")
    public ResponseEntity<?> getProfileById (@Valid @PathVariable("email") String email, HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO response = profileService.getByProfileDTOByEmail(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-ALL")
    public ResponseEntity<?> getAllProfile (HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<ProfileDTO> profileDTOList = profileService.getAllProfile();
        return ResponseEntity.ok(profileDTOList);
    }

    @DeleteMapping("/delete-by-id/{email}")
    public ResponseEntity<?> deleteProfileById (@Valid @PathVariable("email") String email, HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        String response = profileService.deleteProfileByEmail(email);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/change-name-surname/{email}")
    public ResponseEntity<?> changeProfileByEmail(@Valid @PathVariable("email") String email,@Valid @RequestParam("name") String name,
                                                  @Valid @RequestParam("surname") String surname, HttpServletRequest request) {
        ProfileJwtDTO profileJwtDTO = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO profileDTO = profileService.changeProfileByEmail(email, name, surname);
        return  ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/get/size/page")
    public ResponseEntity<?> get(@PathVariable("size") Integer size, @PathVariable("page") Integer page, @RequestBody ProfilefilterDTO dto) {
        PageImpl<ProfileEntity> response = profileService.filterSpe(page,size,dto);
        return ResponseEntity.ok(response);
    }
}
