package com.example.booking.services;

import com.example.booking.Jwt.JwtTokenUtil;
import com.example.booking.dtos.BookingDTO;
import com.example.booking.dtos.EditBookingDTO;
import com.example.booking.entities.Booking;
import com.example.booking.entities.CategoryOwner;
import com.example.booking.entities.User;
import com.example.booking.repositories.BookingRepository;
import com.example.booking.repositories.CategoryOwnerRepository;
import com.example.booking.repositories.UserRepository;
import com.example.booking.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.booking.Exception.BookExceptionModel;
import com.example.booking.Exception.BookFieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryOwnerRepository ownerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //get booking
    public List<BookingDTO> getBooking(HttpServletRequest request){
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        if (user.getRole().name() == "student") {
            return getBookingByEmail(userName);
        } if (user.getRole().name() == "lecturer") {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by("startTime").descending());
            List<CategoryOwner> ownerList = ownerRepository.findByUser_Name(user.getName());
            List<Booking> bookings = new ArrayList<>();
            checkConditionLecturer(bookingList,ownerList,bookings);
            bookings.sort(Comparator.comparing(Booking::getStartTime).reversed());
            return  listMapper.mapList(bookings,BookingDTO.class,modelMapper);
        } if (user.getRole().name() == "admin") {
            List<Booking> BookingList = bookingRepository.findAll(Sort.by("startTime").descending());
            return  listMapper.mapList(BookingList,BookingDTO.class,modelMapper);
        } else {
            List<BookingDTO> emptyBooking = new ArrayList<>();
            return emptyBooking;
        }
    }
    //get all booking with student role
    public List<BookingDTO> getBookingByEmail(String user){
        List<Booking> BookingList = bookingRepository.findBookingByEmailOrderByStartTimeDesc(user);
        return  listMapper.mapList(BookingList,BookingDTO.class,modelMapper);
    }

    public void checkConditionLecturer(List<Booking> booking, List<CategoryOwner> owners, List<Booking> filter) {
//        System.out.println("Before checkConditionLecturer");
        if (booking != null && owners != null) {
//            System.out.println("Between checkConditionLecturer");
            for (CategoryOwner owner : owners) {
//                System.out.println("owner : "+owner.getEventCategory().getId());
                for (Booking bk : booking) {
//                    System.out.println("bk : "+bk.getCategory().getId());
                    if(owner.getEventCategory().getId() == bk.getCategory().getId()) {
                        filter.add(bk);
//                        System.out.println("add "+bk);
                    }
                }
            }
        }
    }
    // create booking
    public Booking create(HttpServletRequest request, BookingDTO newBooking) {
        String requestToken = request.getHeader("Authorization");
        String userName = null;
        User user = null;
        Booking booking;
        if ( requestToken != null ) {
            userName = jwtTokenUtil.getUsernameFromToken(requestToken.substring(7));
            user = userRepository.findByEmail(userName);
        }
        try{
            if ( requestToken == null ) {
                booking = modelMapper.map(newBooking, Booking.class);
                return bookingRepository.saveAndFlush(booking);
            } else {
                if ( user.getRole().name() != "lecturer" &&
                        (requestToken.intern() == "" || user.getRole().name() == "admin"
                                || (user.getRole().name() == "student" && newBooking.getEmail().intern() == userName.intern())
                        )) {
                    booking = modelMapper.map(newBooking, Booking.class);
                    return bookingRepository.saveAndFlush(booking);
                }else return modelMapper.map(bookingRepository.findById(null), Booking.class);

            }
        } catch (Exception ex) {
            if( requestToken != null && user.getRole().name() == "student"){
               throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"The booking email must be the same as the student's email !",ex);
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    //get booking by id
    public BookingDTO getBookingById(HttpServletRequest request,Integer bookingId) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        Boolean checkLecturer = false;
        try {
            if(user.getRole().name() == "lecturer") {
                Booking findbooking = bookingRepository.findAllById(bookingId);
                List<CategoryOwner> ownerList = ownerRepository.findByUser_Name(user.getName());
                for (CategoryOwner owner : ownerList) {
                    if(owner.getEventCategory().getId() == findbooking.getCategory().getId()) checkLecturer = true;
                }
            }
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try{
            if ( user.getRole().name() == "admin" || (user.getRole().name() == "lecturer" && checkLecturer)
                    || (user.getRole().name() == "student" && bookingRepository.findByIdAndEmail(bookingId,userName).size() != 0)) {
                Booking booking = bookingRepository.findById(bookingId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Can't Get Booking ID: " + bookingId
                        ));
                return modelMapper.map(booking, BookingDTO.class);
            } else return modelMapper.map(bookingRepository.findById(null), BookingDTO.class);
        } catch (Exception ex) {
            if(user.getRole().name() == "student"){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The booking email must be the same as the student's email !",ex);
            } throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    //get by categoryId
    public List<BookingDTO> getBookingByCategoryId(HttpServletRequest request,Integer categoryId) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        if (user.getRole().name() == "student") {
            List<Booking> filterBooking = bookingRepository.findByEmailAndCategoryIdOrderByStartTimeDesc(userName,categoryId);
            return listMapper.mapList(filterBooking, BookingDTO.class, modelMapper);
        }
        if (user.getRole().name() == "lecturer") {
            List<Booking> bookingList = bookingRepository.findBycategoryIdOrderByStartTimeDesc(categoryId);
            List<CategoryOwner> ownerList = ownerRepository.findByUser_Name(user.getName());
            List<Booking> bookings = new ArrayList<>();
            checkConditionLecturer(bookingList,ownerList,bookings);
            bookings.sort(Comparator.comparing(Booking::getStartTime).reversed());
            return listMapper.mapList(bookings, BookingDTO.class, modelMapper);

        }
        if (user.getRole().name() == "admin") {
            List<Booking>  booking = bookingRepository.findBycategoryIdOrderByStartTimeDesc(categoryId);
            return listMapper.mapList(booking, BookingDTO.class,modelMapper);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    // delete booking
    public void deleteById(HttpServletRequest request,Integer id) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        try{
            if ( (user.getRole().name() == "admin") ||
                    (user.getRole().name() == "student" && bookingRepository.findByIdAndEmail(id,userName).size() != 0) ) {
                bookingRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Can't Found This Booking"));
                bookingRepository.deleteById(id);
            } else bookingRepository.findById(null);
        } catch (Exception ex) {
            if(user.getRole().name() == "student"){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The booking email must be the same as the student's email !",ex);
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    //EDIT
    public Booking mapBooking(Booking existsBooking, EditBookingDTO editbookingdto){
        if(editbookingdto.getNote() != null){
            existsBooking.setNote(editbookingdto.getNote().trim());
        }
        if(editbookingdto.getStartTime() != null){
            existsBooking.setStartTime(editbookingdto.getStartTime());
        }
        return existsBooking;
    }

    public EditBookingDTO editBooking(HttpServletRequest request,EditBookingDTO editbookingdto, Integer id) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);

        if ( user.getRole().name() == "admin" || (user.getRole().name() == "student" && bookingRepository.findByIdAndEmail(id,userName).size() != 0)) {
            Booking booking = bookingRepository.findById(id).map(o->mapBooking(o, editbookingdto))
            .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.FORBIDDEN, "No ID : " + id));
                    bookingRepository.saveAndFlush(booking);
            return modelMapper.map(booking, EditBookingDTO.class);
        }else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    //get booking with student role
    public List<BookingDTO> getBookingByEmail(HttpServletRequest request){
        String requestToken = request.getHeader("Authorization").substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(requestToken);
        List<Booking> BookingList = bookingRepository.findBookingByEmailOrderByStartTimeDesc(user);
        return  listMapper.mapList(BookingList,BookingDTO.class,modelMapper);
    }

}