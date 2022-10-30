#Movie DB Apps
Movie-DB Apps is apps to displaying list of movie that has feature :
    1. Popular Movies
    2. Now Playing Movies
    3. Upcoming Movies
    4. Favorite Movies
    5. Search Movies
    6. Display Detail of Movies

Movies Sources is from TMDB, TMDB is a community built movie and TV database.
Every piece of data has been added by our amazing community dating back to 2008.
TMDb's strong international focus and breadth of data is largely unmatched and something we're incredibly proud of.
Put simply, we live and breathe community and that's precisely what makes us different.

## Screeshots
<p align="center">
<img src="/preview/first_screen.png" width="32%"/>
<img src="/preview/second_screen.png" width="32%"/>
</p>

## How to build on your environment
Add your [The Movie DB](https://www.themoviedb.org)'s API key in build.gradle file.
Build and Lets Rock 😉

## The app has following packages:
1. **adapter**: Adapter for displaying recycle list.
2. **api**: Interface from TMDB API
3. **db**: Database package for creating room instances and DAO file.
4. **model**: Entity class for API or Database Params.
5. **module**: Koin module to dependency injection
6. **repository** Helper class to call API or Room Database
7. **ui** Containing Activity, Fragment or other that related to User Interface
8. **utils** Ultilities package that contains helper class
9. 
## Libraries Used

This apps using MVVM Architecture with Koin and other libraries like :

- Koin (💉Dependency Injection) https://insert-koin.io/
- Retrofit & okhttp Logging Interceptor  https://square.github.io/retrofit/
- Glide https://github.com/bumptech/glide
- Shimmer https://facebook.github.io/shimmer-android/
- ViewModel
- LiveData
- Navigation Component
- Room
- Kdoc-er & Dokka https://kotlinlang.org/docs/kotlin-doc.html#module-and-package-documentation
- Mockito https://site.mockito.org/
- MockWebServer 🎭 https://github.com/square/okhttp/tree/master/mockwebserver