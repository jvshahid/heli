ARDUINO_DIR   = /usr/share/arduino
ARDMK_DIR     = /usr/share/arduino
AVR_TOOLS_DIR = /usr

USER_LIB_PATH = /home/jvshahid/codez/arduino/plane/libraries

# Standard libraries
ARDUINO_LIBS = Wire Wire/utility

# user libraries found in USER_LIB_PATH
ARDUINO_LIBS += i2cdevlib/Arduino/MPU6050 i2cdevlib/Arduino/I2Cdev

### BOARD_TAG
### It must be set to the board you are currently using. (i.e uno, mega2560, etc.)
BOARD_TAG         = uno

### CPPFLAGS
### Flags you might want to set for debugging purpose. Comment to stop.
CPPFLAGS         = -pedantic -Wall -Wextra -O2

MONITOR_BAUDRATE = 115200

### MONITOR_PORT
### The port your board is connected to. Using an '*' tries all the ports and finds the right one.
ARDUINO_PORT = /dev/ttyACM0

### path to Arduino.mk, inside the ARDMK_DIR, don't touch.
include /usr/share/arduino/Arduino.mk
