# Promotion Microservice #

B2B Microservice design to manage and retrieve promotions;

## Stoplight documentation ###
https://bees.stoplight.io/docs/promotion-api/

## Technologies ###

* Spring Boot
* RabbitMQ
* MongoDB


## How do I get set up? ##

### Prerequisites
   * [JDK 1.8](http://openjdk.java.net/projects/jdk8/)
   * [Maven-3](https://maven.apache.org/download.cgi)
   * [Git](https://git-scm.com/downloads)
   * [Docker](https://docs.docker.com/engine/install/)
   * [Docker-Compose](https://docs.docker.com/compose/install/)
   
### Installation guides
   
* [Windows 10](#windows-10-installation-guide)
* [Linux (Ubuntu based)](#linux-installation-guide)

### Setup containers

Open the folder local-env

Run docker-compose to setup the Rabbit and Mongo containers

```bash
cd local-env
docker-compose up -d
```

### Running the project

1 - Clone the repository and switch to directory

```bash
git clone https://dev.azure.com/ab-inbev/GHQ_B2B_Delta/_git/promotion-service
```

2 - Import a new maven project to your favorite IDE.

    File > Import... > Existing Maven Projects
    

3 - Build the project

```bash
mvn clean install
```

4 - Run with spring boot
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.fork=false
```

## Windows 10 Installation guide

With Java and Docker installed:

1 - Create a system variable called "JAVA_HOME" pointing to the JDK folder

2 - Add a new entry to the "PATH" system variable pointing to "%JAVA_HOME%\bin"

3 - In Docker's advanced settings, set memory to 4096 MB;

4- Run the following command on git bash
```
git config --global core.autocrlf false
```


## Linux Installation guide

### Install JDK 1.8

Run: 

```bash
sudo apt-get update
sudo apt-get install default-jdk
``` 

Checking if it was installed:

```bash
java -version
```

The output should be like this:

```bash
    openjdk version "1.8.0_121"
    OpenJDK Runtime Environment (build 1.8.0_121-8u121-b13-0ubuntu1.16.04.2-b13)
    OpenJDK 64-Bit Server VM (build 25.121-b13, mixed mode)
```

### Install Maven

Run: 

```bash
sudo apt-cache search maven
sudo apt-get install maven
``` 

Checking if it was installed:

```bash
mvn -version
```

The output should be like this:

```bash
    Apache Maven 3.3.9
    Maven home: /usr/share/maven
    Java version: 1.8.0_121, vendor: Oracle Corporation
    Java home: /usr/lib/jvm/java-8-oracle/jre
    Default locale: pt_BR, platform encoding: UTF-8
    OS name: "linux", version: "4.4.0-21-generic", arch: "amd64", family: "unix
```

### Install Git

```bash
sudo apt-get update
sudo apt-get install git
```

## Contribution guidelines ###

* Writing tests
    * use JUnit to cover main scenarios
* Open a feature branch from develop:
    * feature/\<jira-id\>-\<feature-name\>
* Code review
    * Open a pull request to merge your changes in develop
* Deploy feature branch in dev environment to test integrations.
* Other guidelines
    * Run sonarlint during development and fix issues before pull request.
    * Add JavaDocs for main methods and classes.
    * [Structure](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html)
* Updating branches
	* Always pull the code using rebase. You can set this as default using 
```git config --global pull.rebase true```

## Who do I talk to? ###

* [Lucas Grabert](mailto:lucas.grabert@ab-inbev.com)
* [Paulo Dutra](mailto:paulo.dutra@ab-inbev.com)
* [Diego Correa](mailto:diego.correa@ab-inbev.com)
