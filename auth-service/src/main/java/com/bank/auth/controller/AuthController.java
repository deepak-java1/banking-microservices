package com.bank.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.auth.dto.AuthResponseDTO;
import com.bank.auth.dto.LoginRequestDTO;
import com.bank.auth.dto.RefreshTokenRequestDTO;
import com.bank.auth.dto.RegisterRequestDTO;
import com.bank.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request){
		
		AuthResponseDTO response = authService.login(request);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(
					@RequestBody RegisterRequestDTO request){
		
		AuthResponseDTO response = authService.register(request);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validateToken(
									 @RequestParam String token	){
		
		boolean valid = authService.validateToken(token);
		
		return ResponseEntity.ok(valid);
	}
	
	@PostMapping("/refresh-token")
	public ResponseEntity<AuthResponseDTO> refreshToken(
			@RequestBody RefreshTokenRequestDTO request){
				
		AuthResponseDTO response = authService.refreshToken(request);
		
		return ResponseEntity.ok(response);
		
	}
	
	
	
	
}
