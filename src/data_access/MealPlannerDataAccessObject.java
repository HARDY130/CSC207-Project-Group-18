package data_access;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;

public class MealPlannerDataAccessObject {
    private final String APP_ID = "8b482516";
    private final String APP_KEY = "962b81bb247fe98c9623b284e3604e7c";
    private final String BASE_URL = "https://api.edamam.com/api/meal-planner/v1";
    private final String Authorization = "Basic OGI0ODI1MTY6IDk2MmI4MWJiMjQ3ZmU5OGM5NjIzYjI4NGUzNjA0ZTdjCQ==";
    private final HttpClient httpClient;

    public MealPlannerDataAccessObject(){
        this.httpClient = HttpClient.newHttpClient();
    }

    String testingJsonBody = """
                {
                  "size": 7,
                  "plan": {
                    "accept": {
                      "all": [
                        {
                          "health": [
                            "SOY_FREE",
                            "FISH_FREE",
                            "MEDITERRANEAN"
                          ]
                        }
                      ]
                    },
                    "fit": {
                      "ENERC_KCAL": {
                        "min": 1000,
                        "max": 2000
                      },
                      "SUGAR.added": {
                        "max": 20
                      }
                    },
                    "exclude": [
                      "http://www.edamam.com/ontologies/edamam.owl#recipe_x",
                      "http://www.edamam.com/ontologies/edamam.owl#recipe_y",
                      "http://www.edamam.com/ontologies/edamam.owl#recipe_z"
                    ],
                    "sections": {
                      "Breakfast": {
                        "accept": {
                          "all": [
                            {
                              "dish": [
                                "drinks",
                                "egg",
                                "biscuits and cookies",
                                "bread",
                                "pancake",
                                "cereals"
                              ]
                            },
                            {
                              "meal": [
                                "breakfast"
                              ]
                            }
                          ]
                        },
                        "fit": {
                          "ENERC_KCAL": {
                            "min": 100,
                            "max": 600
                          }
                        }
                      },
                      "Lunch": {
                        "accept": {
                          "all": [
                            {
                              "dish": [
                                "main course",
                                "pasta",
                                "egg",
                                "salad",
                                "soup",
                                "sandwiches",
                                "pizza",
                                "seafood"
                              ]
                            },
                            {
                              "meal": [
                                "lunch/dinner"
                              ]
                            }
                          ]
                        },
                        "fit": {
                          "ENERC_KCAL": {
                            "min": 300,
                            "max": 900
                          }
                        }
                      },
                      "Dinner": {
                        "accept": {
                          "all": [
                            {
                              "dish": [
                                "seafood",
                                "egg",
                                "salad",
                                "pizza",
                                "pasta",
                                "main course"
                              ]
                            },
                            {
                              "meal": [
                                "lunch/dinner"
                              ]
                            }
                          ]
                        },
                        "fit": {
                          "ENERC_KCAL": {
                            "min": 200,
                            "max": 900
                          }
                        }
                      }
                    }
                  }
                }
                """;

    public JSONObject searchFood(String jsonBody) throws Exception {
        String endpoint = String.format("%s/%s/select?beta=true",
                BASE_URL, APP_ID);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", Authorization)
                .header("Edamam-Account-User", "HARDY130")
                .POST(HttpRequest.BodyPublishers.ofString(testingJsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("API request failed with status: " + response.statusCode() + response.body());
        }

        return new JSONObject(response.body());
    }
}