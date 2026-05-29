package com.studentmanagement.service;

import com.studentmanagement.dto.AdminRequestDto;
import com.studentmanagement.dto.ChangePasswordDto;

public interface AdminService {

    String register(AdminRequestDto dto);

    String login(AdminRequestDto dto);

    String changePassword(ChangePasswordDto dto);
}