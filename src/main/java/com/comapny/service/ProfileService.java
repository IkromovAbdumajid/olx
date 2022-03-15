package com.comapny.service;

import com.comapny.dto.profile.ProfileDTO;
import com.comapny.dto.profile.ProfilefilterDTO;
import com.comapny.enums.ProfileStatus;
import com.comapny.repository.ProfileRepository;
import com.comapny.spec.ProfileSpecification;
import com.comapny.entity.ProfileEntity;
import com.comapny.exception.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;


    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setProfileRole(dto.getProfileRole());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }
    public ProfileDTO getByProfileDTOByEmail(String email) {
         Optional<ProfileEntity> optionalProfile = profileRepository.findByEmail(email);
        if (optionalProfile.isPresent()) {
            return toDTO(optionalProfile.get());
        }
        throw new ItemNotFoundException("Profile having this id not found");
    }
    public List<ProfileDTO> getAllProfile () {
        List<ProfileEntity> profileEntityList = profileRepository.findAll();
        List<ProfileDTO> profileDTOList = new LinkedList<>();
        for (ProfileEntity profile : profileEntityList) {
            profileDTOList.add(toDTO(profile));
        }
        return profileDTOList;
    }
    public String deleteProfileByEmail (String email) {
        profileRepository.deleteByEmail(email);
        return "Successfully deleted";
    }

    public ProfileDTO changeProfileByEmail (String email, String name, String surname) {
         Optional<ProfileEntity> optionalProfile = profileRepository.findByEmail(email);
        if (optionalProfile.isPresent()) {
            ProfileEntity profile = optionalProfile.get();
            profile.setName(name);
            profile.setSurname(surname);
            profileRepository.save(profile);
            return toDTO(profile);
        }
        throw new ItemNotFoundException("Profile not found");
    }

    private ProfileDTO toDTO (ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setPassword(profileEntity.getPassword());
        profileDTO.setLogin(profileEntity.getLogin());
        profileDTO.setProfileRole(profileEntity.getProfileRole());
        return profileDTO;
    }

    public ProfileEntity getByLoginAndPaswd (String login, String password) {
        Optional<ProfileEntity> optional = profileRepository.findByLoginAndPassword(login, password);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw  new RuntimeException("Profile not found");
    }


    public ProfileEntity get(Integer id) {
        Optional<ProfileEntity> optional =  profileRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw  new RuntimeException("not found");
    }

    public PageImpl<ProfileEntity> filterSpe(int page, int size, ProfilefilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "profileId");

        Specification<ProfileEntity> spec = null;
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(ProfileSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(ProfileSpecification.status(ProfileStatus.CREATED));
        }
        if (filterDTO.getName() != null) {
            spec.and(ProfileSpecification.name("name",filterDTO.getName()));
        }
        if (filterDTO.getEmail() != null) {
            spec.and(ProfileSpecification.name("email",filterDTO.getEmail()));
        }
        if (filterDTO.getSurname() != null) {
            spec.and(ProfileSpecification.name("surname",filterDTO.getSurname()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ProfileSpecification.profileId( filterDTO.getProfileId()));
        }
        if (filterDTO.getFromDate() != null) {
            spec.and(ProfileSpecification.dates("fromDate", filterDTO.getFromDate()));
        }
        if (filterDTO.getToDate() != null) {
            spec.and(ProfileSpecification.dates("toDate", filterDTO.getToDate()));
        }

        Page<ProfileEntity> articlePage = profileRepository.findAll(spec, pageable);
        return (PageImpl<ProfileEntity>) articlePage;
    }


}
