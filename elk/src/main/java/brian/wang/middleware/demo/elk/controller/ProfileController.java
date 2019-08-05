package brian.wang.middleware.demo.elk.controller;


import brian.wang.middleware.demo.elk.domain.ProfileDocument;
import brian.wang.middleware.demo.elk.service.ProfileService;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile-management")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiles/{id}")
    public ProfileDocument getOrder(@PathVariable("id")String id) {
        return profileService.getProfile(id);
    }

    @PostMapping("/profiles/{id}/update")
    public boolean postOrder(@PathVariable("id")String id,
                            @RequestBody String body) {
        return profileService.postProfile(id, body);
    }
}
