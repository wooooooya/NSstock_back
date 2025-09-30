#!/bin/bash

# 배포 로그 보기 좋게
echo "==== [배포 시작] $(date) ===="

# 1. 백엔드 프로젝트 디렉토리 이동
cd /home/ec2-user/NSstock_back || exit

# 2. Git 작업 공간 정리
echo "==== Git 초기화 ===="
git reset --hard
git clean -fd

# 3. 최신 코드 가져오기
echo "==== 최신 코드 가져오기 ===="
git pull origin main

# 4. 기존 빌드 결과물 삭제
echo "==== 기존 빌드 결과물 삭제 ===="
rm -rf build/

# 5. application.properties 복사
# (config 디렉토리에 따로 관리)
echo "==== application.properties 복사 ===="
cp /home/ec2-user/config/application.properties /home/ec2-user/NSstock_back/src/main/resources/application.properties

# 6. 빌드 수행
echo "==== Gradle 빌드 시작 ===="
./gradlew bootJar

# 7. 백엔드 서비스 재시작
echo "==== 서비스 재시작 ===="
sudo systemctl restart backend.service

echo "==== [배포 완료] $(date) ===="
