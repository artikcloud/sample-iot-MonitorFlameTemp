#include <DHT.h>

// Delay between reads
const int delayBetweenReads = 5000;//5s

// For temperature sensor
const int DHTPIN = 2;
const int DHTTYPE = DHT11;
DHT dht(DHTPIN, DHTTYPE);

// For flame detector senso
const int flameDigitalPinIn = 3; 

void setup() {
  // initialize serial communication @ 9600 baud:
  Serial.begin(9600);
  dht.begin();
  pinMode(flameDigitalPinIn, INPUT);
}

void loop() {
  // Read data from temperature sensor
  float temp = dht.readTemperature(true);
  if (isnan(temp)) {
    temp = -1;// error
  } 
  
  // read the sensor on analog A0:
  int flameAnalogReading = analogRead(A0);
  
  // HIGH(1) means no fire is detected
  // LOW (0) means fire is detected
  int flameDigitalReading = digitalRead(flameDigitalPinIn);
  
  // Compose data and send to Serial
 //  Data format: "temperature,flameAnalogReading,flameDigitalReading
  // Data example: "84,1001,1"
  Serial.print(String((int)temp));
  Serial.print(",");
  Serial.print(String(flameAnalogReading));
  Serial.print(",");
  Serial.println(String(flameDigitalReading));

  delay(delayBetweenReads);
}
