#!/usr/bin/env bash

container_ids=$(sudo docker ps -q)

if [ ! -z "$container_ids" ]; then
  echo "실행 중인 Docker 컨테이너를 종료하고 삭제합니다."

  sudo docker stop $container_ids
  sudo docker rm $container_ids
else
  echo "실행 중인 Docker 컨테이너가 없습니다."
fi

image_ids=$(sudo docker images -q)

if [ ! -z "$image_ids" ]; then
  echo "모든 Docker 이미지를 삭제합니다."

  sudo docker rmi -f $image_ids
else
  echo "삭제할 Docker 이미지가 없습니다."
fi