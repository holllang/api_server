package swyg.hollang.aop

import org.aspectj.lang.annotation.Pointcut

class LogTracePointcuts {

    @Pointcut("execution(* swyg.hollang.repository..*(..))")
    fun allRepository(){}

    @Pointcut("execution(* swyg.hollang.service..*(..))")
    fun allService(){}

    @Pointcut("execution(* swyg.hollang.controller..*(..))")
    fun allController(){}

    @Pointcut("allRepository() || allService() || allController()")
    fun allLogTrace(){}
}