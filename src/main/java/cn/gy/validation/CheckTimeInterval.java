package cn.gy.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckTimeIntervalValidator.class)
@Documented
@Repeatable(CheckTimeInterval.List.class)
@Target({TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
public @interface CheckTimeInterval {

    String startTime() default "from";

    String endTime() default "to";

    String message() default "时间不能为null且开始时间不能大于结束时间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Documented
    @interface List {

        CheckTimeInterval[] value();

    }

}
