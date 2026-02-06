package com.MyProject.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyProject.service.HikSystemService;


@RestController
@RequestMapping("/api/hikSystem")
public class HikSystemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HikSystemService hikSystemService;

    @Autowired
    public HikSystemController(HikSystemService hikSystemService) {
        this.hikSystemService = hikSystemService;
    }
    @PostMapping("/arm/{id}")
    public ResponseEntity<String> armZone(@PathVariable int id){
        return executeCommand("ARM:" + id);
    }
    @PostMapping("/disarm/{id}")
    public ResponseEntity<String> disarmZone(@PathVariable int id){
        return executeCommand("DISARM:" + id);
    }
    @PostMapping("/bypass/{id}")
    public ResponseEntity<String> bypassZone(@PathVariable int id){
        return executeCommand("BYPASS:" + id);
    }
    @PostMapping("/unbypass/{id}")
    public ResponseEntity<String> unbypassZone(@PathVariable int id){
        return executeCommand("UNBYPASS:" + id);
    }
    @PostMapping("/trigger/{id}")
    public ResponseEntity<String> triggerAlarm(@PathVariable int id){
        return executeCommand("TRIGGER:" + id);
    }
    @GetMapping("/status/{id}")
    public ResponseEntity<String> getZoneStatus(@PathVariable int id){
        return executeCommand("STATUS:" + id);
    }
    @GetMapping("/listAllZones")
    public ResponseEntity<String> listZones(){
        return executeCommand("LIST_ALL_ZONES");
    }
    @GetMapping("/listArmedZones")
    public ResponseEntity<String> listArmedZones(){
        return executeCommand("LIST_ARMED_ZONES");
    }
    @GetMapping("/listBypassedZones")
    public ResponseEntity<String> listBypassedZones(){
        return executeCommand("LIST_BYPASSED_ZONES");
    }
    @GetMapping("/listDisarmedZones")
    public ResponseEntity<String> listDisarmedZones(){
        return executeCommand("LIST_DISARMED_ZONES");
    }
    @GetMapping("/listArmingZones")
    public ResponseEntity<String> listArmingZones(){
        return executeCommand("LIST_ARMING_ZONES");
    }
    @PostMapping("/listOneZone/{id}")
    public ResponseEntity<String> listOneZone(@PathVariable int id){
        return executeCommand("LIST_ONE_ZONE:" + id);
    }

    @SuppressWarnings("null")
    private ResponseEntity<String> executeCommand(String command) {
       String jsonResponse = hikSystemService.SendCommand(command);

    if (jsonResponse == null) {
             return ResponseEntity.status(500)
                 .contentType(MediaType.APPLICATION_JSON)
                 .body("{\"status\":\"ERROR\", \"message\":\" No response from Hik-System\"}");
        
    }
    if(jsonResponse.startsWith("Error:")) {
            return ResponseEntity.status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\":\"ERROR\", \"message\":\"" + jsonResponse + "\"}");
        }
        logger.info("Forwarding to React: " + jsonResponse);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResponse);
    }
}