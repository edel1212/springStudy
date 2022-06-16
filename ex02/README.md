# spring_ex02

AOP 설정 및 Transaction 설정

1) AOP 설정 요약
 - aspectJ 와 aspectJ weaver를 pom에 추가해준다.  
   *주의 
    <org.aspectj-version>1.9.0</org.aspectj-version>
    <org.slf4j-version>1.7.25</org.slf4j-version>
    버전을 꼭 확인을 하자
    
   => root-context에서 namesapces에서 AOP를 추가해준다 
   => root-context에 aop:aspectj-autoproxy도 추가해준다
   => component:scan을 사용해서 aop 적용할 패키지명을 bean에 주입해주자. 
   => 다음 적용한 package에 java파일을만들어 사용하자 @See: LogAdvice.java
   
2) Transaction 설정 요약
 - pom 과 root-context에 jdbc 설정을 해주자 
   => root-context의 namespaces에 tx를 추가해주자
   => bean에 tx관련 설정을 주입해주자 @See : root-context
   => 이 후 적용할 메서드에 @Transactional를 추가해주자 @See : SampleTxServiceImpl.java
