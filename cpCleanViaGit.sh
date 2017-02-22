#!/bin/ksh

MASTER=$1
TMP_DIR=$2
REPO_NAME=$3
REMOTE_REPO=${REPO_NAME}.git
CUR_DIR=`pwd`

echo MASTER: $MASTER
echo TMP_DIR: $TMP_DIR
echo REPO_NAME: $REPO_NAME
echo REMOTE_REPO: $REMOTE_REPO
echo CURR_DIR: $CUR_DIR

cd $MASTER
MASTER=`pwd`
CUR_BRANCH=`git branch | sed -e '/^ /d' -e 's/^..//'`

cd $TMP_DIR                               &&
mkdir ${REMOTE_REPO}                      &&
git init --bare $REMOTE_REPO              &&
cd $REMOTE_REPO                           &&
REPO=`pwd`                                &&
cd $MASTER                                &&
git remote add tmpRepo $REPO              &&
git push tmpRepo $CUR_BRANCH              &&
git remote remove tmpRepo                 &&
cd $TMP_DIR                               &&
git clone -b $CUR_BRANCH $REPO
