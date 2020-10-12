package com.assignment1_3.spring_rest.presentationLayer.mappers;

import com.assignment1_3.spring_rest.presentationLayer.models.BankAccountRequestModel;
import com.assignment1_3.spring_rest.presentationLayer.models.BankAccountResponseModel;
import com.assignment1_3.spring_rest.dataLayer.models.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccount requestToEntityObject(BankAccountRequestModel bankAccountRequestModel);

    BankAccountResponseModel EntityObjectToResponseModel(BankAccount bankAccount);

}
