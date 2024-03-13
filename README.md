# D208 Sixth Sense

- 서비스명 : 성식당 (SungChef)



# Git Flow

- master : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치

| 출처 : [우린 Git-flow를 사용하고 있어요 | 우아한형제들 기술블로그](https://techblog.woowahan.com/2553/)

- develop 변경사항을 feature로 가져오기 (Optional)

- develop 브랜치에 upstream/feature-user 브랜치를 merge 합니다

- upstream/feature-user 기능이 merge된 develop를 upstream에 push 합니다

- 출시 담당자는 QA를 시작하기 위해 먼저 release 브랜치를 생성하고 upstream에 push하여 release 브랜치를 공유

- QA 중 버그 수정하기

- release 브랜치에서 버그 티켓에 대한 브랜치를 생성합니다

- 버그를 수정합니다

- 작업 브랜치에 버그 수정 사항을 커밋합니다

- 작업 브랜치를 origin에 push 합니다

- Github에서 bfm-101_bug_login_id_max_length 브랜치를 release-1.0.0에 merge 하는 Pull Request를 생성합니다

- 동료에게 리뷰 승인을 받은 후 자신의 Pull Request를 merge 합니다.

### 앱 출시

release 브랜치를 최신 상태로 갱신합니다

release 브랜치를 develop 브랜치에 merge 합니다

develop 브랜치를 upstream에 push 합니다

release 브랜치를 master 브랜치에 merge 합니다

1.0.0 태그를 추가합니다

master 브랜치와 1.0.0 태그를 upstream에 push 합니다

## 작업을 할 때 지켜야할 서로 간의 약속

저희는 작업을 할 때 지켜야 할 몇 가지 약속이 있습니다.

1. 작업을 시작하기 전에 JIRA 티켓을 생성합니다.
2. 하나의 티켓은 되도록 하나의 커밋으로 합니다.
3. 커밋 그래프는 최대한 단순하게 가져갑니다.
4. 서로 공유하는 브랜치의 커밋 그래프는 함부로 변경하지 않습니다.
5. 리뷰어에게 꼭 리뷰를 받습니다.
6. 자신의 Pull Request는 스스로 merge 합니다.

처음에는 master와 develop 브랜치가 존재합니다. 물론 develop 브랜치는 master에서부터 시작된 브랜치입니다. develop 브랜치에서는 상시로 버그를 수정한 커밋들이 추가됩니다. 새로운 기능 추가 작업이 있는 경우 develop 브랜치에서 feature 브랜치를 생성합니다. feature 브랜치는 언제나 develop 브랜치에서부터 시작하게 됩니다. 기능 추가 작업이 완료되었다면 feature 브랜치는 develop 브랜치로 merge 됩니다. develop에 이번 버전에 포함되는 모든 기능이 merge 되었다면 QA를 하기 위해 develop 브랜치에서부터 release 브랜치를 생성합니다. QA를 진행하면서 발생한 버그들은 release 브랜치에 수정됩니다. QA를 무사히 통과했다면 release 브랜치를 master와 develop 브랜치로 merge 합니다. 마지막으로 출시된 master 브랜치에서 버전 태그를 추가합니다.
