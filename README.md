#Semester Project - Zachery Miller

A Java program that accepts a file containing CPU
temperature readings and calculates a Least Squares regression
and a piecewise interpolation for each core. The results are
saved to a file for each core in the main project folder.

The project can be compiled using the following command:

`./gradlew clean build`

The project can be run using the following command where
{File} is the file you wish to calculate.

`java -jar ./build/libs/semesterproject-1.0-SNAPSHOT.jar {File}`