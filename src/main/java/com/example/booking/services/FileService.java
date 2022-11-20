package com.example.booking.services;

import com.example.booking.Jwt.JwtTokenUtil;
import com.example.booking.dtos.BookingDTO;
import com.example.booking.entities.Booking;
import com.example.booking.entities.User;
import com.example.booking.repositories.BookingRepository;
import com.example.booking.repositories.CategoryOwnerRepository;
import com.example.booking.repositories.UserRepository;
import com.example.booking.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
@Service
public class FileService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;

    // GET file
    public ResponseEntity<?> getFile(HttpServletRequest request, String name, Integer id) {
        String requestToken = request.getHeader("Authorization");
        Booking bk = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Can't Get Booking ID: " + id
        ));
        String userName = null;
        User user = null;
        String typeFile = bk.getFileType();

        if ( requestToken != null ) {
            userName = jwtTokenUtil.getUsernameFromToken(requestToken.substring(7));
            user = userRepository.findByEmail(userName);
        }
        try{
            if ( requestToken == null || user.getRole().name() == "lecturer"
                    || user.getRole().name() == "admin" || user.getRole().name() == "student"
                    || requestToken.intern() == "" ) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + bk.getFileName() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE,typeFile)
                        .body(bk.getFile());
            } else {
                return new ResponseEntity<>("Cannot loading this file",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            if( requestToken != null && (user.getRole().name() == "student" || user.getRole().name() == "lecturer" || user.getRole().name() == "admin" )){
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error with authorize",e);
            }
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Error without authorize",e);
        }
    }

    // VIEW file
    public ResponseEntity<?> viewFile(HttpServletRequest request, String name,Integer id) {
        String requestToken = request.getHeader("Authorization");
        Booking bk = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Can't Get Booking ID: " + id
        ));
        String userName = null;
        User user = null;
        String typeFile = bk.getFileType();

        if ( requestToken != null ) {
            userName = jwtTokenUtil.getUsernameFromToken(requestToken.substring(7));
            user = userRepository.findByEmail(userName);
        }
        try{
            if ( requestToken == null || user.getRole().name() == "lecturer"
                    || user.getRole().name() == "admin" || user.getRole().name() == "student"
                    || requestToken.intern() == "" ) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "inline; filename=\"" + bk.getFileName() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE,typeFile)
                        .body(bk.getFile());
            } else {
                return new ResponseEntity<>("Cannot viewing this file",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            if( requestToken != null && (user.getRole().name() == "student" || user.getRole().name() == "lecturer" || user.getRole().name() == "admin" )){
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error with authorize",e);
            }
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Error without authorize",e);
        }
    }

    public ResponseEntity<?> resetFile(HttpServletRequest request, Integer id) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        try{
            if ( (user.getRole().name() == "admin") ||
                    (user.getRole().name() == "student" && bookingRepository.findByIdAndEmail(id,userName).size() != 0) ) {
                Booking bk = bookingRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Can't Found This Booking"));
                bk.setFileName(null);
                bk.setFile(null);
                bk.setFileType(null);
                bookingRepository.saveAndFlush(bk);
                return new ResponseEntity<>(modelMapper.map(bk, BookingDTO.class),HttpStatus.OK);
            } else return new ResponseEntity<>(bookingRepository.findById(null),HttpStatus.EXPECTATION_FAILED);
        } catch (Exception ex) {
            if(user.getRole().name() == "student"){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The booking email must be the same as the student's email !",ex);
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Error with unexpected booking id or operation",ex);
        }
    }
}
