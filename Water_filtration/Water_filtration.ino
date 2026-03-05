// Include the motor shield V1 library
#include <AFMotor.h>

// Define the pin the push button is connected to
#define pushButtonGo A3
#define pushButtonStop A2

// Connect the DC motors to the motor control board
AF_DCMotor pressMotor(2); // gear box for the press assembly
AF_DCMotor mixMotor(1);   // dowel stabilizer on the bottom
AF_DCMotor cfpump(3);     // alum water pump
AF_DCMotor pump(4);       // clean water pump

// State variables
bool systemRunning = false;
bool lastButtonStateGo = HIGH;
bool lastButtonStateStop = HIGH;
bool turbidityOneDoing = true;
bool turbidityTwoDoing = false;
bool dirtyPumpDoing = false;
bool alumPumpDoing = false;
bool mixingFastDoing = false;
bool mixingSlowDoing = false;
bool stopMixing=false;
bool gearboxMotorDoing = false;
bool finalPumpDoing = false;
unsigned long run=1;

// Relay for submersible pump
const byte RELAY_PIN = 53;

// Timing for turbidity readings
unsigned long currentMillis = 0;
unsigned long previousMillis = 0;
const unsigned long interval = 1000;
int seconds = 0; // Counter for seconds




void setup() {
  Serial.begin(9600);
  Serial.println("Time\tSensor 1\tSensor2");
  Serial.println("====\t========\t=======");

  //Set initial motor speeds
  pressMotor.setSpeed(0);
  mixMotor.setSpeed(0);
  cfpump.setSpeed(0);
  pump.setSpeed(0);

  //Start with everything stopped
  pressMotor.run(RELEASE);
  mixMotor.run(RELEASE);
  cfpump.run(RELEASE);
  pump.run(RELEASE);

  //Configure push button (from button code)
  pinMode(pushButtonGo, INPUT_PULLUP);
  pinMode(pushButtonStop, INPUT_PULLUP);

  //Relay setup (from relay code)
  pinMode(RELAY_PIN, OUTPUT);
  digitalWrite(RELAY_PIN, LOW); // relay OFF
}

void loop() {
  unsigned long timer = 0;
  bool buttonStateGo = digitalRead(pushButtonGo);

  // Detect button press
  if (buttonStateGo == LOW && lastButtonStateGo == HIGH) {
    if(!systemRunning){//changes system running only if its false
      systemRunning = !systemRunning;  //set system running to true 
    } 
  }
  lastButtonStateGo = buttonStateGo;//cause the current buttonState then becomes the previous button state

  //Only run if systemRunning is true 
  if(systemRunning){

    if (turbidityOneDoing) {
      turbidityTest();
    } 

    if(dirtyPumpDoing){
      runRelay(); 
      dirtyPumpDoing=false;
      alumPumpDoing=true;
    }

    if(alumPumpDoing){
      timer=0;
      cfpump.run(FORWARD);
      cfpump.setSpeed(255);
      while (timer <= 30000) {

        bool buttonStateStop = digitalRead(pushButtonStop);//reads in stop button
        if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
          systemRunning = false;//stop the system if the stop button is pressed
          cfpump.setSpeed(0);
        }
        lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

        //If paused, stay stoped here until go button is pressed
        while (!systemRunning) {
          bool buttonGoState = digitalRead(pushButtonGo);//read in go button
          if (buttonGoState == LOW && lastButtonStateGo == HIGH) {
            systemRunning = true;//the system starts going again 
          }
          lastButtonStateGo = buttonGoState;
          delay(1);
        }

        // Only increase the timer and run the relay when unpaused
        cfpump.setSpeed(255);
        timer++;
        delay(1);
      }

      cfpump.setSpeed(0);
      cfpump.run(RELEASE);
      alumPumpDoing=false;
      mixingFastDoing=true;
    }

    if(mixingFastDoing){
      timer=0;
      mixMotor.run(FORWARD);
      mixMotor.setSpeed(180);
      while (timer <= 40000) {

        bool buttonStateStop = digitalRead(pushButtonStop);//reads in the stop button
        if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
          systemRunning = false;//stop the system if the stop button is pressed
          mixMotor.setSpeed(0);
        }
        lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

        //If paused, stay stoped here until go button is pressed
        while (!systemRunning) {
          bool buttonGoState = digitalRead(pushButtonGo);//read in go button
          if (buttonGoState == LOW && lastButtonStateGo == HIGH) {
            systemRunning = true;//the system starts going again  
          }
          lastButtonStateGo = buttonGoState;
          delay(1);
        }

        // Only advance the timer and run the relay when unpaused
        mixMotor.setSpeed(180);
        timer++;
        delay(1);
      }
      mixMotor.setSpeed(0);
      mixMotor.run(RELEASE);
      mixingFastDoing=false;
      mixingSlowDoing=true;
    }

    if(mixingSlowDoing){
      timer=0;
      mixMotor.run(FORWARD);
      mixMotor.setSpeed(80);

      while (timer <= 30000) {

        bool buttonStateStop = digitalRead(pushButtonStop);//reads in stop button
        if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
          systemRunning = false;//stop the system if the stop button is pressed
          mixMotor.setSpeed(0);
        }
        lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

        //If paused, stay stoped here until go button is pressed
        while (!systemRunning) {
          bool buttonGoState = digitalRead(pushButtonGo);
          if (buttonGoState == LOW && lastButtonStateGo == HIGH) {
            systemRunning = true;//the system starts going again 
          }
          lastButtonStateGo = buttonGoState;
          delay(1);
        }

        // Only advance the timer and run the relay when unpaused
        mixMotor.setSpeed(80);
        timer++;
        delay(1);
      }

      mixMotor.setSpeed(0);
      mixMotor.run(RELEASE);
      mixingSlowDoing=false;
      stopMixing=true;
      
    }
    if(stopMixing){
      timer=0;
      while(timer<=100000){
        bool buttonStateStop = digitalRead(pushButtonStop);//reads in stop button
        if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
          systemRunning = false;//stop the system if the stop button is pressed
        }
        lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

        //If paused, stay stoped here until go button is pressed
        while (!systemRunning) {
          bool buttonGoState = digitalRead(pushButtonGo);//read in go button
          if (buttonGoState == LOW && lastButtonStateGo == HIGH) {
            systemRunning = true;//the system starts going again  
          }
          lastButtonStateGo = buttonGoState;
          delay(1);
        }

        // Only advance the timer and run the relay when unpaused
        timer++;
        delay(1);
        
      }
      stopMixing=false;
      gearboxMotorDoing=true;
    }
    if(gearboxMotorDoing){
      timer=0;
      pressMotor.run(FORWARD);
      pressMotor.setSpeed(255);

      while (timer <= 20000) {

        bool buttonStateStop = digitalRead(pushButtonStop);//reads in stop button
        if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
          systemRunning = false;//stop the system if the stop button is pressed
          pressMotor.setSpeed(0);
        }
        lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

        //If paused, stay stoped here until go button is pressed
        while (!systemRunning) {
          bool buttonGoState = digitalRead(pushButtonGo);//read in go button
          if (buttonGoState == LOW && lastButtonStateGo == HIGH) {
            systemRunning = true;//the system starts going again  
          }
          lastButtonStateGo = buttonGoState;
          delay(1);
        }

        // Only advance the timer and run the relay when unpaused
        pressMotor.setSpeed(255);
        timer++;
        delay(1);
      }

      pressMotor.setSpeed(0);
      pressMotor.run(RELEASE);
      gearboxMotorDoing=false;
      finalPumpDoing=true;
    }

    if(finalPumpDoing){
      timer=0;
      pump.run(FORWARD);
      pump.setSpeed(255);

      while (timer <= 42000) {

        bool buttonStateStop = digitalRead(pushButtonStop);//reads in stop button
        if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
          systemRunning = false;//stop the system if the stop button is pressed
          pump.setSpeed(0);
        }
        lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

        //If paused, stay stoped here until go button is pressed
        while (!systemRunning) {
          bool buttonGoState = digitalRead(pushButtonGo);//read in go button
          if (buttonGoState == LOW && lastButtonStateGo == HIGH) {
            systemRunning = true;//the system starts going again  
          }
          lastButtonStateGo = buttonGoState;
          delay(1);
        }

        // Only advance the timer and run the relay when unpaused
        pump.setSpeed(255);
        timer++;
        delay(1);
      }

      pump.setSpeed(0);
      pump.run(RELEASE);
      finalPumpDoing=false;
      turbidityTwoDoing=true;
    }

    if (turbidityTwoDoing) {
      turbidityTest();
    }

    // pressMotor.run(BACKWARD);
    // pressMotor.setSpeed(255);
    // delay(7000);
    // pressMotor.setSpeed(255);

  }
}

void turbidityTest() {
  
   // Get the current time in milliseconds
  currentMillis = millis();

  //check the stop button
  bool buttonStateStop = digitalRead(pushButtonStop);
  if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
    systemRunning = false;//pause system
  }
  lastButtonStateStop = buttonStateStop;

  if (!systemRunning){
    //nothing happens cause like the system is stoped
  }else{
  // Check if the interval has passed
  if (currentMillis - previousMillis >= interval) {
    // Save the last time you updated the serial monitor
    previousMillis = currentMillis;
    
    // Increment the seconds counter
    seconds++;
  
    int sensor1 = analogRead(A1);// read the input on analog pin 0
    float voltage1 = sensor1 * (5.0 / 1024.0); // Convert the analog reading (which goes from 0 - 1023) to a voltage (0 - 5V)
    float turbidity1 = (-1120.4*pow(voltage1,2) + 5742.3*voltage1-4352.9);
    int sensor2 = analogRead(A0);
    float voltage2 = sensor2 * (5.0 / 1024.0); 
    float turbidity2 = (-1120.4*pow(voltage2,2) + 5742.3*voltage2-4352.9);
    Serial.print(seconds);
    Serial.print("\t");
    Serial.print(voltage1); 
    //Serial.print("\t\t");
    //Serial.print(turbidity1);
    Serial.print("\t\t");
    Serial.print(voltage2); 
    //Serial.print("\t\t");
    //Serial.println(turbidity2); // println means next loop will print on new line


    if (seconds >= 25 && run == 1) {
      turbidityOneDoing = false;
      seconds = 0;
      run = 2;
      previousMillis = millis();
      dirtyPumpDoing=true;
    }

    if (seconds >= 25 && run == 2) {
      turbidityTwoDoing = false;
      seconds = 0;
      run = 1;
      previousMillis = millis();
      digitalWrite(RELAY_PIN, LOW);
	
      systemRunning = false;//finished system
    }
  }
  }
}

void runRelay() {
  unsigned long timer = 0;
  //amount of time in ms that the submersible pump has to run for 
  while (timer <= 16000) {

    bool buttonStateStop = digitalRead(pushButtonStop);//reads in stop button
    if (buttonStateStop == LOW && lastButtonStateStop == HIGH) {
      systemRunning = false;//stop the system if the stop button is pressed
      digitalWrite(RELAY_PIN, LOW);
    }
    lastButtonStateStop = buttonStateStop;//setting up the last button state for the stop button as low or high what ever the reading was 

    //If paused, stay stoped here until go button is pressed
    while (!systemRunning) {
      bool buttonStateGo = digitalRead(pushButtonGo);//read in go button
      if (buttonStateGo == LOW && lastButtonStateGo == HIGH) {
        systemRunning = true;//the system starts going again  
      }
      lastButtonStateGo = buttonStateGo;
      delay(1);
    }

    // Only advance the timer and run the relay when unpaused
    digitalWrite(RELAY_PIN, HIGH);
    timer++;
    delay(1);
  }

  digitalWrite(RELAY_PIN, LOW);
}

