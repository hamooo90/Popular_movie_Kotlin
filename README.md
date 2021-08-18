# Description 
A simple Movie database app that uses TheMovieDb api to show trending movies, search all the movies, view details of a movie and view cast info and pictures.

## Architecture 
- Written in Kotlin
- MVVM architectural pattern
- Clean code
- Single activity
- Dagger Hilt for dependency injection
- Retrofit for api call
- RxJava3 for network call
- Moshi to parse Json to object
- Glide for image loading
- uses Jetpack componnent
    - LiveData
    - ViewModel
    - Navigation
    - Paging

## Preview
<img src="demo.gif" width="300" />

## Online Emulator
[Open app in appetize.io](https://appetize.io/app/gb6hfzdpjp3zq7btapwzc4e57m "Popular movie")

## How to use
To build this source code in your Android studio you need to put your Api key for TheMovieDB in gradle.properties

    #tmdb_api_key=123456789101112131415

# Licence

    Copyright 2021 Hamed Vakhide
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
