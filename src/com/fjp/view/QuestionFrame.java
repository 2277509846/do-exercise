package com.fjp.view;

import com.fjp.entity.Question;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class QuestionFrame extends JFrame {
    private JButton next = new JButton("下一个");
    private JButton previous = new JButton("上一个");
    private JTextArea descriptionTextArea = new JTextArea(10, 40);
    private JPanel choosePanel = new JPanel();
    private ButtonGroup chooses = new ButtonGroup();
    private JRadioButton chooseA = new JRadioButton("A");
    private JRadioButton chooseB = new JRadioButton("B");
    private JRadioButton chooseC = new JRadioButton("C");
    private JRadioButton chooseD = new JRadioButton("D");
    private String[] answers;
    private JButton ensure = new JButton("确定");
    private JButton importQuestion = new JButton("导入题目");
    private List<Question> questions = new ArrayList<Question>();
    private int index;
    private JTextField exerciseNum = new JTextField(2);
    private JButton go = new JButton("跳转");

    public QuestionFrame() {
        init();
        addListener();
    }

    //为组件添加监听器
    private void addListener() {
        next.addActionListener((e) -> choose(1));
        previous.addActionListener((e) -> choose(-1));
        importQuestion.addActionListener((e) -> {
            index = 0;
            setTitle("1");
            questions = questionList();
            Collections.shuffle(questions);
            answers = new String[questions.size()];
            descriptionTextArea.setText(questions.get(0).getDescription());
        });
        ensure.addActionListener((e) -> {
            choose(0);
            int c = 0;
            for (String answer : answers) if (answer == null) c++;
            if (c > 0) {
                JOptionPane.showMessageDialog(QuestionFrame.this, "还有" + c + "题没有完成！");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < answers.length; i++) {
                if (!answers[i].equals(questions.get(i).getAns()))
                    stringBuilder.append("第" + (i + 1) + "题错误，答案：" + questions.get(i).getAns()).append("\n");
            }
            if (stringBuilder.toString().equals("")) stringBuilder.append("恭喜，完全正确！");
            JOptionPane.showMessageDialog(QuestionFrame.this, stringBuilder.toString());
        });
        go.addActionListener((e) -> {
            try {
                int num = Integer.parseInt(exerciseNum.getText());
                if (num > 0 && num <= answers.length) {
                    index = num - 1;
                    chooses.clearSelection();
                    choose(0);
                } else {
                    JOptionPane.showMessageDialog(QuestionFrame.this, "题号不存在！");
                }
            } catch (NumberFormatException | HeadlessException ex) {
                JOptionPane.showMessageDialog(QuestionFrame.this, "题号必须为整数！");
            }
        });
    }

    //初始化界面
    private void init() {
        setTitle(index + 1 + "");
        setSize(500, 300);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = getContentPane();
        descriptionTextArea.setEnabled(false);
        descriptionTextArea.setDisabledTextColor(Color.BLACK);
        descriptionTextArea.setLineWrap(true);
        chooses.add(chooseA);
        chooses.add(chooseB);
        chooses.add(chooseC);
        chooses.add(chooseD);
        choosePanel.add(new JLabel("                                      "));
        choosePanel.add(chooseA);
        choosePanel.add(chooseB);
        choosePanel.add(chooseC);
        choosePanel.add(chooseD);
        choosePanel.add(new JLabel("                                      "));
        content.add(descriptionTextArea);
        content.add(choosePanel);
        content.add(previous);
        content.add(ensure);
        content.add(next);
        content.add(importQuestion);
        content.add(exerciseNum);
        content.add(go);
    }

    //页数改变时，改变选项的选择状态
    private void choose(int type) {
        if (chooseA.isSelected()) answers[index] = "A";
        if (chooseB.isSelected()) answers[index] = "B";
        if (chooseC.isSelected()) answers[index] = "C";
        if (chooseD.isSelected()) answers[index] = "D";
        if (type == 1) {
            index++;
            if (index == questions.size()) index = questions.size() - 1;
        } else if (type == -1) {
            index--;
            if (index == -1) index = 0;
        }
        descriptionTextArea.setText(questions.get(index).getDescription());
        chooses.clearSelection();
        setTitle(index + 1 + "");
        if (answers[index] != null) {
            switch (answers[index]) {
                case "A":
                    chooseA.setSelected(true);
                    break;
                case "B":
                    chooseB.setSelected(true);
                    break;
                case "C":
                    chooseC.setSelected(true);
                    break;
                case "D":
                    chooseD.setSelected(true);
                    break;
            }
        }
    }

    //获取问题列表
    protected abstract List<Question> questionList();

}
