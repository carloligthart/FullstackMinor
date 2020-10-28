package com.assignment1_5.lagen.presentation.mapper;

import com.assignment1_5.lagen.dataAcces.entity.CombiBankAccount;
import com.assignment1_5.lagen.presentation.dto.CombiBankAccountRequestDto;
import com.assignment1_5.lagen.presentation.dto.CombiBankAccountResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CombiBankAccountMapper {

    CombiBankAccount requestToEntity(CombiBankAccountRequestDto combiBankAccountRequestDto);

    CombiBankAccountResponseDto entityToResponse(CombiBankAccount combiBankAccount);
}
