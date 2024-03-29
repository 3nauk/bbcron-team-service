<h1 align="center"> Project Title </h1> <br>

<p align="center">
  Sample microservice description.
</p>


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)
- [API](#requirements)
- [Acknowledgements](#acknowledgements)




## Introduction

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![last build](https://github.com/3nauk/bbcron-spring-template/actions/workflows/maven.yml/badge.svg)
![example branch parameter](https://github.com/github/docs/actions/workflows/main.yml/badge.svg?branch=main)

TODO: Replace with introduction

## Features
TODO: Description of features

* Include a list of
* all the many beautiful
* web server features


## Requirements
The application can be run locally or in a docker container, the requirements for each setup are listed below.


### EGO
A running instance of [EGO](https://github.com/overture-stack/ego/) is required to generate the Authorization tokens and to provide the verification key.

[EGO](https://github.com/overture-stack/ego/) can be cloned and run locally if no public server is available. 


### Local
* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/download.cgi)


### Docker
* [Docker](https://www.docker.com/get-docker)


## Quick Start
Make sure the JWT Verification Key URL is configured, then you can run the server in a docker container or on your local machine.

### Configure JWT Verification Key
Update __application.yml__. Set `auth.jwt.publicKeyUrl` to the URL to fetch the JWT verification key. The application will not start if it can't set the verification key for the JWTConverter.

The default value in the __application.yml__ file is set to connect to EGO running locally on its default port `8081`.

### Run Local
```bash
$ mvn spring-boot:run
```

Application will run by default on port `1234`

Configure the port by changing `server.port` in __application.yml__


### Run Docker

First build the image:
```bash
$ docker-compose build
```

When ready, run it:
```bash
$ docker-compose up
```

Application will run by default on port `1234`

Configure the port by changing `services.api.ports` in __docker-compose.yml__. Port 1234 was used by default so the value is easy to identify and change in the configuration file.


## Testing
TODO: Additional instructions for testing the application.


## API

Listar Los endpoints asociados al microservicio Teams
{api.version}: v1

| API                                                               |       Description                             |       Request Body        |       Response Body       |   HTTP Status Code        |
| ---                                                               | ---                                           | ---                       | ---                       | ---                       |
| GET  https://api.bbcron.com/{api.version}/teams                   | Devuelve la lista de Teams                    |  None                     | Array Team                |     200/OK                |
| GET  https://api.bbcron.com/{api.version}/teams/{teamId}          | Devuelve el detalle de un team                |  None                     |  Team                     |     200/OK                |
| GET  https://api.bbcron.com/{api.version}/teams/users             | Devuelve la lista de usuarios de un Team      |  None                     | Array User                |     200/OK                |
| POST  https://api.bbcron.com/{api.version}/teams/                 | Crea un teams                                 |  TeamResponse             |  User                     |     200/OK                |
| DELETE  https://api.bbcron.com/{api.version}/teams/{teamId}       | Elimina un teams                              |  null                     |  null                     |     204/NO CONTENT        |
| PATCH  https://api.bbcron.com/{api.version}/teams/users/{userId}  | Añade un usuario a un Team                    |  None                     |  Team                     |     200/OK                |
| DELETE https://api.bbcron.com/{api.version}/teams/users/{userId}  | Elimina un usuario da un Team                 |  None                     |  None                     |     200/OK                |



## Acknowledgements
TODO: Show folks some love.