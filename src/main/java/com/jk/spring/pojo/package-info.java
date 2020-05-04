package com.jk.spring.pojo;

/*
간단한 POJO class

POJO : Plain Old Java Object, 어느 한 기술에 종속되지 않고 순수한 java object 를 뜻한다.

POJO 가 중요한 이유 : '객체 지향'이라는 java 가 어느 기술에 종속되는 순간 '객체 지향' 이 아닌, '기술 지향' 으로 변질되어 버리기 때문

POJO class : 서로간 관계가 느슨 하게 형성 되어 있으며 특정 기술과 스펙에 대해 종속적이지 않은 class 라고 한다.
printer 를 interface 로 사용해서 각 관계가 느슨하게 형성 (StringPrinter 나 ConsolePrinter 가 변경되어도 외부에 영향을 끼치지 않음)하고
특정 기술이나 스펙에 관계 없이 HelloWorld 가 구동되어 있기 때문에 POJO class 라고 볼 수 있다.

사견으론, 객체 지향인데 너무 이런 저런 기술 어노테이션 등이 무분별하게 쓰이는 부분에 대해 견제하고자 생긴 개념이 아닐까 한다.
결국 POJO 를 지향하면서 JPA 라는 새로운 '규격' 이 생긴걸 보면 개발자에게 경고하고자 생긴 개념이지 않을까 한다. (실제로 얘를 지켜라 라기 보단)
 */