# WithingsTest2 app


## Preview Video
coming soon

## Intro
This is an Android app for generating gif images.

It's a Compose project that uses pixabay api to search for images using keywords and displays them on a grid.
The user has the option to select images from the grid and generate a gif image.

The app uses StateFlow for data transmission, LazyVerticalStaggeredGrid for grid display and WorkManager for bitmap processing.

## Instructions
Before compiling the project, add a PIXABAY_API_KEY to your local.properties file as the following: 
PIXABAY_API_KEY = your_key_here

## Obfuscation
The source code has obfuscation and shrinkResources enabled. It uses an external obfuscation dictionary available at https://bit.ly/3uGrnSu