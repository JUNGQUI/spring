### Resolver

[여기](https://docs.oracle.com/javase/8/docs/api/index.html?javax/naming/spi/Resolver.html)
에 따르면 리졸버의 기본 개념은 context 가 지원하지 못하는 부분에 대해 풀어서 지원해주는 말 그대로 'resolver' 역할을 수행한다.

쉽게 보면 context 는 기본적으로 제공해주는 method 라 봐도 무방하고, 여기에서 예외의 처리가 필요한 부분들을 resolver 가 처리를 해준다고 보면 된다.

구글링해서 가장 보편적으로 찾을 수 있는 예제는 ArgumentResolver 다.

Argument Resolver 는 스프링 라이프 사이클 내에서 뷰 -> 디스패처 -> 컨트롤러 에서 중간에 flow 를 인터셉트해서 파라미터에 대해
수정하여 controller 로 넘겨주는 역할을 한다.

기본적인 flow 는 컨트롤러는 httpRequest 에서 body 로부터 (필요 시 헤더로부터) 데이터를 받아 비즈니스 로직을 수행하는데
이 ArgumentResolver 를 구현해서 사용하면 임의의 데이터에서 추출 후 controller 에 넘기는 역할을 수행할 수 있다.

이런 spring 내장 resolver 말고도 본인이 직접 작성해서 사용하는 경우도 resolver 라 볼 수 있다.

