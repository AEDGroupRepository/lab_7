/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab7.analytics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;
import lab7.entities.Post;
import lab7.utils.MapSort;
import lab7.entities.Comment;
import lab7.entities.User;

/**
 *
 * @author harshalneelkamal
 */
public class AnalysisHelper {
    // find user with Most Likes
    //  key: UserId ; Value: sum of likes from all comments
    public void userWithMostLikes() {
        Map<Integer, Integer> userLikesCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
    
        for (User user : users.values()) {
            for (Comment c : user.getComments()) {
                int likes = 0;
                if (userLikesCount.containsKey(user.getId())) {
                    likes = userLikesCount.get(user.getId());
                }
                likes += c.getLikes();
                userLikesCount.put(user.getId(), likes);
            }
        }
        int max = 0;
        int maxId = 0;
        for (int id : userLikesCount.keySet()) {
            if (userLikesCount.get(id) > max) {
                max = userLikesCount.get(id);
                maxId = id;
            }
        }
        System.out.println("User with most likes: " + max + "\n" 
            + users.get(maxId));
    }
    
    // find 5 comments which have the most likes
    public void getFiveMostLikedComment() {
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        List<Comment> commentList = new ArrayList<>(comments.values());
        
        Collections.sort(commentList, new Comparator<Comment>() {
            @Override 
            public int compare(Comment o1, Comment o2) {
                return o2.getLikes() - o1.getLikes();
            }
        });
        
        System.out.println("5 most likes comments: ");
        for (int i = 0; i < commentList.size() && i < 5; i++) {
            System.out.println(commentList.get(i));
        }
    }
    public void getAverageLikesPerComment(){
        Map<Integer,Comment> comments = DataStore.getInstance().getComments();
        List<Comment> commentList = new ArrayList<>(comments.values());
        double total_likes = 0;
        int comment_num = commentList.size();
        
        for(Comment comment : commentList){
            total_likes+=comment.getLikes();
        }
        
        double average_likes = total_likes/comment_num;
        System.out.println("The average number of all comments is " + average_likes);
     }
    public void getPostByMostLikedComments(){
        Map<Integer, Post> postHashMap = DataStore.getInstance().getPosts();
        Map<Integer,Integer> tempPostHashMap = new HashMap<>();
        for(Post p:postHashMap.values()){
            for(Comment c:p.getComments()){
            int likes = 0;
            if(tempPostHashMap.containsKey(p.getPostId())){
                likes = tempPostHashMap.get(p.getPostId());
            }
            likes+=c.getLikes();
            tempPostHashMap.put(p.getPostId(), likes);
            }
        }
        int max = 0;
        int maxId = 0;
        for(int id:tempPostHashMap.keySet()){
            if(tempPostHashMap.get(id)>max){
                max = tempPostHashMap.get(id);
                maxId = id;
            }
        }
         System.out.println("Post with most likes: " + max + "\n"
                + postHashMap.get(maxId)+maxId);
    }
    
    public void getPostWithMostComments(){
        Map<Integer, Post> postHashMap = DataStore.getInstance().getPosts();
        List<Post> postList = new ArrayList<>(postHashMap.values());
        
        
        Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getComments().size()-p1.getComments().size();
            }
        });
        
        System.out.println("Post with most comments: "+postList.get(0).getComments().size()+"\n"
                +postList.get(0));

    }
    
}
