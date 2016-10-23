#include <dht.h>

dht DHT;

#define DHT11_PIN 7

int counter = 1;
int deltaTimeStep=1000;
int loopTimeStep=12000;
int auxCount = loopTimeStep/deltaTimeStep;  // 12 seconds control

double meanTe = 0.0;
double meanHR = 0.0;

void setup(){
  Serial.begin(9600);
  //Serial.println(auxCount);
}

void loop(){
  
  int chk = DHT.read11(DHT11_PIN);
  //Serial.print("Temperature = ");
  //Serial.println(DHT.temperature);
  //Serial.print("Humidity = ");
  //Serial.println(DHT.humidity);

  if (counter<auxCount){
    counter++;
    meanTe = meanTe + DHT.temperature;
    meanHR = meanHR + DHT.humidity;

      //Serial.print("Counter = ");
      //Serial.println(counter);
  } else {
      //counter++;
      //Serial.print("Counter = ");
      //Serial.println(counter);
      meanTe = (meanTe + DHT.temperature)/counter;
      meanHR = (meanHR + DHT.humidity)/counter;

      Serial.print(meanTe);
      Serial.print(",");
      Serial.print(meanHR);
      Serial.println();
      
      counter = 1;
      meanTe=0.0;
      meanHR=0.0;
  }
  
  //Wait 1s
  delay(deltaTimeStep*20); //Perform 4 min instead of 5min (program may take more than 4 min)
  
}

