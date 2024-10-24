package org.upe.controllers;

import org.upe.persistence.interfaces.ArticleInterface;
import org.upe.persistence.interfaces.EventInterface;
import org.upe.persistence.interfaces.UserInterface;
import org.upe.persistence.repository.ArticleUtility;
import org.upe.persistence.repository.EventUtility;
import org.upe.persistence.repository.UserUtility;
import java.util.List;

public class ArticleController {
    private static final UserUtility userUtility = new UserUtility();
    private static final EventUtility eventUtility = new EventUtility();
    private static final ArticleUtility articleUtility = new ArticleUtility();

    public ArticleInterface createArticle(UserInterface user, String name, String articleAbstract) {
        ArticleInterface article = articleUtility.createArticle(name, user.getCPF(), articleAbstract);
        userUtility.addUserArticle(user.getCPF(), article.getArticleID());
        return article;
    }


    static List<ArticleInterface> getAllArticlesByUser(String userCPF) {
        return articleUtility.getAllArticlesByUser(userCPF);
    }


    static boolean submitArticle(ArticleInterface article, EventInterface event) {
       for (String articleID : event.getArticleList()) {
           if (articleID.equals(article.getArticleID())) {
               return false;
           }
       }
        return eventUtility.addArticleOnList(article.getArticleID(), event.getId());
    }

}
