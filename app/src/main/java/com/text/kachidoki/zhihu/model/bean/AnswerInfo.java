package com.text.kachidoki.zhihu.model.bean;

/**
 * Created by Frank on 15/5/2.
 */
public class AnswerInfo {
    private int totalCount;
    private int totalPage;
    private Answer[] answers;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        answers = answers;
    }

    public class Answer {
        private String id;
        private String authorId;
        private String authorName;
        private String authorFace;
        private String content;
        private String date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String data) {
            this.date = date;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorFace() {
            return authorFace;
        }

        public void setAuthorFace(String authorFace) {
            this.authorFace = authorFace;
        }
    }
}
