# Combo Microservice#

## How do I get set up? ###

### Prerequisites
   * [JDK 1.8](http://openjdk.java.net/projects/jdk8/)
   * [Maven-3](https://maven.apache.org/download.cgi)
   * [Git](https://git-scm.com/downloads)
   * [Docker 1.13.0 +](https://www.docker.com/products/overview)
   * [Docker Compose 1.18.0 +](https://docs.docker.com/compose/install/)
   * [SonarLint plugin for eclipse](http://www.sonarlint.org/eclipse/)
   
### Installation guides
   
* [Windows 10](#windows-10-installation-guide)
* [Linux (Ubuntu based)](#linux-installation-guide)

### Running the project

1 - Clone the repository and switch to directory

```bash
git clone ab-inbev@vs-ssh.visualstudio.com:v3/ab-inbev/GHQ_B2B_Delta/combo-service && cd combo-service/api
```

2 - Execute the command:

   * Commands to compose

```
docker-compose up
```

*This will create a Mongo container that should listen on localhost:10255*

3 - Import a new maven project to Spring Tool Suite

    File > Import... > Existing Maven Projects
    

4 - 'Run As' Spring Boot Application

### Running the e2e tests
```bash
mvn clean verify -Pe2e
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


### Install Docker

See [https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04)


### Install Docker-compose

See [https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-16-04](https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-16-04)


### Run docker without sudo (Optional)

```bash
sudo groupadd docker
sudo gpasswd -a ${USER} docker
sudo service docker restart
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

* [Guilherme Maretti](mailto:gmaretti@ciandt.com)
* [Bruna Soares](mailto:brunak@ciandt.com)
* [Pedro Vicentin](mailto:pedrov@ciandt.com)
* [Rafael Araujo](mailto:rafaraujo@ciandt.com)
