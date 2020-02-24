package com.layers;



import java.io.Serializable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;



@SuppressWarnings("serial")
@Aspect
@Component
public class ExecutionTimeTrackerAdvice  implements Serializable
{
// 	 Logger logger=LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);
 	
 	Logger logger=org.slf4j.LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class); 
	
	 @Around("@annotation(com.layers.TrackExecutionTime)")
     public String tracktime(ProceedingJoinPoint pjp) throws Throwable
     {	 
    	 Long Start=System.currentTimeMillis();
    	 Object obj=pjp.proceed();    
    	 Long End=System.currentTimeMillis();
    	 Long Total=End-Start;
         logger.info("end time - start time is" + Total);
    	 System.out.println(Total);
    	 return "junaid";
     }

}
