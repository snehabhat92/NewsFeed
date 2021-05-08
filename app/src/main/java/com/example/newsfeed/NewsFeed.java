package com.example.newsfeed;

import java.util.ArrayList;
import java.util.Comparator;

public class NewsFeed {
private String status;
private ArrayList<Articles> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "NewsFeed{" +
                "status='" + status + '\'' +
                ", articles=" + articles +
                '}';
    }

    public class Articles {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        @Override
        public String toString() {
            return "Articles{" +
                    "author='" + author + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", url='" + url + '\'' +
                    ", urlToImage='" + urlToImage + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    '}';
        }
    }

    public static class ArticleComparatorAscending implements Comparator<Articles> {

        @Override
        public int compare(Articles articles1, Articles articles2) {
            if(articles1 != null && articles2 != null) {
                return articles1.getPublishedAt().compareTo(articles2.getPublishedAt());
            }
            return -1;

        }

    }

public static class ArticleComparatorDescending implements Comparator<NewsFeed.Articles> {

    @Override
    public int compare(NewsFeed.Articles articles1, NewsFeed.Articles articles2) {
        if(articles1 != null && articles2 != null) {
            return articles2.getPublishedAt().compareTo(articles1.getPublishedAt());
        }
        return -1;

    }

}}