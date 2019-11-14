package jk.techplus.quiz.gkQuiz;

public class UserFeedback {

    private String useremail;
    private String userfeedback;

    public UserFeedback(String useremail, String userfeedback) {
        this.useremail = useremail;
        this.userfeedback = userfeedback;
    }
    public String getUseremail() {
        return useremail;
    }
    public String getUserfeedback() {
        return userfeedback;
    }
    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setUserfeedback(String userfeedback) {
        this.userfeedback = userfeedback;
    }
}
