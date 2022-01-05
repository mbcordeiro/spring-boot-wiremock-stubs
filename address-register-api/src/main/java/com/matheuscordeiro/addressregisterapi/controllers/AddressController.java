package com.matheuscordeiro.addressregisterapi.controllers;

import com.matheuscordeiro.addressregisterapi.bean.Address;
import com.matheuscordeiro.addressregisterapi.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private static Logger LOG = Logger.getLogger(AddressController.class.getName());

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("address", new Address());
        return "address";
    }

    @PostMapping
    public String saveNewAddress(@ModelAttribute("address") Address address) {
        LOG.info("new request for address registration");
        return "list-address";
    }
}
