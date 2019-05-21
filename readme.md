# SportsFeed

This a master/detail app that displays a feed of the ongoing, recently finished, and upcoming sports games using ESPN's
public underlying API (no API key needed).

## Overview

This Kotlin app mostly follows the MVVM architecture as outlined in
[the Android Jetpack architecture guide](https://developer.android.com/jetpack/docs/guide).
Additionally, inspiration was gathered from
[the GithubBrowserSample example application](https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample)
for overall project layout and structure.
API requests are made to
[`ESPNService`](app/src/main/java/com/alexvanyo/sportsfeed/api/ESPNService.kt)
from the
[`FeedRepository`](app/src/main/java/com/alexvanyo/sportsfeed/repository/FeedRepository.kt),
and the results of the API requests (as specified in
[`api`](app/src/main/java/com/alexvanyo/sportsfeed/api))
are observed by a
[`FeedViewModel`](app/src/main/java/com/alexvanyo/sportsfeed/viewmodel/FeedViewModel.kt).
This view model, which is shared between the two fragments,
[`FeedFragment`](app/src/main/java/com/alexvanyo/sportsfeed/ui/FeedFragment.kt)
and
[`CompetitionFragment`](app/src/main/java/com/alexvanyo/sportsfeed/ui/CompetitionFragment.kt),
maintains the underlying state, and through the use of databinding the fragments can observe changes and update their
views accordingly.

This approach greatly simplifies state management with regards to the Android lifecycle, and also cleanly creates a
separation of concerns.
The power of RxJava can be used to set up smart, lifecycle-based polling to the
[`ESPNService`](app/src/main/java/com/alexvanyo/sportsfeed/api/ESPNService.kt),
while the fragments simply need to display a complete list of competitions.
Additionally, the databinding makes polymorphic layouts relatively easy, and overall tests can be much simpler.

## Testing
Robolectric is used instead of pure Android integration tests to enable testing without needing a device or emulator.
Thus all of the tests are in [`app/src/test`](app/src/test), which includes both Robolectric tests and plain JUnit
tests.

## Libraries

- [AndroidX](https://developer.android.com/jetpack/androidx)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Dagger 2](https://google.github.io/dagger/) for dependency injection
- [RxJava](https://github.com/ReactiveX/RxJava) for event creation, transformation and observation
- [Glide](https://github.com/bumptech/glide) for image loading
- [RetroFit](https://square.github.io/retrofit/) for API requests

#### Testing

- [JUnit 4](https://junit.org/junit4/)
- [Robolectric](http://robolectric.org/) for Android framework unit tests
- [Espresso](https://developer.android.com/training/testing/espresso)
- [Mockito](https://site.mockito.org/) for mocking