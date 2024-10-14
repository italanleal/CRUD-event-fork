package org.upe.controllers;

import org.upe.persistence.repository.interfaces.ArticleInterface;
import org.upe.persistence.repository.interfaces.EventInterface;
import org.upe.persistence.repository.interfaces.UserInterface;
import org.upe.persistence.repository.repository.ArticleUtility;
import org.upe.persistence.repository.repository.EventUtility;
import org.upe.persistence.repository.repository.UserUtility;
import java.util.List;

public class ArticleController {
    private static final UserUtility userUtility = new UserUtility();

    static ArticleInterface createArticle(UserInterface user, String name, String articleAbstract) {
        ArticleInterface article = ArticleUtility.createArticle(name, user.getCPF(), articleAbstract);
        userUtility.addUserArticle(user.getCPF(), article.getArticleID());
        return article;
    }


    static List<ArticleInterface> getAllArticlesByUser(String userCPF) {
        return ArticleUtility.getAllArticlesByUser(userCPF);
    }


    static boolean submitArticle(ArticleInterface article, EventInterface event) {
       for (String articleID : event.getArticleList()) {
           if (articleID.equals(article.getArticleID())) {
               return false;
           }
       }
        return EventUtility.addArticleOnList(article.getArticleID(), event.getId());
    }

}
