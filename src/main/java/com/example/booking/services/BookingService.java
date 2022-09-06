package com.example.booking.services;

import com.example.booking.Enum.Role;
import com.example.booking.Jwt.JwtTokenUtil;
import com.example.booking.dtos.BookingDTO;
import com.example.booking.dtos.EditBookingDTO;
import com.example.booking.dtos.EditUserDTO;
import com.example.booking.entities.Booking;
import com.example.booking.entities.User;
import com.example.booking.repositories.BookingRepository;
import com.example.booking.repositories.CategoryRepository;
import com.example.booking.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //get booking
    public List<BookingDTO> getBooking(){
        List<Booking> BookingList = repository.findAll(Sort.by("startTime").descending());
        return  listMapper.mapList(BookingList,BookingDTO.class,modelMapper);
    }

    //create booking
    public Booking create(BookingDTO newBooking) {
        Booking book = modelMapper.map(newBooking, Booking.class);
//
//        if (!OverlapStartTime(book.getCategory().getId(), book.getStartTime())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StartTime error");
//        }
        return repository.saveAndFlush(book);
    }

    //get booking by id
    public BookingDTO getBookingById(Integer bookingId) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking id "+ bookingId+
                        "Get Booking By id Not found"
                ));
        return modelMapper.map(booking, BookingDTO.class);
    }

    //get by categoryId
    public List<BookingDTO> getBookingByCategoryId(Integer categoryId) {
        List<Booking>  booking = repository.findBycategoryIdOrderByStartTimeDesc(categoryId);
        return listMapper.mapList(booking, BookingDTO.class,modelMapper);
    }

    //delete booking
    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + "Not Found ID To Delete"));
        repository.deleteById(id);
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

    public EditBookingDTO editBooking(EditBookingDTO editbookingdto, Integer id) {
        Booking booking = repository.findById(id).map(o->mapBooking(o, editbookingdto))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "No ID : " + id));
        repository.saveAndFlush(booking);
        return modelMapper.map(booking, EditBookingDTO.class);
    }
    //Edit
//    public BookingDTO editBooking(EditBookingDTO editbookingdto, Integer id){
//        Booking booking = modelMapper.map(editbookingdto, Booking.class);
//        Booking bk = repository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.BAD_REQUEST, "Booking id" + id +
//                        "Not found ID to Edit"
//                ));
//        bk.setStartTime(booking.getStartTime());
//        bk.setNote(booking.getNote());
//        repository.saveAndFlush(bk);
//        return modelMapper.map(bk,BookingDTO.class);
//    }

    //get booking with student role
    public List<BookingDTO> getBookingByEmail(HttpServletRequest request){
        String requestToken = request.getHeader("Authorization").substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(requestToken);
        List<Booking> BookingList = repository.findBookingByEmailOrderByStartTimeDesc(user);
        return  listMapper.mapList(BookingList,BookingDTO.class,modelMapper);
    }

}