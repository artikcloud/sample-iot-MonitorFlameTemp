# Monitor Fire and Temperature Using an Arduino, Raspberry Pi and ARTIK Cloud

Build a fire and temperature monitoring system using ARTIK Cloud, off-the-shelf sensors, Arduino Uno, Raspberry Pi, and an Android application. 

Introduction
-------------

The blog post [Monitor Fire and Temperature Using an Arduino, Raspberry Pi and ARTIK Cloud](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html) at http://artik.io/blog/cloud describes what the system does and how it is implemented.

Check out [Demo video.](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html#demo-video)

This repository contains the following software:

 - A [Sketch](https://www.arduino.cc/en/Guide/Environment#toc2) program running on the Arduino Uno board
 - A Node.js script running on the Raspberry Pi
 - An Android application running on the Android phone

Android Application
-------------

The root directory of the application is `android-simple-monitor`.

Prerequisites:

 - <a href="http://developer.android.com/sdk/index.html" target="_blank">Android Studio</a>
 - <a href="https://github.com/artikcloud/artikcloud-java" target="_blank">ARTIK Cloud Java/Android SDK</a>

Setup and Installation:

 1. Build ARTIK Cloud's Java/Android SDK libraries at <a href="https://github.com/artikcloud/artikcloud-java" target="_blank">**GitHub**</a>. The library JAR files are generated under the `target` and `target/lib` directories of the SDK Maven project.
 2. Copy all above library JAR files to the directory `/app/libs` in the Android project.
 2. Import `android-simple-monitor` as an existing Android Studio project in Android Studio IDE.
 3. If you have not connected the IoT system as a device in the ARTIK Cloud User Portal, please consult [Step 1](//link_to_blog#step-1-connect-a-device-in-the-sami-user-portal) in the blog post. Then, in the [User Portal](https://www.artik.cloud), click the Settings icon of the your device. In the pop-up, get the device ID and token to replace `YOUR DEVICE ID` and `YOUR DEVICE TOKEN` in `ArtikCloudSession.java`.
 4. Build the app and deploy APK into your Android phone

Sketch Program for Arduino Uno Board
-------------

The code is located in `arduino` directory. Consult [Step 2: Set up the Arduino](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html#step-2-set-up-the-arduino) in the blog to set up the board and load the program to the board.

Nodejs Program for Raspberry Pi
-------------

The code is located in `raspberrypi` directory. Consult [Step 3: Set up the Raspberry Pi](https://blog.samsungsami.io/development/iot/mobile/2015/08/27/monitor-fire-and-temperature-using-an-arduino-raspberry-pi-and-sami.html#step-3-set-up-the-raspberry-pi) in the blog to install the packages and to run the program on the Pi.

More about SAMI
---------------

If you are not familiar with ARTIK Cloud, we have extensive documentation at https://developer.artik.cloud/documentation

The full ARTIK Cloud API specification can be found at https://developer-dev.artik.cloud/documentation/api-reference/

Check out advanced sample applications at https://developer.artik.cloud/documentation/samples/

To create and manage your services and devices on ARTIK Cloud, create an account at https://developer.artik.cloud

Also see the ARTIK Cloud blog for tutorials, updates, and more: http://artik.io/blog/cloud

License and Copyright
---------------------

Licensed under the Apache License. See [LICENSE](https://github.com/artikcloud/sample-iot-MonitorFlameTemp/blob/master/LICENSE).

Copyright (c) 2016 Samsung Electronics Co., Ltd.
