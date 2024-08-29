package com.federicopuricella.jobportal.controller;

import com.federicopuricella.jobportal.entity.RecruiterProfile;
import com.federicopuricella.jobportal.entity.Users;
import com.federicopuricella.jobportal.repository.UsersRepository;
import com.federicopuricella.jobportal.services.RecruiterProfileServices;
import com.federicopuricella.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileServices recruiterProfileServices;

    public RecruiterProfileController(UsersRepository usersRepository,RecruiterProfileServices recruiterProfileServices) {
        this.usersRepository = usersRepository;
        this.recruiterProfileServices= recruiterProfileServices;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication=SecurityContextHolder.getContext()
                .getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername=authentication.getName();
            Users users=usersRepository.findByEmail(currentUsername).orElseThrow(()->
            new UsernameNotFoundException("Could not found user"));
            Optional<RecruiterProfile> recruiterProfile=recruiterProfileServices.getOne(users.getUserId());
            if(recruiterProfile.isPresent()){
                model.addAttribute("profile",recruiterProfile.get());
            }
        }
        return "recruiter-profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile
                         multipartFile,Model model){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if(!(authentication instanceof AnonymousAuthenticationToken)){
           String currentUsername=authentication.getName();
           Users users=usersRepository.findByEmail(currentUsername).orElseThrow(()->
                   new UsernameNotFoundException("Could not found user"));
           recruiterProfile.setUserId(users);
           recruiterProfile.setUserAccountId(users.getUserId());
       }
       model.addAttribute("profile",recruiterProfile);
       String fileName="";
       if(!multipartFile.getOriginalFilename().equals("")){
           fileName= StringUtils.cleanPath
                   (Objects.requireNonNull(multipartFile.getOriginalFilename()));
           recruiterProfile.setProfilePhoto(fileName);
       }
       RecruiterProfile savedUser=recruiterProfileServices.addNew(recruiterProfile);
       String uploadDir="photos/recruiter/"+savedUser.getUserAccountId();
       try {
           FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
       }catch (Exception ex){
            ex.printStackTrace();
       }
       return "redirect:/dashboard/";
    }
}
