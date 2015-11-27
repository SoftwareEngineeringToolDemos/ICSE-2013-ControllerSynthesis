#!/bin/bash

# Update Ubuntu
sudo apt-get update

# Install Java and Flash (to view demo videos on YouTube)
sudo apt-get install -y openjdk-7-jre
sudo apt-get install -y flashplugin-installer

# Remove unwanted icons from Launcher
cd /usr/share/applications && sudo rm -f libreoffice-writer.desktop libreoffice-calc.desktop libreoffice-impress.desktop ubuntu-software-center.desktop ubuntu-amazon-default.desktop

# Copy VM files to Desktop
sudo chmod 777 /vagrant/*
unzip /vagrant/Controller_Synthesis_VM_Desktop_Files.zip -d /home/vagrant/Desktop/
chmod +x /home/vagrant/Desktop/*.desktop

# Download and extract mtsa analyzer to desktop
sudo wget -O /home/vagrant/mtsa-3.6.zip http://sourceforge.net/projects/mtsa/files/latest/download?source=files
mkdir /home/vagrant/Desktop/mtsa-3.6/
unzip /home/vagrant/mtsa-3.6.zip -d /home/vagrant/Desktop/mtsa-3.6/

# Add mtsa to startup applications before reloading VM
mkdir /home/vagrant/.config/autostart/
cp /home/vagrant/Desktop/ControllerSynthesis*.desktop /home/vagrant/.config/autostart/
