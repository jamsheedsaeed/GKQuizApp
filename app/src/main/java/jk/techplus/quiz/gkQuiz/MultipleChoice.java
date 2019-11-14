package jk.techplus.quiz.gkQuiz;

public class MultipleChoice {
    private String answer = "";
    private String choice1 = "";
    private String choice2 = "";
    private String choice3 = "";
    private String choice4 = "";

    /* renamed from: id */
    private int f33id = -1;


 public MultipleChoice(){

 }



    private String question = "";

    public MultipleChoice(String question , String ans){

        this.question = question;
        this.answer = ans;
    }


    public String getAnswer() {
        return this.answer;
    }

    public String getChoice1() {
        return this.choice1;
    }

    public String getChoice2() {
        return this.choice2;
    }

    public String getChoice3() {
        return this.choice3;
    }

    public String getChoice4() {
        return this.choice4;
    }

    public int getId() {
        return this.f33id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public void setChoice1(String str) {
        this.choice1 = str;
    }

    public void setChoice2(String str) {
        this.choice2 = str;
    }

    public void setChoice3(String str) {
        this.choice3 = str;
    }

    public void setChoice4(String str) {
        this.choice4 = str;
    }

    public void setId(int i) {
        this.f33id = i;
    }

    public void setQuestion(String str) {
        this.question = str;
    }
}
