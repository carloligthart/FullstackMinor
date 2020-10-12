package com.assignment1_4.databases.presentationLayer.mappers;

import com.assignment1_4.databases.presentationLayer.models.AccountHolderRequestModel;
import com.assignment1_4.databases.presentationLayer.models.AccountHolderResponseModel;
import com.assignment1_4.databases.dataLayer.models.AccountHolder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountHolderMapper {

    AccountHolder requestToEntityObject(AccountHolderRequestModel accountHolderRequestModel);

    AccountHolderResponseModel entityObjectToResponseModel(AccountHolder accountHolder);

}
