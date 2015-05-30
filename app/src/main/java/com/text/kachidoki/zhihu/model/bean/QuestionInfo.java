package com.text.kachidoki.zhihu.model.bean;

/**
 * Created by Frank on 15/5/1.
 */
public class QuestionInfo {
    private int totalCount;
    private int totalPage;
    private Question[] questions;

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

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }


    public class Question {
        private String title;
        private String content;
        private String bestAnswerId;
        private String authorName;
        private String authorFace;
        private String id;
        private String authorId;
        private String date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBestAnswerId() {
            return bestAnswerId;
        }

        public void setBestAnswerId(String bestAnswerId) {
            this.bestAnswerId = bestAnswerId;
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
