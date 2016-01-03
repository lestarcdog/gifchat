package hu.cdog.gifchat.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.MINIMAL_CLASS, property = "_type")
public abstract class BaseDto {

}
