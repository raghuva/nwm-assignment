package com.nwm.common;


public abstract class ResourceUtils {

	public static <T extends Object> T entityOrNotFoundException(T entity) {
		if ( entity == null  ) {
			throw new ResourceNotFoundException();
		}
		return entity;
	}
}
