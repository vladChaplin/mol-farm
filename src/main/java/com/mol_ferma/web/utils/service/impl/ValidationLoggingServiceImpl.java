package com.mol_ferma.web.utils.service.impl;

import com.mol_ferma.web.utils.service.ValidationLoggingService;
import org.springframework.stereotype.Service;

@Service
public class ValidationLoggingServiceImpl implements ValidationLoggingService {

    private String value;

    @Override
    public void setValidationValue(String value) {
        this.value = value;
    }

    @Override
    public String getValidationValue() {
        return value;
    }
}
