

# CatDogApp

This project is is an Android Application that shows the user different minigames related to Cats and Dogs. As for now, we have 2 minigames: Image and Facts Generator and Trivia.
For this project we use several Open Source API, that will help us retrieve the images and facts we need.

## API endpoints

- [Cat Image API](https://api.thecatapi.com/)
- [Dog Image API](https://api.thedogapi.com/)
- [Cat Fact API](https://catfact.ninja)
- [Dog Fact API](https://dogapi.dog)

## Main files

### MainActivity.java
- Shows a simple Main Screen consisiting of ImageButtons and Textviews that let the user choose either dog or cat.

### Generator.java
- Creation of the methods for making the API call for the Image and Fact using Retrofit2.
- Checks if the User chose Cat or Dog, depending on the choice we call the corresponding methods for generating the Fact and Image.
- Creation of the navigation bar connected to the Main and Trivia activities.

## Trivia.java


