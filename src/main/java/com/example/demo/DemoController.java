package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Timer;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(DemoController.class.getName());

    private final Counter noteCreationCounter;

    private final Timer noteCreationTimer;
    private final RestTemplate restTemplate;

    private TeacherRepository teacherRepository;
    private WikipediaRepository wikipediaRepository;

    private ChatGptClientImpl chatGptClient;

    public DemoController(RestTemplate restTemplate, MeterRegistry meterRegistry, TeacherRepository teacherRepository, WikipediaRepository wikipediaRepository, ChatGptClientImpl chatGptClient) {
        this.teacherRepository = teacherRepository;
        this.wikipediaRepository = wikipediaRepository;
        this.chatGptClient = chatGptClient;
        this.noteCreationCounter = meterRegistry.counter("counter");
        this.noteCreationTimer = meterRegistry.timer("timer");
        this.restTemplate = restTemplate;
    }

    @GetMapping("test")
    public String testEndpoint() {
        this.noteCreationCounter.increment();
        long startTime = System.currentTimeMillis();
        logger.info("test endpoint");
        this.noteCreationTimer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
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
        List<WikipediaData> wikiData = wikipediaRepository.findByTitle(name);
        return wikiData;
    }

    @GetMapping("gptautocomplete")
    public String getGpt(@RequestParam("text") String name) {
        return chatGptClient.generateText(name);
    }

    @GetMapping("getCity")
    public String getCountries(@RequestParam("ip") String ip) {
        IpData data = getIpData(ip);
        List<WikipediaData> wikiData = wikipediaRepository.findByTitle(data.getCity());
       // /*
        if (wikiData.size() == 0) {
            return null;
        }
        String text = wikiData.get(0).text();
        String prompt = "povzemi dano besedilo";
        String textZaChatGpt = "";
        if (text.length() > 4097) {
            textZaChatGpt = text.substring(0, 4097) + "\n" + prompt;
        } else {
            textZaChatGpt = text + "\n" + prompt;
        }
        String response = chatGptClient.generateText(textZaChatGpt);
       // */
        //String text = wikiData.get(0).text();
        return response;
    }

    public IpData getIpData(String ip) {
        IpData data = restTemplate.getForObject("https://ipapi.co/{ip}/json/", IpData.class, ip);
        return data;
    }

    @GetMapping("wikipedia/{ip}")
    public List<WikipediaData> testEndpoint5(@PathVariable String ip) {
        IpData data = getIpData(ip);
        return wikipediaRepository.findByTitle(data.getCity());
    }
}













