package com.app.services.impl;

import com.app.services.IadminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AdminRepository;
import com.app.dto.LoginDTO;

@Service
public class AdminService implements IadminService {
    
    @Autowired
    AdminRepository adminRepo;

    @Override
    public LoginDTO adminValidation(String email, String password) {
        // Check if the email exists
        if (adminRepo.emailExist(email)) {
            // Fetch admin details
            LoginDTO adminDetails = adminRepo.getAdminDetails(email, password);
            // If admin details are null, return null indicating invalid password
            if (adminDetails != null) {
                return adminDetails;
            }
        }
        return null; // Return null if email doesn't exist or password is incorrect
    }
}
