package com.assignment1_4.databases.presentationLayer.mappers;

import com.assignment1_4.databases.presentationLayer.models.BankAccountRequestModel;
import com.assignment1_4.databases.presentationLayer.models.BankAccountResponseModel;
import com.assignment1_4.databases.dataLayer.models.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccount requestToEntityObject(BankAccountRequestModel bankAccountRequestModel);

    BankAccountResponseModel EntityObjectToResponseModel(BankAccount bankAccount);

}
