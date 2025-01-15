package com.RobertoPalMol.TribeMe_Backend.Controller;


import com.RobertoPalMol.TribeMe_Backend.Entity.UserTribe;
import com.RobertoPalMol.TribeMe_Backend.Service.UserTribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-tribes")
public class UserTribeController {
    @Autowired
    private UserTribeService userTribeService;

    @GetMapping
    public List<UserTribe> getAllMemberships() {
        return userTribeService.getAllMemberships();
    }

    @PostMapping
    public UserTribe addMembership(@RequestBody UserTribe userTribe) {
        return userTribeService.addMembership(userTribe);
    }
}
