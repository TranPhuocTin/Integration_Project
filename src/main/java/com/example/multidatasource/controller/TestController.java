package com.example.multidatasource.controller;
import com.example.multidatasource.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    private final SocketService socketService;

    @Autowired
    public TestController(SocketService socketService) {
        this.socketService = socketService;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateEntity(@PathVariable int id) {
        socketService.sendMessage("/topic/updates", "Entity with ID " + id + " has been updated");

        return ResponseEntity.ok("Update success");
    }
}
