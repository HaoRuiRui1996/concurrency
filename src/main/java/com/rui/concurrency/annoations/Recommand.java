package com.rui.concurrency.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 推荐类或者写法
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Recommand {
}