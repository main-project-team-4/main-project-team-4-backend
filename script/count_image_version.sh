dir_repository=./script/version.txt;
current_version=$(cat $dir_repository)

# 현재 버전 출력
echo $current_version;

# 버전의 마지막 숫자를 추출
last_digit=$(echo $current_version | awk -F. '{print $3}')

# 증가시킨 버전 생성
new_version="1.0.$((last_digit + 1))"

# 새 버전 저장
echo $new_version > $dir_repository
