package com.tcb.sensenet.internal.util;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.EnumUtils;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;

public class EnumUtil {
	public static <T extends Enum> IllegalArgumentException usage(String s, Class<T> e){
		String possibleValues = Stream.of(
				e.getEnumConstants())
				.map(o -> o.name())
				.collect(Collectors.joining(","));
		String msg = String.format(
				"Unknown input mode: %s for %s; Possible values: %s",
				s,e.toString(),possibleValues);
		return new IllegalArgumentException(msg);
	}
	
	public static <E extends Enum<E>> E valueOfCLI(String s, Class<E> clazz){
		E obj = EnumUtils.getEnum(clazz, s.toUpperCase());
		if(obj==null){
			throw usage(s,clazz);
		}
		return obj;
	}
	
	public static <T extends Enum<T>> Optional<T> getEnum(Class<T> clazz, String s){
		return Optional.ofNullable(EnumUtils.getEnum(clazz, s));
	}
}
