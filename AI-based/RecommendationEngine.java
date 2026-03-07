import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;

public class RecommendationEngine {

    public static String getRecommendations(int userId) {

        String result = "";

        try {

            DataModel model = new FileDataModel(new File("data/dataset.csv"));

            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);

            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(2, similarity, model);

            UserBasedRecommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);

            List<RecommendedItem> recommendations =
                    recommender.recommend(userId, 3);

            // CHECK IF RECOMMENDATIONS EXIST
            if (recommendations.isEmpty()) {

                result = "No recommendations found for this user.";

            } else {

                for (RecommendedItem item : recommendations) {

                    result += "Product ID: " + item.getItemID()
                            + "  Score: "
                            + item.getValue()
                            + "\n";
                }
            }

        } catch (Exception e) {

            result = "Error: " + e.getMessage();

        }

        return result;
    }
}