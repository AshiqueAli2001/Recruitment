package com.interland.admin.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.interland.admin.utils.ApplicationProperties;

public class CustomSizeValidator implements ConstraintValidator<CustomSize, String> {

	private String maxKey;
	private String minKey;

	public void initialize(CustomSize customSize) {
		this.maxKey = customSize.maxKey();
		this.minKey = customSize.minKey();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;

		int maxValue;
		int minValue;
		if (ApplicationProperties.INSTANCE.getValue(maxKey) != null)
			maxValue = Integer.parseInt(ApplicationProperties.INSTANCE.getValue(maxKey));
		else
			maxValue = Integer.parseInt(maxKey);
		if (ApplicationProperties.INSTANCE.getValue(minKey) != null)
			minValue = Integer.parseInt(ApplicationProperties.INSTANCE.getValue(minKey));
		else
			minValue = Integer.parseInt(minKey);

		if (value.length() < minValue || value.length() > maxValue) {
			return false;
		}
		return true;
	}
}
