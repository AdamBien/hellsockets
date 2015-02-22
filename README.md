# hellsockets
WebSockets Stress Tester


#Installation

Drop the war into a Java EE 7 compliant server on Java 8

hellsockets is the stress generator, don't deploy it together with the actual application--it will blur the results.

#Usage

1. `curl http://localhost:8080/hellsockets/resources/statistics`for statistics
2.  `curl -XOPTIONS http://localhost:8080/hellsockets/resources/loader` for sample configuration
3.  `curl -XPOST -H'Content-type: application/json' -d'{"uri":"ws://localhost:8080/websockets/messages","message":"hey duke","sessions":10,"iterations":10}' http://localhost:8080/hellsockets/resources/loader` to create 10 sessions with  ws://localhost:8080/websockets/messages and send the message "hey duke" 10 times (=iterations)
