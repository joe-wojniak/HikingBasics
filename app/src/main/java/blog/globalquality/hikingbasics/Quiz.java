package blog.globalquality.hikingbasics;

/**
 * Quiz object for question & answer pairs from hikingBasics Firebase Project
 *
 * Firebase database quiz collections of questions and answers-
 * QuizAv - Avalanche
 * QuizLeaveNoTrace - LeaveNoTrace
 * QuizNav - Navigation
 * QuizSG - Steve Gladbach
 * QuizTenEssentials - 10 Essentials
 *
 */

public class Quiz {

    private String quiz;        // Firebase database collection
    private String name;        // User's name - public name
    private Integer quizScore;  // User's quiz score
    private String document;    // Firebase database document for 1 question & answer pair
    private String response;    // User's answer (response) to the question
    private String question;    // the question
    private String answer;      // the answer matching the question
    private String choice1;     // multiple choice answer #1
    private String choice2;     // multiple choice answer #2
    private String choice3;     // multiple choice answer #3
    private String choice4;     // multiple choice answer #4

    public Quiz(){
    }

    public Quiz(String quiz, String name, Integer quizScore, String document, String response,
                String question, String answer, String choice1, String choice2, String choice3,
                String choice4){
        this.quiz = quiz;
        this.name = name;
        this.quizScore = quizScore;
        this.document = document;
        this.response = response;
        this.question = question;
        this.answer = answer;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    // Getter methods
    public String getQuiz(){return quiz;}
    public String getName(){return name;}
    public Integer getQuizScore(){return quizScore;}
    public String getDocument(){return document;}
    public String getResponse(){return response;}
    public String getQuestion(){return question;}
    public String getAnswer(){return answer;}
    public String getChoice1(){return choice1;}
    public String getChoice2(){return choice2;}
    public String getChoice3(){return choice3;}
    public String getChoice4(){return choice4;}

    // Setter methods
    // only a User's score is updated in the database
    public void setQuizScore(Integer quizScore){this.quizScore=quizScore;}

}
