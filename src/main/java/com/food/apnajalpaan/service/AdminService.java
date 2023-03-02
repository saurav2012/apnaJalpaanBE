package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.admin.AdminModel;
import com.food.apnajalpaan.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    public Mono<AdminModel> saveAdmin(Mono<AdminModel> adminModelMono){
        return adminModelMono.flatMap(adminRepository::insert);
    }

    public Mono<AdminModel> updateAdmin(String adminId,Mono<AdminModel> adminModelMono){
        return adminRepository.findById(adminId)
                .flatMap(res -> {
                    return adminModelMono.flatMap(
                            x -> {
                                if(x.getUsername()!=null) res.setUsername(x.getUsername());
                                if(x.getAge()!=null) res.setAge(x.getAge());
                                if(x.getEmail()!=null) res.setEmail(x.getEmail());
                                if(x.getGender()!=null) res.setGender(x.getGender());
                                if(x.getMobile()!=null) res.setMobile(x.getMobile());
                                if(x.getPassword()!=null) res.setPassword(x.getPassword());
                                if(x.getFirstName()!=null) res.setFirstName(x.getFirstName());
                                if(x.getLastName()!=null) res.setLastName(x.getLastName());
                                if(x.getAddress()!=null) res.setAddress(x.getAddress());
                                return Mono.just(res);
                            });
                })
                .flatMap(adminRepository::save);
    }

    public Mono<String> deleteAdmin(String adminId){
        adminRepository.deleteById(adminId);
        return Mono.just("Admin is deleted successfully");
    }

    public Flux<AdminModel> getAllAdmin() {
        return adminRepository.findAll();
    }
    public Mono<AdminModel> getAdminByAdminId(String adminId) {
        return adminRepository.findById(adminId);
    }
}
