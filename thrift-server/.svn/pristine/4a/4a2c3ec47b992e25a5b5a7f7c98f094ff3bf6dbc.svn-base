package com.github.mql.rpc.provider;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
public @interface RpcService
{
	String version() default "1.0.0";
	int weight() default 1;
}
