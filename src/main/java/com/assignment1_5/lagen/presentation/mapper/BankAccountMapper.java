package com.assignment1_5.lagen.presentation.mapper;

import com.assignment1_5.lagen.dataAcces.entity.BankAccount;
import com.assignment1_5.lagen.presentation.dto.BankAccountRequestDto;
import com.assignment1_5.lagen.presentation.dto.BankAccountResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface BankAccountMapper {

    BankAccount requestToEntity(BankAccountRequestDto bankAccountRequestDto);

    BankAccountResponseDto entityToResponse(BankAccount bankAccount);
}
