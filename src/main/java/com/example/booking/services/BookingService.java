package com.example.booking.services;

import com.example.booking.dtos.BookingDTO;
import com.example.booking.dtos.EditBookingDTO;
import com.example.booking.entities.Booking;
import com.example.booking.repositories.BookingRepository;
import com.example.booking.utils.ListMapper;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    //get booking
    public List<BookingDTO> getBooking(){
        List<Booking> BookingList = repository.findAll(Sort.by("startTime").descending());
        return  listMapper.mapList(BookingList,BookingDTO.class,modelMapper);
    }
    //create booking
    public Booking create(BookingDTO newBooking) {
         Booking book = modelMapper.map(newBooking,Booking.class);
        return repository.saveAndFlush(book);
    }

    //get booking by id
    public BookingDTO getBookingById(Integer bookingId) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking id "+ bookingId+
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(booking, BookingDTO.class);
    }

    //delete booking
    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does not exist !!!"));
        repository.deleteById(id);
    }
    //edit booking
//    public BookingDTO editBooking(EditBookingDTO editbookingdto, Integer id){

//        Booking bk = repository.findById(id)
//                .orElseThrow(()->new ResponseStatusException(
//                HttpStatus.NOT_FOUND,"Booking id" + id +
//                "does not exist !!!"
//                ));
//                bk.setEmail(booking.getEmail());
//                bk.setStartTime(booking.getStartTime());
//                bk.setNote(booking.getNote());
//                repository.saveAndFlush(bk);
//        return modelMapper.map(bk,BookingDTO.class);
//    }
//
//}
    public BookingDTO editBooking(EditBookingDTO editbookingdto,Integer id){
        Booking bk = repository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"notfound :" +id
        ));
        bk.setStartTime(editbookingdto.getStartTime());
        bk.setNote(editbookingdto.getNote());
        repository.saveAndFlush(bk);
        return modelMapper.map(bk,BookingDTO.class);
    }

}
