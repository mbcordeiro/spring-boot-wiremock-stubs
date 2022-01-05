package com.matheuscordeiro.addressregisterapi.services;

import com.matheuscordeiro.addressregisterapi.bean.ZipCodeResponse;

public interface AddressService {
    public ZipCodeResponse getAddressByZipCode();
}
