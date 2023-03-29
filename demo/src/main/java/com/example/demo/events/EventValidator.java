package com.example.demo.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            errors.reject("wrongPrices", "Values fo prices are wrong");
        }

        // 이벤트 종료 일시가 이벤트 시작 일시 보다 이전인 경우
        // 또는 이벤트 종료 일시가 등록 종료 일시 보다 이전인 경우
        // 또는 이벤트 종료 일시가 등록 시작 일시보다 이전인 경우
        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
	        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
	        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
        }

        // TODO BeginEventDateTime
        // 이벤트 시작 일시가 이벤트 종료 일시 보다 이후인 경우 -> 이건 위에서 걸러짐
        // 또는 이벤트 시작 일시가 등록 종료 일시 보다 이전인 경우
        // 또는 이벤트 시작 일시가 등록 시작 일시보다 이전인 경우
        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        if (beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
        	beginEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong");
        }

        // TODO CloseEnrollmentDateTime
        // 등록 종료 일시가 등록 시작 일시 보다 이전인 경우
        // 등록 종료 일시가 이벤트 시작 일시 보다 이후인 경우 -> 위에서 걸러짐
        // 등록 종료 일시가 이벤트 종료 일시 보다 이후인 경우 -> 위에서 걸러짐
        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        if (closeEnrollmentDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "closeEnrollmentDateTime is wrong");
        }
    }
}
