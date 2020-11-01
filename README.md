<h1 align="center">Sports Discovery</h1>

<p align="center">  
 Sports Betting Simulation, Koin, Coroutines, Flow, Jetpack (Room, ViewModel,Navigation LiveData) based on MVVM architecture. Also fetching data from the network and integrating local data in the database via repository pattern.
</p>

<p align="center">
<img src="/previews/main_usage.gif" />
</p>

## Tech stack - Library
- [Kotlin](https://kotlinlang.org/) , [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) , [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [Koin](https://insert-koin.io/)
- [Kotlin-DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- JetPack
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) 
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) 
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) 
  - [Room](https://developer.android.com/topic/libraries/architecture/room)
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started)
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding)
  - [MVVM Architecture]() (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit)
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)

## Architecture
Sports Discovery is based on MVVM architecture and a repository pattern.

![architecture](https://raw.githubusercontent.com/fevziomurtekin/hackernewsapp/master/screenshot/mvvm.png)

## API
Used The Odds API. [Link](https://the-odds-api.com/liveapi/guides/v3/)

## Roadmap
- [ ] Betting Feature
- [ ] Polish UI with Material Designs
- [ ] Use WorkManager to sync data to DB asynchronously 
- [ ] Use View Binding instead of Kotlin synthetics
- [ ] Unit testing with JUnit and Mockito

## License
The Apache License 2.0 - see [`LICENSE`](LICENSE) for more details
