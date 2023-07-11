package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {

    private TeacherRepository teacherRepository;
    private WikipediaRepository wikipediaRepository;

    private ChatGptClientImpl chatGptClient;
    public DemoController(TeacherRepository teacherRepository, WikipediaRepository wikipediaRepository, ChatGptClientImpl chatGptClient){
        this.teacherRepository = teacherRepository;
        this.wikipediaRepository = wikipediaRepository;
        this.chatGptClient = chatGptClient;

    }
    @GetMapping("test")
    public String testEndpoint() {
        return "Hello";
    }

    @GetMapping("test2")
    public int testEndpoint2(@RequestParam("number") int n) {
        return n;
    }

    @GetMapping("getUser")
    public Teacher getUser(@RequestParam("id") int id) {
        return teacherRepository.findById(id).get();
    }
    @GetMapping("getAllUsers")
    public List<Teacher> getUsers() {
        return teacherRepository.findAll();
    }


    @PostMapping("createUser")
    public Teacher createUser(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @GetMapping("getEmails")
    public Integer getEmailsEmpty() {
        return teacherRepository.countByEmailIsNull();
    }


    @GetMapping("getWikipedia")
    public List<WikipediaData> getWikipedia(@RequestParam("name") String name) {
        return wikipediaRepository.findByTitle(name);
    }

    @GetMapping("gptautocomplete")
    public String getGpt(@RequestParam("text") String name) {
        return chatGptClient.generateText(name);
    }











}