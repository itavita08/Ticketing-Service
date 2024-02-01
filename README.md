# 🎫 티켓팅 서비스

🐮🐶 **소개**

- 공연 티켓팅 서비스
- nGrinder를 이용한 부하 테스트

📆 **기간**

- 2023.11.22 ~ 2024.01.23

🧑‍💻 **역할**

- 1인 개인 프로젝트

📝 **개발 스택 및 사용 툴**

- 👾 Back
      
    <img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat&logo=&logoColor=white"> <img src="https://img.shields.io/badge/JPQL-6DB33F?style=flat&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Java-F37C20?style=flat&logo=&logoColor=white">
   
- ☁ Cloud   

    <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/AWS S3-569A31?style=flat&logo=amazons3&logoColor=white"> <img src="https://img.shields.io/badge/AWS LoadBalancer-FF9900?style=flat&logo=&logoColor=white"> 
   
- 💾 DB / Cache

    <img src="https://img.shields.io/badge/AWS RDS(MySQL)-527FFF?style=flat&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/AWS ElastiCache(Redis)-A61200?style=flat&logo=&logoColor=white">
   
- 🕋 CI / CD

    <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=flat&logo=githubactions&logoColor=white"> <img src="https://img.shields.io/badge/AWS CodeDeploy-76D04B?style=flat&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white">
   
- 🖥️ Monitoring / Test       

    <img src="https://img.shields.io/badge/Pinpoint-02A8EF?style=flat&logo=&logoColor=white"> <img src="https://img.shields.io/badge/nGrinder-FF9900?style=flat&logo=&logoColor=white">   
   
   
- ⚒️ Tool

    <img src="https://img.shields.io/badge/IntelliJ-6A5FBB?style=flat&logo=&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/Gitkraken-179287?style=flat&logo=gitkraken&logoColor=white">

🌐 **URL**

Git : https://github.com/itavita08/Ticketing-Service

## ⚙️ 아키텍쳐


![아키텍쳐](https://github.com/itavita08/Webtoon_App/assets/105635205/f2c0eee2-4b06-4b04-b4e6-f4c3a4037a2b)

## 🔩 ERD


![erd](https://github.com/itavita08/Webtoon_App/assets/105635205/cff636d5-2dae-49df-a136-237edff1c0f3)

## ✏️ 기술적 의사 결정


1. Redis 
    - In-Memory 데이터 저장 방식을 사용하므로 디스크 기반 저장에 비해 훨씬 빠른 응답 속도를 제공합니다.
    - Redis의 단일 스레드 구조는 모든 명령을 순차적으로 처리하며, 단일 연산에 대해서는 원자성이 보장됩니다. 이로 인해 티켓팅 서비스에서 중요한 데이터 일관성 및 무결성을 유지하는 데 도움이 되며, 시스템 안정성을 향상할 수 있습니다.
    - 동일한 데이터를 데이터베이스를 통해 반복적으로 조회하는 경우 데이터베이스에 부하가 발생하여 속도가 더 느려집니다. 따라서 Redis 캐시를 사용하여 데이터베이스 부하를 줄이고 응답시간을 개선하기 위해 선택했습니다.
2. JPQL
    - JPQL은 JPA의 쿼리 언어로 널리 사용되며, 객체지향적 접근은 데이터베이스와 객체 간의 매핑을 효과적으로 수행합니다.
    - JPA와의 통합은 데이터베이스 작업을 효율적으로 수행할 수 있도록 도와주며, 타입 안정성은 컴파일 시점에서 오류를 찾아내어 안정적인 쿼리 작성을 보장합니다.
    - JPQL의 간결하고 가독성 있는 문법은 복잡한 검색 요구 사항에도 쉽게 대응할 수 있도록 도와줍니다.
    - 이러한 이유로 JPQL을 사용하여 프로젝트의 쿼리작성 효율성과 유지보수성을 높이기 위해 선택했습니다.
3. GitHub Action
    - GitHub와 통합되어 있어서 별도의 CI/CD 도구를 사용할 필요 없이 프로젝트에 쉽게 적용할 수 있어서 선택했습니다.
4. Docker
    - GitHub Actions를 활용한 CI/CD 구현으로 애플리케이션 배포 및 확장 과정이 빠르고 효율적으로 진행이 가능합니다.
    - Load Balancer을 사용하면서 지속적인 EC2에 배포를 하고 교체가 발생하면서 배포 환경 간의 차이가 생겨 심각한 서비스 장애를 초래할 수 있습니다. Docker을 사용하여 애플리케이션과 모든 의존성을 컨테이너로 패키징 함으로써 개발, 운영환경이 일관된 상태를 유지하기 위해 선택했습니다.
5. Pinpoint
    - nGrinder에서도 TPS, Mean Time Test 등 모니터링을 할 수 있지만 Pinpoint에서 병목지점을 확인할 수 있어 Pinpoint를 선택했습니다.

## 📖 시나리오

> 👉  티켓팅 사이트인 인터파크 티켓 사이트를 참고   
    - 인터파크 티켓 2023년 6월 기준 월간 이용자 수 1713만명   
        티켓팅 사이트는 사람들이 많이 몰렸을 경우 서버가 터지는 경우가 많으므로 최고 트래픽 상황을 가정


- **1일 총 유저 수**
    - DAU(하루 중 중복 없는 순수 사용자 수) x 1명당 1일 평균 요청 수
    - 하루 이용자 수 20만명(예측) x 요청 수 최소 5번 이상(예측)   
    - `100만`
- **1일 평균 rps**
    - 1일 총 요청 수 / 하루 12시간을 초로 환산
    - 100만 / 43200
    - `23 rps`
- **최대 트래픽**
    - 1일 최대 rps
    - 1일 평균 rps x 피크 시간 집중률
    - 피크 시간대에 집중률이 평균 보다 10배많다고 예측
    - 23 rps x 10
    - `230 rps`
- **Vuser**
    - 목표 rps x (한번의 시나리오를 완료하는데 걸리는 시간 / 시나리오 당 요청 수)
    - 한번의 시나리오당 목표 시간 = 1500ms, 시나리오당 요청 수(회원가입, 로그인, 예매) 3번
    - 230 x (1.5 / 3)
    - `115`

## 🐞 부하 테스트


1. **로컬(맥북 에어 M2)환경**
    - Redis X, fetch join 미사용
        
        ![스크린샷 2023-12-08 오전 12 46 35](https://github.com/itavita08/Webtoon_App/assets/105635205/20337cf1-f956-455c-83d8-ca6d5328c549)
        
        - Pinpoint를 통해 getConnection()에서 병목 지점 확인
            - Hikari maximum pool size 여러번 변경으로 최적의 값 120
            - DB max connection 변경
        - 쿼리문에서 병목 지점 확인
            
            ![스크린샷 2023-12-08 오전 3 09 48](https://github.com/itavita08/Webtoon_App/assets/105635205/80091353-e533-47eb-b8c0-de010a2f963b)
            
    - Redis X, fetch join 사용
        
        ![스크린샷 2023-12-08 오전 2 52 08](https://github.com/itavita08/Webtoon_App/assets/105635205/a8cf5b54-d20f-4209-afd3-a79399a1d3f1)
        
        - `6797ms → 2996ms 단축`
    
    - Redis O, fetch join 사용
        - Redis 사용 후 테스트 결과 별차이가 없어서 Pinpoint 확인
        - 부하 툴 및 애플리케이션, 모니터링을 로컬에서 한번에 진행하다보니 CPU 사용률이 100프로라 Redis를 사용해도 별 차이가 없다라고 생각했습니다.
    
2. **AWS 환경**
    - EC2(Arm) t4g.small(2 vCPU, 2 GiB 메모리) 1대
        
        ![스크린샷 2024-01-25 오후 7 31 47](https://github.com/itavita08/Webtoon_App/assets/105635205/94cec3ff-7ac4-4b47-8f28-91d293c8ba37)
        
        ![스크린샷 2024-01-24 오전 1.05.21.png](https://github.com/itavita08/Webtoon_App/assets/105635205/3fbd8c9f-155a-4bd8-b24a-35a3c3ec3146)
        
        - `11883 ms`
        - `CPU 사용률 100%`
        
    - EC2(Arm) t4g.small(2 vCPU, 2 GiB 메모리) 4대, AWS Load Balancer 사용
        
        ![스크린샷 2024-01-25 오후 8 26 34](https://github.com/itavita08/Webtoon_App/assets/105635205/c3e8cc4e-c231-4fc7-96d7-2b8c891ae71e)
        
        - `11883 ms → 3078 ms`
        - TPS 증가 및 처리시간은 줄였지만 여전히 CPU 사용률 100퍼
        
    - EC2(Arm) t4g.medium(2 vCPU, 4 GiB 메모리) 4대, AWS Load Balancer 사용
        - 스케일 업을 하였지만 CPU가 아닌 메모리만 늘어난 스케일 업으로 처리시간에 큰 영향을 주지 못했다.
        - CPU 사용률 100퍼
    - EC2(Arm) t4g.xlarge(4 vCPU, 16 GiB 메모리) 4대, AWS Load Balancer 사용
        - CPU 스케일 업되는 t4g.xlarge 선택
            
            ![스크린샷 2024-01-25 오후 9 51 40](https://github.com/itavita08/Webtoon_App/assets/105635205/eae49bc1-3115-4018-9e9d-2c52c5920a5c)
            
            - `3078 ms → 1487 ms`
            - **목표로 했던 1.5s 달성**
