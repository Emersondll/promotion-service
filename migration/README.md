# Promotion Migration #

Application created to creating field promotionPlatformId in the promotion collections.

This module was created using Kotlin with Spring boot.

## Dependencies

* [JDK 11](https://www.azul.com/downloads/zulu-community)
* [Maven-3](https://maven.apache.org/download.cgi)
* [Git](https://git-scm.com/downloads)
* [Docker 1.13.0 +](https://www.docker.com/products/overview)
* [Bash](https://www.gnu.org/software/bash/)
* [Mongo-Shell](https://www.mongodb.com/docs/v4.4/mongo/)

## Introduction

To perform the creating the field promotionPlatformId, we will have the following steps:

* [Get a user to perform the migration](#get-user-to-migrate).
* [Configure the environment to run the script](#configure-environment).
* [Prepare the consumer module to migration](#prepare-consumers).
* [Run the migration script](#execute-the-script).
* [Validate the results](#check-the-results).
* [Enable consumers](#enable-consumers).

## Get user to migrate

To get a temporary mongo user, just follow the steps in this [link](https://ab-inbev.atlassian.net/wiki/spaces/PKB/pages/2919760206/MongoDB+Atlas+-+Temporary+personal+user+access).

The promotion cluster is the PROD-PILSEN.

## Configure environment

To setup the environment, just clone the repository [promotion-service](https://ab-inbev.visualstudio.com/GHQ_B2B_Delta/_git/promotion-service).

And navigate to the migration folder.

You will need to install some [dependencies](#dependencies) to run.

With all dependencies installed, run the `./setup.sh` script

If the script is executed locally, everything is ready for the next steps.
We also have an option to run the script in a vm on azure, for that to contact the cloud-ops team and request the creation of the vm and do the same steps there.

## Prepare consumers

Deactivate the consumers of the country that will be migrated and activate the country as a multi-vendor.

In [bees-microservices repository](https://ab-inbev.visualstudio.com/GHQ_B2B_Delta/_git/bees-microservices?path=/charts/promotion-consumer-service&version=GBprod&_a=contents), execute the steps below to prepare and activate sync mechanism for the country migrated.

### Create new multi vendor deployments

Only if the country have a specific structure of deployment, will be needed to create new deployments for the country.

All the current countries did not have this need for a specific deployment structure configuration.

For this countries, AR, BR, CA, CL, CO, DO, EC, GB, HN, MX, PA, PE, PY, SV, TZ, US, UY, ZA
Only AR have specific configuration.

### Deactivate country

Edit the values.yaml file and add the country in zones.deactivated from each deployments properties that did not have the zones.activated set.
```yaml
    zones:
      activated:
      deactivated: AR, COUNTRY_TO_MIGRATE
```
If the country has value in the zones.activated property its no need to set the deactivated property.

### Scale down pods

Only if the country migrated has specific deployments, scale down pods setting pod.hpa.enabled has false, pod.hpa.minReplicas and pod.hpa.maxReplicas as 0.
```yaml
    pod:
      hpa:
        enabled: false
        minReplicas: 0
        maxReplicas: 0
```

## Execute the script

Execute the [script](migrate.sh) using `./migrate.sh`.

The script will open an interface that will ask for some inputs.

### Country

The country that will be performed the migration

```
Starting promotion migration
Enter the country to migrate: DO
```

### Database connection
Mongodb connection string of the environment
```
Paste the database connection string: mongodb+srv://<user>:<password>@<host>/<parameters>
```

### Operation
Migration operation

```
Choose the migration operation.
 1 - Migration.
 2 - Check migration result. 
: 
```

We have two options, perform the migration or check the migration results.

Choosing option one will go to the next steps of migration.

### Check the amount to migrate

After choosing the entity, you will be asked if you want to check the amount to be migrated.
If you already have the value, this step can be skipped.

If you choose promotion, the following information will be displayed:

```
Do you want to check the amount of registers to migrate (y/n):
```

The total it is the total of register inside the collection.

Total to migrate it is the amount that will be migrated.


### Calculating the next values

In the next input of script will be asked for:

* [amount to migrate](#amount-to-migrate)
* [page size](#page-size)
* [node amount](#node-amount)
* [thread pool size](#thread-pool-size)

We will need the values obtained in the value [checking step](#check-the-amount-to-migrate) to be used in the next inputs.

For example, we have a 50.000 promotions to migrate.
Our amount to migrate will be 50000.
The page size is a maximum of 500.
With this we will have 50.000 promotions divided into 500 elements per page. In this case, totaling 100 pages. With 100 pages we can divide between 5 nodes that in the end we will have 20 pages per application.
With these 20 pages per application, we have the option of dividing it among some threads as well, but with that amount we don't have the need, so we could set it to 1.

Another smaller example, its 5000 promotions. 
We could have a page size of 500, and just one node and thread that will run really fast.

Now an example of a bigger scenario where we have a 60 million promotions to migrate. 
In this case we can keep the page size at 500, which is the maximum suggested value.
Have 6 nodes, which is the maximum recommended.
And 10 threads per node which is the maximum recommended.
With that we would have 10 million promotions for each node, and each node's thread fetching 500 records per query.

For promotion migrations, for the most part we won't need more than 1 node and a thread, but already promotion account we will probably need to use the maximum values of each entry.

And all values must be without special characters, like ".,", for example, if the amount to migrate is 50.000.000 must be input 50000000.

### Amount to migrate

This information its important because we use it to calculate the division of registers between the nodes.

When the migration entity its promotion, the value will be the "Total to migrate", and when its promotion account will be the "The higher amount".

This values can be got in a previous step, in the [check amount](#check-the-amount-to-migrate) step.

### Page size

The quantity of register search by each thread and node.

The max recommended value its 6.

### Node amount

The amount of application running in parallel.

The max recommended value its 6.

### Thread pool size

The amount of threads opened in each application.

The max recommended value its 6.

After set this value, the script will start.

## Check the results

After run the steps above the script will start and create the container.

The logs can be consulted in the folder logs in the same path then the script.

Or they can be consulted using docker commands like, `docker logs -f promotion-migration-$node`

When the migration ends, all the containers will be deleted and the logs persisted in the folder logs.

Inside the log folder, will have the structure COUNTRY/ENTITY/NODE/app.log

It is possible to run the script again and choose the operation to [check](#check-the-results) the results, and see the amount of registers migrated.

It is possible to open the database and check the registers as well.

## Enable consumers

After the migration is complete, you will need to enable the consumer.

In the [bees-microservices repository](https://ab-inbev.visualstudio.com/GHQ_B2B_Delta/_git/bees-microservices?path=/charts/promotion-consumer-service&version=GBprod&_a=contents), execute the steps below to turn on the consumers.

If the country migrated has specific deployments, scale up the pods and the hpa in values.yaml file for each deployment.
```yaml
pod:
  hpa: 
    enabled: true
    minReplicas: 10
    maxReplicas: 35
```

Edit the values.yaml file and remove the country in the deactivated countries previously set.
```yaml
    zones:
      activated:
      deactivated: AR
```

If the country did not have a specific structure of deployment, enable the general sync listener (if not enabled yet).

```yaml
pods:
  javaOpts: -XX:+UseContainerSupport -XX:InitialRAMPercentage=80.0 -XX:MaxRAMPercentage=80.0
  replicas: 30
  resources:
    limits:
      memory: 2048Mi
      cpu: 1000m
    requests:
      memory: 1024Mi
      cpu: 500m
    readinessProbe:
      initialDelaySeconds: 90
      timeoutSeconds: 3
      failureThreshold: 5
      periodSeconds: 30
    livenessProbe:
      initialDelaySeconds: 90
      failureThreshold: 3
      timeoutSeconds: 1
      periodSeconds: 10
  hpa:
    enabled: true
    minReplicas: 20
    maxReplicas: 20
    averageUtilizationMemory: 70
    averageUtilization: 50
```

If the country have a specific structure of deployment, edit or add a new value inside deployments property, follow the same pattern that are made in AR.

```yaml
deployments:
  ar:
    hpa:
      enabled: true
      minReplicas: 5
      maxReplicas: 10
      averageUtilizationMemory: 70
      averageUtilization: 50
```

## Important Notes

* For execute the script will be needed a connection string with write access.
* The script was tested only in linux environment.
* The collection is multi-vendor and the entity is promotion.
* For execute the script worth check the hardware of the cluster before, in some cases don't worth create several threads and nodes, because its can overhead the server and slow the operations.

## Ideas for improvement

* Add the script in a pipeline.
* Add more ways to check the results.