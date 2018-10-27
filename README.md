# TMDB ![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

TMDB is a challenge mobile project for listing upcoming movies from the The movie database library.

## Libraries used
### Livedata
* Used it's observables for getting the events from the data source
### ViewModel
* As this project implements MVVM, ViewModel is being used for handling responsively the view changes
### Paging Library
* Since it's a new Google's library for handling easily the network calls with paging, It's being used for that.
### Retrofit
* It's the chosen client for performing the network calls
### RxJava 2
* It handles the Retrofit calls responsively and maps the results with the help of the Mapstruct for passing it to the Paging Library
### Mapstruct
* For autogenerating the mappers which transform the Network response into ViewModel usable model objects easily

## Screen shots
![Example](https://image.ibb.co/iymc0q/screenshot.png)
![Example](https://image.ibb.co/h3zamV/schreenshot2.png)

## Contributing
Pull requests are welcome.