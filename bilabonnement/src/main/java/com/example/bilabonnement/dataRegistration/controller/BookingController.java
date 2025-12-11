package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.*;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.model.view.*;
import com.example.bilabonnement.dataRegistration.service.BookingService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // =============================================== BOOKINGER ===============================================
    // VIS TABEL/LISTE OVER BOOKINGER:
    @GetMapping("/dataRegistration/bookings")
    public String showBookingsTable(Model model) {
        List<BookingTableView> bookings =
                bookingService.fetchAllBookingsWithRenterNameAndCarModel();

        model.addAttribute("bookings", bookings);

        return "dataRegistrationHTML/bookings";
    }

    // VIS BOOKING DETALJESIDE:
    @GetMapping("/dataRegistration/bookings/{id}")
    public String showBookingDetail(@PathVariable("id") int leasingContractId, Model model) {

        LeaseContract booking = bookingService.getLeaseContract(leasingContractId);
        Renter renter = bookingService.getRenterForLease(booking);
        Customer customer = bookingService.getCustomerForRenter(renter);
        Car car = bookingService.getCarForLease(booking);
        StatusHistory lastStatus = bookingService.getLatestStatusForLease(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("renter", renter);
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        model.addAttribute("lastStatus", lastStatus);

        return "dataRegistrationHTML/bookingDetail";
    }

    // GODKEND BOOKING:
    @PostMapping("/dataRegistration/bookings/{id}/approve")
    public String approveBooking(@PathVariable int id, RedirectAttributes redirectAttributes) {

        boolean success = bookingService.approveLeaseContractByIdAndUpdateCarStatus(id);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "Booking godkendt!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Fejl: Kunne ikke godkende booking");
        }

        return "redirect:/dataRegistration/bookings";
    }

    // AFVIS OG SLET BOOKING:
    @PostMapping("/dataRegistration/bookings/{id}/reject")
    public String rejectBooking(@PathVariable int id, RedirectAttributes ra) {
        boolean deleted = bookingService.rejectBooking(id);

        if (deleted) {
            ra.addFlashAttribute("message", "Booking #" + id + " er blevet afvist og slettet");
        } else {
            ra.addFlashAttribute("error", "Fejl: Kunne ikke afvise booking");
        }

        return "redirect:/dataRegistration/bookings";
    }
}


