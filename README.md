# Monitor Fire and Temperature Using an Arduino, Raspberry Pi and SAMI

Build a fire and temperature monitoring system using SAMI, off-the-shelf sensors, Arduino Uno, Raspberry Pi, and an Android application. 

Introduction
-------------

The blog post [Monitor Fire and Temperature Using an Arduino, Raspberry Pi and SAMI](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html) at http://blog.samsungsami.io/ describes what the system does and how it is implemented.

Check out [Demo video.](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html#demo-video)

This repository contains the following software:

 - A [Sketch](https://www.arduino.cc/en/Guide/Environment#toc2) program running on the Arduino Uno board
 - A Node.js script running on the Raspberry Pi
 - An Android application running on the Android phone

Android Application
-------------

The root directory of the application is `sample-android-SAMIIoTSimpleMonitor`.

Prerequisites:

 * [TooTallNate Java WebSockets](https://github.com/TooTallNate/Java-WebSocket)
 * Android SDK v21
 * Android Build Tools v21.1.1
 * Android Studio 1.0.1

Setup and Installation:

 1. Download and build [Java WebSockets](https://github.com/TooTallNate/Java-WebSocket). Copy `java_websocket.jar` to `app/libs` under the root of the application .
 2. Import `sample-android-SAMIIoTSimpleMonitor` as an existing Android Studio project in Android Studio IDE.
 3. If you have not connected the IoT system as a device in the SAMI User Portal, please consult [Step 1](//link_to_blog#step-1-connect-a-device-in-the-sami-user-portal) in the blog post. Then, in the [User Portal](https://portal.samsungsami.io), click the Settings icon of the your device. In the pop-up, get the device ID and token to replace `YOUR DEVICE ID` and `YOUR DEVICE TOKEN` in `SAMISession.java`.
 4. Build the app and deploy APK into your Android phone

Sketch Program for Arduino Uno Board
-------------

The code is located in `arduino` directory. Consult [Step 2: Set up the Arduino](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html#step-2-set-up-the-arduino) in the blog to set up the board and load the program to the board.

Nodejs Program for Raspberry Pi
-------------

The code is located in `raspberrypi` directory. Consult [Step 3: Set up the Raspberry Pi](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html#step-3-set-up-the-raspberry-pi) in the blog to install the packages and to run the program on the Pi.

More about SAMI
---------------

If you are not familiar with SAMI, we have extensive documentation at http://developer.samsungsami.io

The full SAMI API specification with examples can be found at http://developer.samsungsami.io/sami/api-spec.html

Peek into advanced sample applications at https://developer.samsungsami.io/sami/samples/

To create and manage your services and devices on SAMI, visit developer portal at http://devportal.samsungsami.io

License and Copyright
---------------------

Licensed under the Apache License. See LICENSE.

Copyright (c) 2015 Samsung Electronics Co., Ltd.
