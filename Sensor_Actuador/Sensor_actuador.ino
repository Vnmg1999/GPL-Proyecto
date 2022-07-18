#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <Arduino_JSON.h>
 
//#define RELE_PIN      2   //cuando tengas el relay funcionado deberiamos ver el pin aqui en este caso esta en el pind digital 2
//const char* ssid = "APSISTEMAS";
//const char* password = "Sistemas2021*";
const char* ssid = "NETLIFE-GONZALEZ";
const char* password = "SPG242308$";

//Your Domain name with URL path or IP address with path
//const char* serverName = "http://192.168.88.188:3000/create";
const char* serverName1 = "http://192.168.100.10:5007/pulso";
const char* serverName = "http://192.168.100.10:5007/create";
// the following variables are unsigned longs because the time, measured in
// milliseconds, will quickly become a bigger number than can be stored in an int.
unsigned long lastTimePost = 0;
unsigned long lastTimeGet = 0;
// Timer set to 10 minutes (600000)
//unsigned long timerDelay = 600000;
// Set timer to 5 seconds (5000)
unsigned long timerDelayPost = 10000;
unsigned long timerDelayGet = 5000;
int Status = 12;  // Digital pin D6
int sensor = 13;    // Digital pin D
const int ledPIN = 15;
String ledStatus = "0";
int ledStatusInt = 0;
String ledReadings;
int ledInicial = 0;


void setup() {

  Serial.begin(115200);
  pinMode(sensor, INPUT);   // declare sensor as input
  pinMode(Status, OUTPUT);  // declare LED as output
  pinMode(ledPIN , OUTPUT);  //definir pin como salida

  WiFi.begin(ssid, password);
  Serial.println("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    delay(8000);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to WiFi network with IP Address: ");
  Serial.println(WiFi.localIP());
  Serial.println("Timer set to 5 seconds (timerDelay variable), it will take 5 seconds before publishing the first reading.");
  //Aqui se configurario lo del relay cuando se tenga
  //pinMode( RELE_PIN, OUTPUT );
  //digitalWrite(RELE_PIN, LOW); // apagar led
}

void loop() {
  int enviar = 0;
  String vMovimiento = "N";

  long state = digitalRead(sensor);
  if (state == HIGH) {
    digitalWrite (Status, HIGH);
    Serial.println("Motion detected!");
    enviar = 1;
    vMovimiento = "S";
  }
  else {
    digitalWrite (Status, LOW);
    Serial.println("Motion absent!");
    vMovimiento = "N";
    enviar = 0;
  }
  //delay(5000);

  if (ledInicial == 0) {
    digitalWrite(ledPIN , LOW);
  ledStatus = "0";
    ledInicial = 1;
  }

  //Send an HTTP POST request every 10 minutes
  if (((millis() - lastTimePost) > timerDelayPost) || (enviar==1) ) {
    if(enviar==1){
    //Check WiFi connection status
    if (WiFi.status() == WL_CONNECTED) {
      WiFiClient client;
      HTTPClient http;

      // Your Domain name with URL path or IP address with path
      http.begin(client, serverName);

      // Specify content-type header
      http.addHeader("Content-Type", "application/x-www-form-urlencoded");
      // Data to send with HTTP POST
      String httpRequestData = "Movimiento=" + vMovimiento + "&Pulso=" + ledStatus;
      // Send HTTP POST request
      int httpResponseCode = http.POST(httpRequestData);

      Serial.print("HTTP Response code POST: ");
      Serial.println(httpResponseCode);
      Serial.println("Dato enviado");
       delay(2000);
      // Free resources
      http.end();
    }else {
      Serial.println("WiFi Disconnected");
    }     
    }else{
    Serial.println("Dato No enviado");    
    }
  lastTimePost = millis();
  }

  //Send an HTTP GET request every 10 minutes
  if (((millis() - lastTimeGet) > timerDelayGet)) {
    //Check WiFi connection status
    if (WiFi.status() == WL_CONNECTED) {
    ledReadings = httpGETRequest();
    Serial.println(ledReadings);
    JSONVar myObject = JSON.parse(ledReadings);
      // JSON.typeof(jsonVar) can be used to get the type of the var
      if (JSON.typeof(myObject) == "undefined") {
      Serial.println("Parsing input failed!");
      return;
      }   
      Serial.print("JSON object = ");
      Serial.println(myObject);    
      // myObject.hasOwnProperty(key) checks if the object contains an entry for key
      if (myObject.hasOwnProperty("Pulso")) {
      Serial.print("myObject[\"Pulso\"] = ");
      Serial.println(myObject["Pulso"]);
      Serial.println("TIPO: "+ JSON.typeof(myObject));
      //ledStatus = JSON.stringify(myObject["Pulso"]) + "M";
      const char* sensor = myObject["Pulso"];
      //ledStatusInt = (int) myObject["Pulso"];
      ledStatus = sensor[0];
      Serial.println("ledStatus: " +ledStatus);
      
      if(ledStatus=="0"){
      digitalWrite(ledPIN , LOW);
      ledStatus = "0";
      Serial.println("Apagar Led: " + ledStatus);
      }
      if(ledStatus=="1"){
      digitalWrite(ledPIN , HIGH);
      ledStatus = "1";
      Serial.println("Encender Led: " + ledStatus);
      }
      }
  }
    else {
      Serial.println("WiFi Disconnected");
    }
    lastTimeGet = millis();
  }

}



String httpGETRequest() {
  WiFiClient client;
  HTTPClient http;
    
  // Your Domain name with URL path or IP address with path
  http.begin(client, serverName1);
  
  // Send HTTP POST request
  int httpResponseCode = http.GET();
  
  String payload = "{}"; 
  
  if (httpResponseCode>0) {
    Serial.print("HTTP Response code GET: ");
    Serial.println(httpResponseCode);
    payload = http.getString();
  Serial.print(payload);
  }
  else {
    Serial.print("Error code GET: ");
    Serial.println(httpResponseCode);
  }
  // Free resources
  http.end();

  return payload;
}
