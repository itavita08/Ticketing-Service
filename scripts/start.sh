#!/usr/bin/env bash

user_name="itavita08"
image_name="ticketing"
image_tag="latest"

echo "새로운 Docker 이미지를 다운로드합니다."
sudo docker pull $user_name/$image_name:$image_tag

echo "새로운 Docker 컨테이너를 실행합니다."
port_mapping="8080:8080"
sudo docker run -d -p $port_mapping $user_name/$image_name:$image_tag