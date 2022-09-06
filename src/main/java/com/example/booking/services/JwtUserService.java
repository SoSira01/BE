package com.example.booking.services;

import com.example.booking.Enum.Role;
import com.example.booking.Exception.BookExceptionModel;
import com.example.booking.Exception.BookFieldError;
import com.example.booking.Jwt.JwtResponse;
import com.example.booking.dtos.MatchUserDTO;
import com.example.booking.dtos.RefreshUserDTO;
import com.example.booking.entities.User;
import com.example.booking.repositories.UserRepository;
import com.example.booking.Jwt.JwtTokenUtil;
import org.apache.catalina.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public  class JwtUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordService passwordService;
    @Value("${jwt.secret}")
    private String secret;

    private Session authentication;


    @Autowired
    public JwtUserService(UserRepository repository, ModelMapper modelMapper, PasswordService passwordService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordService = passwordService;
    }

    public ResponseEntity<Object> loginUser(MatchUserDTO loginRequest){
        BookFieldError fieldError;
        BookExceptionModel bookExceptionModel;

        try{
            User user = repository.findByEmail(loginRequest.getEmail());
            Object obj = modelMapper.map(user,MatchUserDTO.class);
            boolean matchPass = passwordService.validatePassword(user.getPassword(),loginRequest.getPassword());

            //Match pass and gen key
            if(obj !=null && matchPass){
                UserDetails userDetails = loadUserByUsername(loginRequest.getEmail());
                String role = userDetails.getAuthorities().toString();

                System.out.println(userDetails);

                String token = jwtTokenUtil.generateToken(userDetails);
                    System.out.println("access token : " + token);
                    final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
                    System.out.println("refresh token : " + refreshToken);
                    return new ResponseEntity<>(new JwtResponse("Login Successful","Password Match",token,refreshToken,role),HttpStatus.OK); //200
            }else{
                fieldError = new BookFieldError("Password","Password Incorrect",HttpStatus.UNAUTHORIZED); //401
                bookExceptionModel = new BookExceptionModel(fieldError.getStatus(),"Validation failed",fieldError);
                return new ResponseEntity<>(bookExceptionModel,fieldError.getStatus());
            }

        }
        catch (Exception e){
            fieldError = new BookFieldError("Email","A user with the specified email DOES NOT exist",HttpStatus.NOT_FOUND);//404
            bookExceptionModel = new BookExceptionModel(fieldError.getStatus(),"Validation failed",fieldError);
            return new ResponseEntity<>(bookExceptionModel,fieldError.getStatus());
        }
    }

    private Boolean checkExpired(String request){
        return !jwtTokenUtil.isTokenExpired(request);
    }
    BookFieldError fieldError;
    BookExceptionModel bookExceptionModel;
    
    JwtResponse jwtRes;

//     public ResponseEntity<?> getRefreshToken(HttpServletRequest request){
//         String requestRefreshToken = request.getHeader("Authorization").substring(7);
//         String userRefreshToken = jwtTokenUtil.getUsernameFromToken(requestRefreshToken);
//         UserDetails userDetails = loadUserByUsername(userRefreshToken);
//         String Token = jwtTokenUtil.generateRefreshToken(userDetails);
//         System.out.println("request token : "+requestRefreshToken);
//         System.out.println(checkExpired(requestRefreshToken));

//         if(checkExpired(requestRefreshToken).equals(true)) {
// //            fieldError = new BookFieldError("Refresh Token","Token already expired",HttpStatus.OK);//200
// //            bookExceptionModel = new BookExceptionModel(fieldError.getStatus(),"Token already expired",fieldError);
// //            jwtRes =  new JwtResponse("Access Token already expired","Generate Refresh Token",requestRefreshToken,Token);
// //            return new ResponseEntity<>(jwtRes,fieldError.getStatus());
//             return new ResponseEntity<>( new JwtResponse("Access Token already expired","Generate Refresh Token",requestRefreshToken,Token), HttpStatus.OK);
//         }

    public ResponseEntity<?> getRefreshToken(HttpServletRequest request) {
        String stringBody = "";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String requestRefreshToken = request.getHeader("Authorization").substring(7);

        try {
            System.out.println("request Refresh Token : " + requestRefreshToken);
            if (checkExpired(requestRefreshToken).equals(true)) {
                String userRefreshToken = jwtTokenUtil.getUsernameFromToken(requestRefreshToken);
                UserDetails userDetails = loadUserByUsername(userRefreshToken);
                String Token = jwtTokenUtil.generateRefreshToken(userDetails);
//                System.out.println("request token : " + requestRefreshToken);
//                System.out.println("req. token : "+requestRefreshToken+"\n"+"ref. token : "+Token);
                return new ResponseEntity<>(new JwtResponse("Generate Successful", "Refresh Token", requestRefreshToken, Token,null),HttpStatus.OK);
            } else {
                System.out.println("request Token (exp.) : " + requestRefreshToken);
            }
        } catch (ExpiredJwtException exp) {
            stringBody = "Token has already expired";
            System.out.println("request Token cannot valid");
        }

        if(stringBody != null) {
            System.out.println(stringBody);
            return new ResponseEntity<>(stringBody,status);
        } else {
            fieldError = new BookFieldError("Refresh Token","Generate Refresh Token Unsuccessful",HttpStatus.NOT_FOUND);//404
            bookExceptionModel = new BookExceptionModel(fieldError.getStatus(),"failed to generate Refresh Token ",fieldError);
            return new ResponseEntity<>(bookExceptionModel,fieldError.getStatus() );
//            System.out.println(stringBody);
//            return new ResponseEntity<>("JWT Token Error : Please contact admin", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        Collection<Role> userRole = new ArrayList<>();
        userRole.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), userRole);
    }
}
