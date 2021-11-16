package cn.gy.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class CheckTimeIntervalValidator
        implements ConstraintValidator<CheckTimeInterval, Object> {

    private String startTime;

    private String endTime;

    @Override
    public void initialize(CheckTimeInterval constraint) {
        this.startTime = constraint.startTime();
        this.endTime = constraint.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value)
            return false;
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object start = beanWrapper.getPropertyValue(startTime);
        Object end = beanWrapper.getPropertyValue(endTime);
        if (null == start || null == end)
            return false;

        int result = ((Date) end).compareTo((Date) start);
        return result >= 0;
    }

}
