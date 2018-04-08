# FRC-2018-PowerUp
[![Build Status](https://travis-ci.org/Team1091/FRC-2018-PowerUp.svg?branch=master)](https://travis-ci.org/Team1091/FRC-2018-PowerUp)

This is our First Robotics code for 2018.

This year we are trying a gradle build via GradleRio.

We are also trying to actually write some tests.  We have been meaning to for a while, but they always get prioritized out of the build season.

[Team Website](http://team1091.com)

## Sub Projects

We organize our code into subprojects to kinda split it up

* robot - Our robot code. Java using gradlerio to deploy.

* planner - Can plan a path across the field using Djikstra's algorithm.  We avoid walls by making driving near them cost more.  
Allies can be drawn into a png to dodge. 
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

## 2018 Retrospective

* Programming team needs to tweak, and time for vision

* Drive team needs time to practice and train alts.

* Encoders are troublesome.  Weak wires need protection.  Print them out for easy debugging.

* Powering motors in static positions will cause brownout.  Use a worm gear if
you need it.

* center of gravity is important for acceleration.  
I have not seen one of our robots that would not flip going from 1.0 power to -1.0 power.

## 2019 plans

* We need to be driving at the end of the second week, 
and testing mechanisms by the end of week 4.  Probably should bring back the 
shoebox plan, where each mechanism has a defined area and is bolted together on top of the drive train.

* If the challenge requires picking up stuff, or putting in a slot, we shoud value strafing over pushing and speed.
  Alignment will take most of your driving time.

* PID loops for driving certain distances and gyroscope/accelerometer should be high priority research.

* If we are going to enact the FAATT secret plan, we need to be able to simulate the field prior to having a robot.