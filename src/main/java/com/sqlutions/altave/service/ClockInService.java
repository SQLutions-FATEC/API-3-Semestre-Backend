package com.sqlutions.altave.service;

import com.sqlutions.altave.dto.ClockInRequestDTO;
import com.sqlutions.altave.dto.ClockInResponseDTO;
import com.sqlutions.altave.dto.ClockInResponseWithTotalDTO;
import com.sqlutions.altave.dto.ClockInSearchDTO;

import java.text.ParseException;

public interface ClockInService {
    ClockInResponseDTO createClockIn(ClockInRequestDTO clockInRequestDTO);
    ClockInResponseDTO getClockInById(Long id);
    ClockInResponseWithTotalDTO searchClockIns(ClockInSearchDTO clockInSearchDTO, int page, int size);
    ClockInResponseDTO updateClockIn(Long id, ClockInRequestDTO clockInRequestDTO) throws ParseException;
    ClockInResponseDTO deleteClockIn(Long id);
}