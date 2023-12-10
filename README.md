# Knight of 42

## 요약
- 주제 : Java GUI Programming을 이용한 텍스트 기반 RPG 게임 만들기
- 기간 : 2023.11.24 ~ 
- 사용 기술
  - Java 21
  - Swing Framework
  - JDBC

## 개발 방법
- TDD(Test Driven Development) 방식으로 개발
- Design Pattern 적용
- Git을 이용한 버전 관리

## 제약 사항
- MVC(Model-View-Controller) 패턴 적용
- Maven을 이용한 빌드
- Validation 체크(Hibernate Validator)
- Builder 패턴 적용

## 기능
- [ ] Console Mode와 GUI Mode를 선택할 수 있다.
    - [ ] Console Mode
    - [ ] GUI Mode
- [ ] 사용자는 다양한 유형의 영웅을 여러 명 보유할 수 있다.
- [ ] 사용자가 게임을 시작할 때, 사용자는 새 영웅을 생성하거나 기존 영웅을 선택할 수 있다.
    - [ ] 사용자는 영웅을 생성할 수 있다.
    - [ ] 사용자는 기존 영웅을 선택할 수 있다.
- [ ] 사용자는 다음과 같은 스탯을 갖는 영웅을 볼 수 있다.
    - [ ] Hero name
    - [ ] Hero class
    - [ ] Level
    - [ ] Experience
    - [ ] Attack -> Coding Skill
    - [ ] Defense -> Mental Strength
    - [ ] Hit Points -> Health
- [ ] 영웅 스탯은 영웅 레벨과 유물에 따른 영향을 받는다.
    - [ ] 영웅 레벨에 따른 스탯 영향
    - [ ] 유물에 따른 스탯 영향
- [ ] 유물는 세 가지 유형이 있다.
    - [ ] Weapon - 공격력 증가 => Keyboard
    - [ ] Armor - 방어력 증가 => Hoodie
    - [ ] Helm - Hit Points 증가 => Headset
- [ ] 영웅을 선택하면 게임이 시작된다.
- [ ] 영웅은 (레벨 - 1) * 5 + 10 - (레벨 % 2) 공식으로 계산된 크기의 정사각형 맵을 탐색해야 한다. - (레벨 7의 영웅은 39 X 39 맵에 배치된다.)
- [ ] 영웅의 초기 위치는 맵 중앙이다.
- [ ] 영웅은 맵의 경계선에 도달하면 승리한다.
- [ ] 영웅은 매 턴마다 다음 중 한 방향으로 한 위치를 이동할 수 있다.
    - [ ] 북
    - [ ] 동
    - [ ] 남
    - [ ] 서
- [ ] 지도가 생성되면, 다양한 힘을 가진 적들이 무작위로 배치된다.
- [ ] 영웅이 적을 만나면, 영웅은 적과 전투하거나 50% 확률로 도망칠 수 있다.
    - [ ] 영웅이 적과 전투할 경우, 전투를 시뮬레이션하여 사용자에게 전투 결과를 제시해야 한다.
    - [ ] 영웅이 도망칠 경우, 영웅은 이전 위치로 돌아간다. 
- [ ] 영웅이 전투에서 패배하면, 영웅은 죽고 미션은 실패한다.
- [ ] 영웅이 전투에서 승리하면, 경험치와 유물을 획득한다.
  - [ ] 경험치는 악당의 힘에 따라 달라지며, 다음 레벨 경험치에 도달하면 레벨이 올라간다.
  - [ ] 유물은 보관하거나 버릴 수 있다.
    - [ ] 유물의 품질과 확률은 적의 힘에 따라 다르다.
- [ ] 레벨 업은 다음 공식에 따른다. (레벨 * 1000 + (레벨 - 1)ˆ2 * 450)
  - [ ] 레벨 1 - 1000 경험치
  - [ ] 레벨 2 - 2450 경험치
  - [ ] 레벨 3 - 4800 경험치
  - [ ] 레벨 4 - 8050 경험치
  - [ ] 레벨 5 - 12200 경험치
  - [ ] 레벨 6 - 17350 경험치
- [ ] 게임은 두 가지 모드로 실행해야 한다.
  - [ ] 콘솔 모드
  - [ ] GUI 모드
- [ ] 사용자가 게임을 종료할 때, 상태는 텍스트 파일에 저장되고, 다음 게임을 시작할 때 불러올 수 있다.
  - [ ] 사용자는 게임을 저장할 수 있다.
  - [ ] 사용자는 게임을 불러올 수 있다.
- [ ] 텍스트 파일 대신 데이터베이스를 사용할 수 있다.
  - [ ] 사용자는 게임을 저장할 수 있다.
  - [ ] 사용자는 게임을 불러올 수 있다.
- [ ] 런타임에 Console Mode와 GUI Mode를 전환할 수 있다.
  - [ ] 사용자는 Console Mode로 전환할 수 있다.
  - [ ] 사용자는 GUI Mode로 전환할 수 있다.

## 게임 규칙
- [ ] 총 7개의 stage가 존재한다.
  - [ ] Stage 1 - Libft
  - [ ] Stage 2 - get_next_line, Born2beroot, ft_printf
  - [ ] Stage 3 - push_swap, minitalk, pipex, so_long, FdF, fract-ol
  - [ ] Stage 4 - Philosophers, minishell
  - [ ] Stage 5 - CPP Module, miniRT, cub3d, NetPractice
  - [ ] Stage 6 - ft_containers, ft_irc, Inception
  - [ ] Stage 7 - ft_transcendence
- [ ] coding skill, mental strength, health는 0 이상 42이하이다.
- [ ] coding skill, mental strengh, health는 도구 없이 27까지만 오를 수 있다.
- [x] 각 플레이어 타입 별로 다른 특성을 갖는다.
- [x] 레벨은 0부터 시작한다.
- [x] 0단계는 피신(보너스) 단계이다.
- [ ] 

## 기술 검증
- [ ] Swing Framework
- [ ] Maven
- [ ] JDBC
- [ ] Hibernate Validator

## 설계
- [ ] 화면 설계
- [ ] DB 설계
  - 플레이어 상태
  - 인벤토리
  - 게임 상태
- [ ] 클래스 설계

## 참고 자료
- [Swing Framework](https://www.oracle.com/technical-resources/articles/javase/swingappfr.html)
- [Hibernate Validator](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-gettingstarted)
- [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
- [Maven](https://maven.apache.org/users/index.html)