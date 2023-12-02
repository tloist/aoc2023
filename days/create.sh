#!/usr/bin/env bash

if [[ $# -eq 0 ]] ; then
  echo "No argument given, need to specify a date"
  exit 1
fi

mkdir -p $1/src/main/scala
mkdir -p $1/src/main/resources
mkdir -p $1/src/test/scala
mkdir -p $1/src/test/resources
touch $1/src/main/resources/inputA.txt
touch $1/src/test/resources/example.txt