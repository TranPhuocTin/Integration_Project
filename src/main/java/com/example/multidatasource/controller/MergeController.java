package com.example.multidatasource.controller;

import com.example.multidatasource.model.request.UpdateModel;
import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.service.MergeService;
import com.example.multidatasource.service.SocketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public String updateMergePerson(@RequestBody UpdateModel updateModel, @PathVariable int id){
        MergePerson mergePerson = mergeService.getMergePersonById(id);
        mergePerson.setPersonalId(id);
        BeanUtils.copyProperties(updateModel, mergePerson);
        boolean result = mergeService.updateEmployeePersonal(mergePerson, id);
//        Testing websocket
        if(result){
                socketService.sendMessage("/topic", mergePerson);
                return "Update successfully";
        }
        return "Failed to update";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMergePerson(@PathVariable int id){
        return mergeService.deleteEmployeePersonal(id);
    }
}
