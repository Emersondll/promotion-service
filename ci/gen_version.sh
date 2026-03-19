#!/bin/bash
# script to generate app version to be set on maven


echo "Building for $BUILD_SOURCEBRANCHNAME"

cd $1

APP_VERSION="$(printf 'VERSION=${project.version}\n0\n' | mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate | grep '^VERSION' | cut -d '=' -f2 | cut -d '-' -f1)"
if [ $BUILD_SOURCEBRANCHNAME != 'master' ]
    then
        DEPLOY_VERSION="${APP_VERSION}_${BUILD_BUILDID}"-$BUILD_SOURCEBRANCHNAME
    else
        DEPLOY_VERSION="${APP_VERSION}_${BUILD_BUILDID}"
fi

echo "##vso[task.setvariable variable=APP_VERSION]$APP_VERSION"
echo "##vso[task.setvariable variable=DEPLOY_VERSION]$DEPLOY_VERSION"
echo "Application version $APP_VERSION"
echo "Deploy version $DEPLOY_VERSION"
echo "DEPLOY_VERSION=${DEPLOY_VERSION}" > version.properties
echo "APP_VERSION=${APP_VERSION}" >> version.properties
echo "${DEPLOY_VERSION}" > version.txt
