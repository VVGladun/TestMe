# Test Me

## SETUP
IMPORTANT: the consumer key and secret required for the API calls are excluded from the repo using git ignore!

Please copy the consumer key and consumer secret to the file `local.properties` in the root folder
as CONSUMER_KEY and CONSUMER_SECRET respectively.
Alternatively, replace the values in the build.gradle.kts

The coding test project is based on the out-of-the box empty activity project created in
Android Studio Narwhal 3 Feature Drop | 2025.1.3 (August 28, 2025)
and wasn't tested with different versions of Android Studio.

## Architecture
I decided to use MVVM(*ish) for the presentation layer since it's the industry standard nowadays.
The packaging is (loosely) inspired by the Robert "Uncle Bob" Martin's clean architecture,
though in real life project I would use feature/domain-based packages for the top level structuring.
To keep things a bit simpler, I also decided not to create separate UseCase classes and just use the repository interface in my ViewModel.

## Trade-offs and possible improvements
If the listing results were more than just 20 items, I would implement a pagination support.

Since the real (prod) app has a lot of large lazy loading lists, I would've created a generic base class
for paginated content first and then extended it for listings and other use cases (also see my comments in the base ScreenLceState). 

In the large real life projects with a much bigger and more complex repositories, I would add UseCase classes in the domain package as an extra layer of abstraction
and a cleaner business logic driven structure in our domain layer.

I decided not to spent a lot of time on creating a proper style guide system.
Instead, I am left the default template material theme pretty much as is and just added some simple extension for spacing 
and created a (composition local provided) theme with a few custom colors to demonstrate different common approaches in Compose codebases 