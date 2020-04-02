package com.jk.spring.IOCAndDI;

/*
Dependency Injection 과 Inversion Of Control 과 관련된 부분

예시는 UseService class 를 확인하면 가능하다.

현재 예시에서는 매우 간단하기 때문에 생성자로 새로 만들어서 사용하나 (라이브러리를 호출)
DI 를 통해 IOC 패턴을 사용하나 (spring container 가 라이브러리를 줌)
큰 차이를 못 느끼지만 저렇게 사용하는 method 가 많아진다면 매 호출 시 생성자를 통해 하는 것 보다
injection 을 통해 주입받는 것이 의존성을 낮출 수 있다.
 */