package com.bank.auth.service;

import com.bank.auth.dto.AuthResponseDTO;
import com.bank.auth.dto.LoginRequestDTO;
import com.bank.auth.dto.RefreshTokenRequestDTO;
import com.bank.auth.dto.RegisterRequestDTO;

public interface AuthService {

	AuthResponseDTO login(LoginRequestDTO request);
	AuthResponseDTO register(RegisterRequestDTO  request);
	boolean validateToken(String token);
	AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);
	
}

