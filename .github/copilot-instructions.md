# Project Overview

이 프로젝트는 Jetpack Compose 기반의 안드로이드 앱으로, 멀티모듈, MVI, 클린 아키텍처를 적용합니다.

## Folder Structure (Modules)

- `app`: 앱 엔트리, 네비게이션 그래프, DI 엔트리포인트.
- `build-logic` : Gradle Convention Plugin, 버전 카탈로그, 공통 플러그인 관리.
- `core:common`: 공용 유틸.
- `core:designsystem`: 테마, 컴포넌트, 아이콘/일러스트.
- `core:network`: Retrofit/OkHttp 클라이언트, 인터셉터, DTO 어댑터.
- `core:domain`: 엔터티, 유즈케이스, 리포지토리 인터페이스.
- `core:data`: 리포지토리 구현, Remote/Local 데이터소스.
- `feature:<name>`: 각 기능 화면. MVI, Compose UI.

## Libraries and Frameworks

- Kotlin, Coroutines, Flow
- Jetpack Compose, Material3, Navigation
- Hilt (DI)
- Retrofit, OkHttp (network)
- Coil (이미지)
- Build: Gradle Kotlin DSL, ktlint

## Coding Standards

- Kotlin 컨벤션 준수
- 모듈 경계 준수: 상위 계층이 하위 구현에 의존하지 않도록(의존 방향: app/presentation(feature) → domain → data).
- MVI
    - Contract 분리
    - Reducer는 순수함수로 상태 전이만 담당.
    - ViewModel은 UI 스레드 안전성을 보장하고, 비즈니스 로직은 UseCase로 위임.
- Compose
    - State hoisting, 안정성(Stable/Immutable) 고려, 불필요한 recomposition 방지.
    - Modifier 순서 일관성, Preview 제공, 접근성 준수.

## Pull Request Review Checklist (Copilot가 검증)

- 아키텍처
    - 모듈 간 의존 방향, MVI 패턴 검증, 책임 분리 검증.
- 코드 품질
    - 불변성, null-safety, Suspend/Flow 적절성, 코루틴 스코프/디스패처 관리.
- Compose
    - stable parameter, remember 적절성, 리스트 성능(key, lazy 아이템), Modifier/레이아웃 과도한 중첩 여부.
- 데이터
    - 에러/리트라이/캐싱 전략, 네트워크 타임아웃/재시도/백오프.
- 보안/배포
    - API 키/시크릿 노출 금지, 릴리즈 프로가드/리소스 최적화, 앱 사이즈/성능 체크.
- 기타
    - 리뷰는 한국어로.
    - compose, kotlin 안티 패턴 고려.
    - compose, kotlin 성능 고려.