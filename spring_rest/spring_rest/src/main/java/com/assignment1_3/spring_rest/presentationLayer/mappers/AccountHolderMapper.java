package com.assignment1_3.spring_rest.presentationLayer.mappers;

import com.assignment1_3.spring_rest.presentationLayer.models.AccountHolderRequestModel;
import com.assignment1_3.spring_rest.presentationLayer.models.AccountHolderResponseModel;
import com.assignment1_3.spring_rest.dataLayer.models.AccountHolder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountHolderMapper {

    AccountHolder requestToEntityObject(AccountHolderRequestModel accountHolderRequestModel);  // change naming maybe?

    AccountHolderResponseModel entityObjectToResponseModel(AccountHolder accountHolder);

}
