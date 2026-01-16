package com.recommendation;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RecommenderExample {

    public static void main(String[] args) {
        try {

            System.out.println(" Loading data...");

            // Load CSV from resources
            File file = new File(
                    RecommenderExample.class
                            .getClassLoader()
                            .getResource("data.csv")
                            .getFile()
            );
            DataModel model = new FileDataModel(file);

            // Similarity and neighborhood
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Loop through all users
            long[] userIDs = {1, 2, 3, 4}; // Add more if you have more users
            int howMany = 3; // Number of recommendations per user

            for (long userID : userIDs) {
                List<RecommendedItem> recommendations = recommender.recommend(userID, howMany);
                System.out.println("\n Recommendations for User " + userID + ":");
                for (RecommendedItem r : recommendations) {
                    System.out.println("Product ID: " + r.getItemID() + "  Score: " + r.getValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}









