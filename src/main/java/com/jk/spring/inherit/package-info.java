package com.jk.spring.inherit;

/*
최초 CommonService 를 구축하며 read 와 write 를 만들었다, 그런데 이후 needs 가 생겨서 별도의 service 로 update 와 list 가 필요해졌다.
하지만 controller 단에서는 여전히 이전 service 를 재사용하면서 새로운 service 도 같이 한번에 사용하고 싶어졌다

일 경우에 CommonAService 와 같이 CommonService 를 상속받고 AInterface 로 추상화 하여 사용하면 코드도 재사용하면서 다른 service 를 만들 수 있다.
관심사를 분리함과 동시에 이전 코드 재사용이라 볼 수 있다.
물론 필요한 경우엔 override 를 통해 business logic 을 변경 할 수 있다.
 */