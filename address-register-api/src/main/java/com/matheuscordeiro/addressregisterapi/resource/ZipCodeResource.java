package com.matheuscordeiro.addressregisterapi.resource;

import com.matheuscordeiro.addressregisterapi.bean.ZipCodeResponse;
import com.matheuscordeiro.addressregisterapi.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipCode")
public class ZipCodeResource {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{zipCode}")
    public ZipCodeResponse listAddressByZipCode(@PathVariable String zipCode) {
        return addressService.getAddressByZipCode(zipCode);
    }

}
