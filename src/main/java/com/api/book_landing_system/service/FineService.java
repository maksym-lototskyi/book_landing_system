package com.api.book_landing_system.service;

import com.api.book_landing_system.dtos.fine_dtos.FineGetDto;

import java.util.List;

public interface FineService {
    List<FineGetDto> getUserFines(Long userId);
    List<FineGetDto> getUserUnpaidFines(Long userId);
    void payFines(Long id, Long fineId);
}
