package com.customsoftware.medicalservices.repository.custom;

import com.customsoftware.medicalservices.domain.User;
import com.customsoftware.medicalservices.service.dto.search.SearchUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserRepository {
    public Page<User> search(SearchUserDTO searchUserDTO, Pageable pageable);
}
