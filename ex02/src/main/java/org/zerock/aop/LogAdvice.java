package org.zerock.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect 	/** @Description : �ش� Ŭ������ ��ü�� Aspect�� ���� �� ������ ��Ÿ���� ����! */
@Log4j  	/** @Descriptonn : �α�� */
@Component 	/** @Description : AOP�� ������ ������ Spring���� Bean�� �ν��ϱ� ���ؼ� �����! */
public class LogAdvice {

	/**
	 * @Description : 1) Advice�� ���� ������̼ǵ��� ���������� Pointcut�� ������
	 *                   Pointcut�� ������ @Pointcut ���ε� ������ ������
	 *                
	 *                2) �Ʒ� �޼����� @Berfoe �Ǵ� @After ������̼� ������ execution...���ڿ���,��
	 *                   AspectJ�� �������̴�.  execution�� ��� ���������ڿ� Ư�� Ŭ������ �޼��带 ������ ���ִ�
	 *                   - �Ǿ��� "*"�� �ǹ̴� ���������ڸ� �ǹ��ϰ� �ǵ��� "*"�� Ŭ������ �̸��� �޼����� �̸��� �ǹ��Ѵ�
	 *              
	 *                3) Aspect ������̼� ����
	 *                   - @Before				// Target�� JoinPoint�� ȣ���ϱ� ���� ����Ǵ� �ڵ� �ڵ� ��ü�� ������ �Ұ�����
	 *					 - @AfterReturning		// ��� ������ ���������� �̷���� �Ŀ� �����ϴ� �ڵ�
	 *					 - @AfterThrowing		// ���ܰ� �߻��� �ڿ� �����ϴ� �ڵ�
	 *					 - @After				// ���������� ����ǰų� ���ܰ� �߻����� �� ���� ���� ����Ǵ� �ڵ�
	 *					 - @Around				// �޼����� ���� ��ü�� ������ �� �ִ� ���� ������ �ڵ� ���� ��� �޼��带 ȣ���ϰ� ����� ���ܸ� ó�� ������
	 * */ 
	
	
	
	@Before( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logBefore() {
		log.info("=============================================");
		log.info("==================Before=====================");
		log.info("=============================================");
	}
	
	@After( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logAfter() {
		log.info("=============================================");
		log.info("==================After======================");
		log.info("=============================================");
	}
}
