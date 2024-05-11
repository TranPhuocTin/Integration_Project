package com.example.multidatasource.controller;

import com.example.multidatasource.model.request.UpdateModel;
import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.service.MergeService;
import com.example.multidatasource.service.SocketService;
import lombok.extern.java.Log;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.List;


@RestController
@RequestMapping("/merge")
@EnableWebSocket
public class MergeController {
    private final MergeService mergeService;

    private final SocketService socketService;

    @Autowired
    public MergeController(MergeService mergeService, SocketService socketService) {
        this.mergeService = mergeService;
        this.socketService = socketService;
    }

    @GetMapping("/get-merge-person")
    public List<MergePerson> getMergePerson(){
        return mergeService.mergeEmployeePersonal();
    }

    //Fixing: the Personal entity doesn't have benefit plan id, logical failure
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateMergePerson(@RequestBody UpdateModel updateModel, @PathVariable int id){
        if(updateModel == null){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        else{
            MergePerson mergePerson = mergeService.getMergePersonById(id);
            BeanUtils.copyProperties(updateModel, mergePerson);
            mergePerson.setPersonalId(id);
            boolean result = mergeService.updateEmployeePersonal(mergePerson, id);
            if(result){
                List<MergePerson> mergePersonList = mergeService.mergeEmployeePersonal();
                socketService.sendMessage("/topic/merge", mergePersonList);
                return new ResponseEntity<>(mergePersonList, HttpStatus.OK);
            }
            return ResponseEntity.badRequest().body("Update failed");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMergePerson(@PathVariable int id){
        return ResponseEntity.ok(mergeService.deleteEmployeePersonal(id));
    }
}
