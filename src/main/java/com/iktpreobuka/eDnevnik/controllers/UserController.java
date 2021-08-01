package com.iktpreobuka.eDnevnik.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.entities.dto.UserLogin;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;
import com.iktpreobuka.eDnevnik.services.FileHandler;
import com.iktpreobuka.eDnevnik.utils.Encryption;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@RestController
@RequestMapping
public class UserController {

	@Autowired
	private FileHandler fileHandler;
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Value("${spring.security.secret-key}")
	private String secretKey;
	
	@Value("${spring.security.token-duration}")
	private Integer duration;
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password){
		UserEntity user = userRepository.findByUsername(username);
		if(user != null && Encryption.validatePassword(password, user.getPassword())) {
			String token = getJWTToken(user);
			UserLogin userLog = new UserLogin(user.getUsername(), token);
			return new ResponseEntity<>(userLog, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong username/password", HttpStatus.UNAUTHORIZED);
	}
	
	private String getJWTToken(UserEntity user) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().getName());
		String token = Jwts.builder()
				.setId("softtekJWT").setSubject(user.getUsername())
				.claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + duration))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
		return "Bearer " + token;
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/download")
	public ResponseEntity<?> downloadLogs(){
		try {
			File file = fileHandler.getLogs();
			
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
			HttpHeaders responseHeaders = new HttpHeaders();
	        responseHeaders.add("content-disposition", "attachment; filename=" + "logs.txt");
	        
			
			return ResponseEntity.ok()
		            .headers(responseHeaders)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/octet-stream"))
		            .body(resource);
		} catch (IOException e) {
			e.getStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/getLogedUser")
	public ResponseEntity<?> getLogedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String aaaa = (String)auth.getPrincipal();
		UserEntity user = userRepository.findByUsername(aaaa);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	
}
