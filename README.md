# Monitor Fire and Temperature Using an Arduino, Raspberry Pi and ARTIK Cloud

Build a fire and temperature monitoring system using ARTIK Cloud, off-the-shelf sensors, Arduino Uno, Raspberry Pi, and an Android application. 

Introduction
-------------

The blog post [Monitor Fire and Temperature Using ARTIK Cloud, Open-Source IoT Hardware and Android](https://www.artik.io/2016/05/monitor-fire-temperature-using-artik-cloud-open-source-iot-hardware-android/) at http://artik.io/blog/cloud describes what the system does and how it is implemented.

Check out [Demo video.](https://www.artik.io/2016/05/monitor-fire-temperature-using-artik-cloud-open-source-iot-hardware-android/#demo-video)

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

 1. Import `android-simple-monitor` as an existing Android Studio project in Android Studio IDE.
 2. If you have not connected the IoT system as a device in My ARTIK Cloud, please consult **Step 1: Connect a device in My ARTIK Cloud** in the [blog post](https://www.artik.io/2016/05/monitor-fire-temperature-using-artik-cloud-open-source-iot-hardware-android/). Then, in [My ARTIK Cloud](https://www.artik.cloud), click the Settings icon of the your device. In the pop-up, get the device ID and token to replace `YOUR DEVICE ID` and `YOUR DEVICE TOKEN` in `ArtikCloudSession.java`.
 3. Build the app and deploy APK into your Android phone

This sample application uses ARTIK Cloud Java/Android SDK library downloaded from Maven Central Repository. The version of the library is specified in `app/build.gradle`of the Android project. You may also build ARTIK Cloud Java/Android SDK library using the source code at <a href="https://github.com/artikcloud/artikcloud-java" target="_blank">GitHub</a>.

Sketch Program for Arduino Uno Board
-------------

The code is located in `arduino` directory. Consult **Step 2: Set up the Arduino** in the [blog](https://www.artik.io/2016/05/monitor-fire-temperature-using-artik-cloud-open-source-iot-hardware-android/) to set up the board and load the program to the board.

Nodejs Program for Raspberry Pi
-------------

The code is located in `raspberrypi` directory. Consult **Step 3: Set up the Raspberry Pi** in the [blog](https://www.artik.io/2016/05/monitor-fire-temperature-using-artik-cloud-open-source-iot-hardware-android/) to install the packages and to run the program on the Pi.

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
