package com.comapny.service;

import com.comapny.dto.auth.AuthorizationDTO;
import com.comapny.dto.auth.RegistrationDTO;
import com.comapny.dto.profile.ProfileDTO;
import com.comapny.entity.ProfileEntity;
import com.comapny.enums.ProfileRole;
import com.comapny.enums.ProfileStatus;
import com.comapny.exception.BadRequestException;
import com.comapny.exception.ItemNotFoundException;
import com.comapny.repository.ProfileRepository;
import com.comapny.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;

    public ProfileDTO authorization(AuthorizationDTO dto) { // eshmat1
        String pswd = DigestUtils.md5Hex(dto.getPassword());

        Optional<ProfileEntity> optional = profileRepository
                .findByLoginAndPassword(dto.getLogin(), pswd);
        if (!optional.isPresent()) {
            throw new RuntimeException("Login or Password incorrect");
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("You are not allowed");
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(optional.get().getName());
        profileDTO.setSurname(optional.get().getSurname());
        profileDTO.setJwt(JwtUtil.createJwt(optional.get().getId(), optional.get().getProfileRole()));
        return profileDTO;
    }

    public void registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("Mazgi Email uje bant");
        }

        optional = profileRepository.findByLogin(dto.getLogin());
        if (optional.isPresent()) {
            throw new BadRequestException("Mazgi Login uje bant");
        }

        String pswd = DigestUtils.md5Hex(dto.getPassword());

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPassword(pswd);
        entity.setEmail(dto.getEmail());
        entity.setProfileRole(ProfileRole.USER_ROLE);
        entity.setStatus(ProfileStatus.CREATED);
        profileRepository.save(entity);

        String jwt = JwtUtil.createJwt(entity.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("Salom jigar Qalaysan\n");
        builder.append("Agar bu sen bo'lsang Shu linkga bos: ");
        builder.append("http://localhost:8080/auth/verification/" + jwt);

        emailService.sendEmail(dto.getEmail(), "Registration KunUz Test", builder.toString());

    }

    public void verification(String jwt) {
        Integer id = JwtUtil.decodeJwtAndGetId(jwt);

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("User Not found");
        }
        optional.get().setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(optional.get());
    }
}
