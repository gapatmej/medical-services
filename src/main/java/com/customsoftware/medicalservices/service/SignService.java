package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.domain.User;

public interface SignService {
    void sign(String path, User user);
}
