

# CatDogApp

This project is is an Android Application that shows the user different minigames related to Cats and Dogs. As for now, we have 2 minigames: Image and Facts Generator and Trivia.
For this project we use several Open Source API, that will help us retrieve the images and facts we need.

## API endpoints

- [Cat Image API](https://api.thecatapi.com/v1/images/search)
- [Dog Image API](https://api.thedogapi.com/v1/images/search)
- [Cat Fact API](https://catfact.ninja/fact)
- [Dog Fact API](https://dogapi.dog/api/v2/facts)

## How to make an API call
1. First, you need to create the POJO (Plain Old Java Object) That represents the structure of the JSON that you are parsing through the API. Take as an example this JSON for the Dog Fact.

```json
{
    "data": [{
        "id": "082f9b07-1566-4cf5-863e-f17a00833311",
        "type": "fact",
        "attributes": {
            "body": "Basset Hounds cannot swim."
        }
    }]
}
```

For this we only want the body value, so we can ignote the id and type values.
In order to access the body value, we need to create a _Class representation_ of the JSON. In this case we will need 3 classes.
- DogFact.java
```java
public class DogFact {
    @SerializedName("data")
    @Expose
    private Data[] data;
    public Data[] getData() {
        return data;
    }
}
```

- Data.java
```java
public class Data {
    @SerializedName("attributes")
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }
}

```
- Attributes.java
```java
public class Attributes {
    @SerializedName("body")
    private String body;

    public String getBody() {
        return body;
    }
}
```

2. Then, you need to create an interface which will allow you to get the value of the JSON when making the API call. This interface has a @GET annotation that will make the Http request, this annotation receives as a parameter the API endpoint (excluding the domain name).

```java
public interface DogFactApi {
    @GET("api/v2/facts")
    Call<DogFact> getDogFact();
}
```

3. With everything done, on the Generator.java class we create a method that will use Retrofit2 library to make the API call. Like this:

```java
public void generateDogFact(TextView textView){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dogapi.dog") // API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogFactApi dogFactApi = retrofit.create(DogFactApi.class);
        Call<DogFact> factCall = dogFactApi.getDogFact();

        factCall.enqueue(new Callback<DogFact>() {
            @Override
            public void onResponse(Call<DogFact> call, Response<DogFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DogFact jsonResponse = response.body();
                    List<Data> dogFactList =  new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    String text = dogFactList.get(0).getAttributes().getBody();
                    textView.setText(text);
                }
            }
            @Override
            public void onFailure(Call<DogFact> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }
```
## Main files

### MainActivity.java
- Shows a simple Main Screen consisiting of ImageButtons and Textviews that let the user choose either dog or cat.

### Generator.java
- Creation of the methods for making the API call for the Image and Fact using Retrofit2.
- Checks if the User chose Cat or Dog, depending on the choice we call the corresponding methods for generating the Fact and Image.
- Creation of the navigation bar connected to the Main and Trivia activities.

## Trivia.java
- Checks if the User chose Cat or Dog, depending on the choice we call the corresponding methods for generating image and retrieving questions.
- Creates an instance of the generator to show a random image on the Trivia.
- Creation of several methods to manage the JSON:
    - loadJSONFromAsset: Loads the JSON file received as a parameter
    - readJSON: Reads the JSON file, saves the data on arrays and puts the data randomly grabbed from the array on the Views.


## Secondary files

├───cat
│   ├───api
│   └───pojo
├───dog
│   ├───api
│   └───pojo

