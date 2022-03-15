
package com.comapny.spec;

import com.comapny.entity.ProfileEntity;
import com.comapny.enums.ProfileRole;
import com.comapny.enums.ProfileStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProfileSpecification {
    public static Specification<ProfileEntity> role(ProfileRole role) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role"), role);
        });
    }

    public static Specification<ProfileEntity> status(ProfileStatus status) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    public static Specification<ProfileEntity> name(String field,String name) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), name);
        });
    }


    public static Specification<ProfileEntity> profileId(Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("profile.id"), id);
        });
    }

    public static Specification<ProfileEntity> dates(String field, LocalDate date) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), date);
        });
    }
}
