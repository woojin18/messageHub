package kr.co.uplus.cm.common.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.dto.RestResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component
public class LoggingAspect {
	
	@Autowired
	private ObjectMapper mapper;

//	@Pointcut("within(kr.co.uplus.cm..controller..*)")
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void onRequest() {
	}

	@Around("onRequest()")
	public Object logAction(ProceedingJoinPoint joinPoint) throws Throwable {
		Class clazz = joinPoint.getTarget().getClass();
		Object result = null;
		try {
			result = joinPoint.proceed(joinPoint.getArgs());
			return result;
		} finally {
			try {
		        String msg = "[URL] " + getRequestUrl(joinPoint, clazz);
		        msg = msg + " [REQUEST] " + params(joinPoint);
		        
		        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
		        msg = msg + " [STATUS] " + response.getStatus();

		        if (result instanceof RestResult) {
			        msg = msg + " [RESPONSE] " + mapper.writeValueAsString(result);
		        }
		        log.info(msg);
			} catch (Exception e) {
				
			}
		}
	}

	private String getRequestUrl(JoinPoint joinPoint, Class clazz) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
		String url = null;
		if (requestMapping == null) {
			url = Stream.of(GetMapping.class, PutMapping.class, PostMapping.class, PatchMapping.class, DeleteMapping.class, RequestMapping.class)
					.filter(mappingClass -> method.isAnnotationPresent(mappingClass))
					.map(mappingClass -> getUrl(method, mappingClass, "")).findFirst().orElse(null);
		} else {
			String baseUrl = requestMapping.value()[0];
			url = Stream.of(GetMapping.class, PutMapping.class, PostMapping.class, PatchMapping.class, DeleteMapping.class, RequestMapping.class)
					.filter(mappingClass -> method.isAnnotationPresent(mappingClass))
					.map(mappingClass -> getUrl(method, mappingClass, baseUrl)).findFirst().orElse(null);
		}
		
		return url;
	}

	private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl) {
		Annotation annotation = method.getAnnotation(annotationClass);
		String[] value;
		String httpMethod = null;
		try {
			value = (String[]) annotationClass.getMethod("value").invoke(annotation);
			httpMethod = (annotationClass.getSimpleName().replace("Mapping", "")).toUpperCase();
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			return null;
		}
		return String.format("%s %s%s", httpMethod, baseUrl, value.length > 0 ? value[0] : "");
	}

	private Map params(JoinPoint joinPoint) {
		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
		String[] parameterNames = codeSignature.getParameterNames();
		Object[] args = joinPoint.getArgs();
		Map<String, Object> params = new HashMap<>();
		for (int i = 0; i < parameterNames.length; i++) {
			params.put(parameterNames[i], args[i]);
		}
		return params;
	}
}
