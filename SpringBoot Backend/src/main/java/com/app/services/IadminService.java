package com.app.services;

import com.app.dto.LoginDTO;

public interface IadminService {

	LoginDTO adminValidation(String email, String password);

}