package com.bank.auth.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bank.auth.dto.AuthResponseDTO;
import com.bank.auth.dto.LoginRequestDTO;
import com.bank.auth.dto.RefreshTokenRequestDTO;
import com.bank.auth.dto.RegisterRequestDTO;
import com.bank.auth.entity.User;
import com.bank.auth.repository.UserRepository;
import com.bank.auth.service.AuthService;
import com.bank.auth.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	
	public AuthServiceImpl(UserRepository userRepository,
			JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public AuthResponseDTO login(LoginRequestDTO request) {
		
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
		
		if(optionalUser.isEmpty()) {
			throw new RuntimeException("User not found");
		}
		
		User user = optionalUser.get();
		if(!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Invalid Password");
		}
		
		String token = jwtUtil.generateToken(user.getEmail());
		
		return new AuthResponseDTO(
				token,
				"Login Successful"
				);
	}

	@Override
	public AuthResponseDTO register(RegisterRequestDTO request) {

		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
		
		if(existingUser.isPresent()) {
			throw new RuntimeException("Email already registered");
		}
		
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setRole(request.getRole());
		
		userRepository.save(user);
		
		return new AuthResponseDTO(
				null,
				"User registered successfully"
				);
	}

	@Override
	public boolean validateToken(String token) {
		return jwtUtil.validateToken(token);
	}

	@Override
	public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
		
		boolean valid = jwtUtil.validateToken(request.getToken());
		
		if(!valid) {
			throw new RuntimeException("Invalid Token");
		}
		
		String email = jwtUtil.extractEmail(request.getToken());
		
		String newToken = jwtUtil.generateToken(email);
		
		return new AuthResponseDTO(
				newToken,
				"Token refreshed successfully"
				);
	}
	
	
}
