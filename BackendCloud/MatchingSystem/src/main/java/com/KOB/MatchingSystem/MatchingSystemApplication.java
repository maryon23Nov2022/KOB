package com.KOB.MatchingSystem;

import com.KOB.MatchingSystem.Service.Impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args){
        MatchingServiceImpl.matchingPool.start();   //Start thread.
        SpringApplication.run(MatchingSystemApplication.class, args);
    }
}