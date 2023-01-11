package com.gabia.voting.item.valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Slf4j
public class VoteTimeCorrectCheckValidator implements ConstraintValidator<VoteTimeCorrectCheck, Object> {

    private String message;
    private String startTimeFieldName;
    private String endTimeFieldName;

    @Override
    public void initialize(VoteTimeCorrectCheck constraintAnnotation) {
        message = constraintAnnotation.message();
        startTimeFieldName = constraintAnnotation.startTime();
        endTimeFieldName = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDateTime startTime = getFieldValue(value, startTimeFieldName);
        LocalDateTime endTime = getFieldValue(value, endTimeFieldName);
        boolean flag = (startTime.isEqual(endTime) || startTime.isBefore(endTime));
        if (!flag){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(startTimeFieldName)
                    .addConstraintViolation();
        }
        return flag;
    }

    private LocalDateTime getFieldValue(Object object, String fieldName) {
        Class<?> classType = object.getClass();
        Field dateField;
        try {
            dateField = classType.getDeclaredField(fieldName);
            dateField.setAccessible(true);
            Object target = dateField.get(object);
            if (!(target instanceof LocalDateTime)) {
                throw new ClassCastException();
            }
            return (LocalDateTime) target;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new ServerErrorException("해당 필드를 찾을 수 없습니다.");
    }
}
