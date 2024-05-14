package com.example.multidatasource.controller;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import com.example.multidatasource.payload.UpdateBenefitAndPayRateDTO;
import com.example.multidatasource.payload.UpdateEmploymentDetails;
import com.example.multidatasource.payload.UpdatePersonalDTO;
import com.example.multidatasource.payload.MergePersonDTO;
import com.example.multidatasource.service.MergeService;
import com.example.multidatasource.service.SocketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<MergePersonDTO> getMergePerson(){
        return mergeService.mergeEmployeePersonal();
    }

    //Fixing: the Personal entity doesn't have benefit plan id, logical failure
    @PostMapping("/update-profile/{id}")
    public ResponseEntity<?> updateMergePerson(@RequestBody UpdatePersonalDTO updatePersonalDTO, @PathVariable int id){
        if(updatePersonalDTO == null){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        else{
            MergePersonDTO mergePersonDTO = mergeService.getMergePersonById(id);
            BeanUtils.copyProperties(updatePersonalDTO, mergePersonDTO);
            mergePersonDTO.setPersonalId(id);
            boolean result = mergeService.updateEmployeePersonal(mergePersonDTO, id);
            if(result){
                sendSocketMessage();
                return ResponseEntity.ok("Update success");
            }
            return ResponseEntity.badRequest().body("Update failed");
        }
    }

    @PostMapping("/update-benefitPlanAndPayrate/{id}")
    public ResponseEntity<?> updateBenefitPlanAndPayrate(@RequestBody UpdateBenefitAndPayRateDTO updateBenefitAndPayrateDTO, @PathVariable int id){
        if(updateBenefitAndPayrateDTO == null){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        else{
            BenefitPlanEntity benefitPlanUpdate = new BenefitPlanEntity();
            PayRateEntity payRateEntityUpdate = new PayRateEntity();

            BeanUtils.copyProperties(updateBenefitAndPayrateDTO, benefitPlanUpdate);
            BeanUtils.copyProperties(updateBenefitAndPayrateDTO, payRateEntityUpdate);


            boolean result = mergeService.updateBenefitPlanPayrate(id ,benefitPlanUpdate, payRateEntityUpdate, updateBenefitAndPayrateDTO.getPaidToDate(), updateBenefitAndPayrateDTO.getPaidLastYear());

            if(result){
                sendSocketMessage();
                return ResponseEntity.ok("Update success");
            }
            return ResponseEntity.badRequest().body("Update failed");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMergePerson(@PathVariable int id){
        String result = mergeService.deleteEmployeePersonal(id);
        sendSocketMessage();
        return ResponseEntity.ok(mergeService.deleteEmployeePersonal(id));
    }

    @PutMapping("/update-employment-details/{id}")
    public ResponseEntity<?> updateEmploymentDetails(@RequestBody UpdateEmploymentDetails updateEmploymentDetails, @PathVariable int id){
        if(updateEmploymentDetails == null){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        else{
            String result = mergeService.updateEmploymentDetails(id, updateEmploymentDetails);
            if(result.equals("Update Successfully")){
                sendSocketMessage();
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
    }

    public void sendSocketMessage(){
        List<MergePersonDTO> mergePersonDTOList = mergeService.mergeEmployeePersonal();
        socketService.sendMessage("/topic/merge", mergePersonDTOList);
    }
}
