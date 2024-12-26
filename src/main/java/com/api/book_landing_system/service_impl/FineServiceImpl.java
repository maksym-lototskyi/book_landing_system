package com.api.book_landing_system.service_impl;

import com.api.book_landing_system.dtos.fine_dtos.FineGetDto;
import com.api.book_landing_system.mapper.FineMapper;
import com.api.book_landing_system.model.Fine;
import com.api.book_landing_system.repository.FineRepository;
import com.api.book_landing_system.repository.UserRepository;
import com.api.book_landing_system.service.FineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FineServiceImpl implements FineService {
    private final FineRepository fineRepository;
    private final UserRepository userRepository;

    public FineServiceImpl(FineRepository fineRepository, UserRepository userRepository) {
        this.fineRepository = fineRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<FineGetDto> getUserFines(Long userId) {
        return fineRepository.findAllByUserId(userId).stream().map(FineMapper::fineToFineGetDto).collect(Collectors.toList());
    }

    @Override
    public List<FineGetDto> getUserUnpaidFines(Long userId) {
        return fineRepository.findAllUnpaidByUserId(userId).stream().map(FineMapper::fineToFineGetDto).collect(Collectors.toList());
    }

    @Override
    public void payFines(Long userId, Long fineId) {
        if(!userRepository.existsById(userId)){
            throw new IllegalArgumentException("There is no user with id " + userId);
        }
        if(!fineRepository.existsById(fineId)){
            throw new IllegalArgumentException("There is no fine with id " + userId);
        }

        Fine fine = fineRepository.findById(fineId).get();

        if(fine.isPaid()){
            throw new IllegalArgumentException("This fine is already paid");
        }
        if(fine.getLoan().getUser().getId().equals(userId)){
            throw new IllegalArgumentException("You can not pay this fine");
        }

        fine.setPaid(true);
        fineRepository.save(fine);
    }
}
