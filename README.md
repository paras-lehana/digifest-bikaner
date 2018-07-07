# digifest-bikaner
Repo for sources of SmartStreet (to be presented @ IT Day 2018)


# Summary

Adaptive Smart Street Lightning is a module to automate existing street lights infrastructure using deployment of cost-efficient ESP8266 WiFi chipsets. The module optimizes group's net intensity (thereby energy costs) using self-learning over attributes like area light intensity (using LDR), weather conditions (like cloudiness, temperature, visibility), geolocation (using Google Maps API), time of the day and Master Control. A group of Street Lights (belonging to a colony or area) is a (sub)network and thus bears an IP address that can be controlled by the administrators using local internet/Wi-Fi (the android interface that allows this is the Master Control). Moreover, the street network is not hard-coded into the app but is managed via cloud server (Firebase) allowing real-time management. The module will thus help connect a network of street lights arrive at a collective optimized intensity to further reduce net energy consumptions as well as collect environmental parameters that could be used to generate datasets for statistical analysis and predictions using Fuzzy Inference System and/or Machine Learning Algorithms. The approach can be extended to whole city or state department and can additionally help rectify local errors such as a non-working street light or those depicted by the collected data. While administrators would have advanced tools to control and manage the network through mobile or web authorized interfaces, citizens would be able to view their local and global street lights cluster and can have further access to corresponding open data.

# Introducation to Team and Project

We're developing an cost-efficient ESP8266 based Smart Street Lighting module which can be implemented over existing street lighting infrastructure. India has over 3 million street lights while there are about 280 million street lights globally. This module, besides saving over 80 TWh of electricity annually, will also automate the lighting process, generate locality reports, enable pattern generation, implement intensity optimization, master control and remote access.

The module consists of cost-efficient and Lua-programmed WiFi-enabled ESP8266 chips - a single chip costs only 500 INR and can control a network or cluster of 1000 street lights. The remote access is granted according to the user level by an Android App that can be run anywhere in the network. The locations of street lights are fetched from Realtime Firebase cloud database and shown using Maps API. Weather API, TelosB sensors and LDR is used to generate reports which is fetched to ML algorithms.

Team Name: The Vigyaaneers
Team Members: Paras Lehana; Ayushmaan Bansal; Yash Garg; Abhinav Bansal

# Refer to docs

The project can be understood from scratch by reading the report in docs folder or getting a simple overview with presentation.

# Directory Overview

app - Working SmartStreet Android Application for Master Control (integrated with Wifi connectivity or remote access, Google Maps API, ESP8266 calling, Weather API, Firebase cloud database fetch)

code - Source codes for lua (ESP8266), TelosB (sensor collected data) and android (app)

docs - referred documents, literature (research paper referred on similar approaches), report (must read), user-manual (user-friendly doc describing how to use or tweak the project modules including both hardware and software modules), research-paper (our current research work)

google-maps - Images of Google Maps API to be used both in app and physical board

photos - project photos

presentation - overview of the project

stats - data collection reports of sensor and analysis over it

team - member specific directories and files

tools - softwares used for running esp8266 controller and coding into it (lua) 
