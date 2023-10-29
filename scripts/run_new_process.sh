#!/bin/bash
# 현재 포트 번호 확인
CURRENT_PORT=$(cat /home/ubuntu/app/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0
echo "> 현재 실행 중인 WAS의 포트는 ${CURRENT_PORT} 입니다."
# 새로운 포트 번호 설정
if [ ${CURRENT_PORT} -eq 8081 ]; then
    TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo "> Nginx가 현재 어떤 WAS에 연결되어 있지 않습니다."
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi
# 새로운 WAS 프로세스 시작
nohup java -jar -Dserver.port=${TARGET_PORT} /home/ubuntu/app/demo-0.0.1-SNAPSHOT.jar > /home/ubuntu/nohup.out 2>&1 &
echo "> 이제 새로운 WAS가 ${TARGET_PORT} 포트에서 실행 중입니다."
exit 0