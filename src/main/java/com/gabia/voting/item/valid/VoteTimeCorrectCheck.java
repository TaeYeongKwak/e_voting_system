package com.gabia.voting.item.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VoteTimeCorrectCheckValidator.class)
public @interface VoteTimeCorrectCheck {

    String message() default "입력된 투표 시간 정보가 올바르지 않습니다.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startTime();

    String endTime();

}
