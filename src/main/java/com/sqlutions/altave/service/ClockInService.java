package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.*;

import java.text.ParseException;

public interface ClockInService {
    ClockInResponseDTO createClockIn(ClockInRequestDTO clockInRequestDTO);
    ClockInResponseDTO getClockInById(Long id);
    ClockInResponseWithTotalDTO searchClockIns(ClockInSearchDTO clockInSearchDTO, int page, int size);
    ClockInResponseDTO updateClockIn(Long id, ClockInRequestDTO clockInRequestDTO) throws ParseException;
    ClockInResponseDTO deleteClockIn(Long id);
    ClockInResponseDTO updateClockInTime(Long id, ClockInTimeUpdateDTO updateDTO);

}