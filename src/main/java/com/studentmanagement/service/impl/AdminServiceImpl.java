package com.studentmanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.studentmanagement.dto.AdminRequestDto;
import com.studentmanagement.dto.ChangePasswordDto;
import com.studentmanagement.entity.Admin;

import com.studentmanagement.repository.AdminRepository;

import com.studentmanagement.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired

    private AdminRepository adminRepository;

    @Override
    public String register(AdminRequestDto dto) {

        Optional<Admin> existingAdmin = adminRepository.findByEmail(dto.getEmail());

        if(existingAdmin.isPresent()) {

            throw new RuntimeException("Email already registered");
        }

        Admin admin = new Admin();

        admin.setName(dto.getName());

        admin.setEmail(dto.getEmail());

        admin.setPassword(dto.getPassword());

        adminRepository.save(admin);

        return "Admin registered successfully";
    }

    @Override
    public String login(AdminRequestDto dto) {

        Admin admin = adminRepository
                        .findByEmail(dto.getEmail())

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid Email"
                                )
                        );

        if(!admin.getPassword()
                .equals(dto.getPassword())) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        return "Login successful";
    }

    @Override
    public String changePassword(ChangePasswordDto dto) {

        Admin admin =
                adminRepository
                        .findByEmail(dto.getEmail())

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Admin not found"
                                )
                        );

        if(!admin.getPassword()
                .equals(dto.getOldPassword())) {

            throw new RuntimeException(
                    "Old password is incorrect"
            );
        }

        admin.setPassword(
                dto.getNewPassword()
        );

        adminRepository.save(admin);

        return "Password updated successfully";
    }
}