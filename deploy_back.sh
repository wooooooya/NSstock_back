#!/bin/bash

# =================================================================
# 변수 설정
# =================================================================
# 프로젝트 경로
REPOSITORY_PATH="/home/ec2-user/NSstock_back"
# EC2 홈 경로 (jar 파일이 scp로 전송되는 위치)
EC2_HOME="/home/ec2-user"

# 로그 파일 경로
DEPLOY_LOG_PATH="$REPOSITORY_PATH/deploy.log"
APP_LOG_PATH="$REPOSITORY_PATH/app.log"

# =================================================================
# 실행
# =================================================================
echo "===== 배포 시작 : $(date +%Y-%m-%d\ %H:%M:%S) =====" >> $DEPLOY_LOG_PATH

# 새로운 jar 파일 찾기 (가장 최근에 수정된 jar 파일)
JAR_FILE=$(ls -tr $EC2_HOME/*.jar | tail -n 1)
JAR_NAME=$(basename $JAR_FILE)

echo "> 새 jar 파일: $JAR_FILE" >> $DEPLOY_LOG_PATH

# 기존에 실행 중인 애플리케이션 종료
echo "> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_LOG_PATH
CURRENT_PID=$(pgrep -f "$REPOSITORY_PATH/.*\.jar")

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG_PATH
else
    echo "> 실행중인 애플리케이션 종료 (PID: $CURRENT_PID)" >> $DEPLOY_LOG_PATH
    kill -15 $CURRENT_PID
    sleep 5
fi

# 기존 jar 파일 백업 또는 삭제 (선택)
# 여기서는 기존 파일을 삭제하는 로직을 추가합니다.
if [ -f "$REPOSITORY_PATH/*.jar" ]; then
  echo "> 기존 jar 파일 삭제" >> $DEPLOY_LOG_PATH
  rm $REPOSITORY_PATH/*.jar
fi

# 새로운 jar 파일 이동
echo "> 새 jar 파일($JAR_NAME)을 $REPOSITORY_PATH 로 이동" >> $DEPLOY_LOG_PATH
mv $JAR_FILE $REPOSITORY_PATH/

# 새로운 애플리케이션 실행
echo "> 새 애플리케이션 배포" >> $DEPLOY_LOG_PATH
cd $REPOSITORY_PATH
nohup java -jar $JAR_NAME > $APP_LOG_PATH 2>&1 &

sleep 3

# 실행 후 프로세스 확인
NEW_PID=$(pgrep -f $JAR_NAME)
echo "> 새 애플리케이션 PID: $NEW_PID" >> $DEPLOY_LOG_PATH

echo "===== 배포 종료 : $(date +%Y-%m-%d\ %H:%M:%S) =====" >> $DEPLOY_LOG_PATH
