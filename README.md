# News APP
A sample android app that shows how to use ViewModels and Room together with Coroutine with Flow & HILT, in Kotlin by Clean Architecture.

## Implemented by Clean Architecture
The following project is structured with 3 layers:

- Presentation (app)
- Domain
- Data

## Features
- Kotlin based android application .
- UI calls method from ViewModel.
- ViewModel executes Use case
- Each Repository returns data from a Data Source (Cached using ROOM or Remote) .
- Rerofit for networking .
- HILT for Dependency injection .
- Jetpack compose navigation component

## Scenario
Used http://api.nytimes.com as a public api 

## At a glance

    get a list of News posts from api .
    user can click on one of the posts to display more info.
    Supported offline mode
