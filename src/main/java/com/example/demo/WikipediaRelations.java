package com.example.demo;

import java.util.List;

public record WikipediaRelations(String infobox, Boolean isPartName, Boolean isDisambiguation, List<String> categories, List<String> titleParenthesis, List<String> beCompRelations) {

}