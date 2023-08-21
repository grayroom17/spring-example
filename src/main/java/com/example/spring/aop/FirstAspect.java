package com.example.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FirstAspect {

    /*
        @within - check annotation on the class level
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
        within - check class type name
     */
    @Pointcut("within(com.example.spring.service.*Service)")
    public void isServiceLayer() {
    }

    /*
        this - check AOP proxy class type
        target - check target object class type
     */
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

    /*
        @annotation - check annotation on method level
     */
    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
        args - check method param type
        * - any param type
        .. - 0 or many params type
     */
    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hamModelParam() {
    }

    /*
        @args - check annotation on the param type
        * - any param type
        .. - 0 or many params type
     */
    @Pointcut("isControllerLayer() && @args(com.example.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation() {
    }

    /*
        @bean - check bean name
     */
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {
    }

    /*
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
     */
    @Pointcut("execution(public * com.example.spring.service.*Service.findById(*)) ")
    public void anyFindByIdServiceMethod() {
    }

}