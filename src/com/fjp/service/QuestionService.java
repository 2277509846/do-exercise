package com.fjp.service;

import com.fjp.entity.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
    //获取问题列表
    public List<Question> questionList() {
        List<Question> questions = new ArrayList<Question>();
        try {
            File file = new File("questions.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("、")) {
                    String numStr = line.substring(0, line.indexOf("、")).trim();
                    try {
                        Integer.parseInt(numStr);
                        StringBuilder descriptionBuilder = new StringBuilder();
                        descriptionBuilder.append(line.substring(line.indexOf("、") + 1)).append("\n");
                        while (!(line = bufferedReader.readLine()).contains("ans: "))
                            descriptionBuilder.append(line).append("\n");
                        String description = descriptionBuilder.toString().trim();
                        String answer = line.substring(line.indexOf("ans: ") + 5).trim();
                        questions.add(new Question(description, answer));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            for (Question question : questions) {
                System.out.println(question);
            }
            bufferedReader.close();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return questions;
    }
}
