# FRC-2018-PowerUp
[![Build Status](https://travis-ci.org/Team1091/FRC-2018-PowerUp.svg?branch=master)](https://travis-ci.org/Team1091/FRC-2018-PowerUp)

This is our First Robotics code for 2018.

This year we are trying a gradle build via GradleRio.

We are also trying to actually write some tests.  We have been meaning to for a while, but they always get prioritized out of the build season.

[Team Website](http://team1091.com)

## Sub Projects

We organize our code into subprojects to kinda split it up

* robot - Our robot code. Java using gradlerio to deploy.

* planner - Can plan a path across the field using [Djikstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).  
We avoid walls by making driving near them cost more.  Allies can be drawn into a png to dodge. We encode rotations as a 3rd dimension, so that we can account for cost and beginning and ending state.
Removed mid competition, to make us more predictable.

* vision - Vision that can find the center of blue, red, or yellow on the screen.

* peripheral - Vision that extends around us.  Not deployed for competition.



## Robot Commands
We use gradle this year, which means you can run these tests through intellij or from the command line.

Check out the code, change the the directory, and run the ./gradlew or gradlew.bat with a command.

```bash

# To build and deploy code to the robot
gradlew.bat :compileAndDeploy

# To build and run tests
gradlew.bat robot:build

# To just build, in the case that you broke all the tests
gradlew.bat robot:assemble

# To run all tests
gradlew.bat test

# To run check the log
gradlew.bat riolog

# To start shuffleboard
gradlew.bat shuffleboard

```

Post season [Retrospective](./retrospective.md)