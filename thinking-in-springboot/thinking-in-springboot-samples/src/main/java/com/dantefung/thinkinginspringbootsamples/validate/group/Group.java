package com.dantefung.thinkinginspringbootsamples.validate.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author Mark sunlightcs@gmail.com
 */
@GroupSequence({Add.class,Update.class, Verify.class,Reset.class,Delete.class})
public interface Group {

}