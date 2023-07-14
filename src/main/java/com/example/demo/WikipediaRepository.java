package com.example.demo;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WikipediaRepository extends ElasticsearchRepository<WikipediaData, Long> {
    List<WikipediaData> findByTitle(String title);
    @Query("{\"match\": {\"title\": {\"query\": \"?0\"}}}")
    List<WikipediaData> findByTitleCustom(String title);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"text\": \"?1\"}}]}}")
    List<WikipediaData> findByTownAndCountry(String town, String country);

}