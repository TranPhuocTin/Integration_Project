package com.example.multidatasource.controller;

import com.example.multidatasource.dto.MergeDTO;
import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.service.MergeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.List;

@RestController
@RequestMapping("/merge")
@EnableWebSocket
public class MergeController {
    @Autowired
    MergeService mergeService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/get-merge-person")
    public List<MergePerson> getMergePerson(){
        return mergeService.mergeEmployeePersonal();
    }

    @PostMapping("/update/{id}")
    public String updateMergePerson(@RequestBody  MergeDTO mergeDTO, @PathVariable int id){
        MergePerson mergePerson = new MergePerson();
        mergePerson.setPersonalId(id);
        BeanUtils.copyProperties(mergeDTO, mergePerson);
        boolean result = mergeService.updateEmployeePersonal(mergePerson, id);
//        Testing websocket
        messagingTemplate.convertAndSend("/topic/merge", "123");
        if(result){
                messagingTemplate.convertAndSend("/topic/merge", mergePerson);
                return "Update successfully";
        }
        return "Failed to update";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMergePerson(@PathVariable int id){
        return mergeService.deleteEmployeePersonal(id);
    }
}
