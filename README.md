# QuizApp

Quiz App is an application based on creating and solving quizzes. In order to log in to the application, you must first create an account. You can change your profile picture, add your real name or write a short article about you. You can create your own quiz. You can edit quizzes that you have created yourself. You can search for different quizzes on the search screen and solve the quiz if you want. You earn points for each correct answer you give in the quizzes. You can see your score on the profile screen.

You can change your password at any time. If you forget your password, you can reset your password using the "forgot password" feature. You can delete your account if you wish. After logging into the app once, you do not need to log in again until your token expires. If your token expires, you will be redirected to the login screen (token renewal feature will be added soon).

## Outputs 🖼

|                 | Output |                 | Output |
|-----------------|--------|-----------------|--------|
| Sign In         | <img src="https://user-images.githubusercontent.com/73544434/230725801-7eb27e0b-1b80-4dd3-97a3-7e413d630aaa.png" width="240" height="480"/>       | Register        | <img src="https://user-images.githubusercontent.com/73544434/230725827-2ea03d1a-da8d-4aea-a62f-96d26b5bcbaa.png" width="240" height="480"/>       |
| Forgot Password | <img src="https://user-images.githubusercontent.com/73544434/230725856-6259aee7-a32a-4115-9cb5-5ac989162488.png" width="240" height="480"/>       | Home            | <img src="https://user-images.githubusercontent.com/73544434/230725892-664f99cf-d6c1-4714-9e3a-eaa9de05e02f.png" width="240" height="480"/>       |
| Search          | <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNjY4MzMxZTBmOTc3MzM3OGEzNjhjMzVmNWU3YTkxZTQ3OTE0YTQxMyZjdD1n/TUxjODywAlkQwylYW7/giphy.gif" width="240" height="480"/>        | Leaderboard     | <img src="https://user-images.githubusercontent.com/73544434/230725983-e2924338-6bc8-4c86-876f-adcf4c00b9a3.png" width="240" height="480"/>        |
| Profile         |  <img src="https://user-images.githubusercontent.com/73544434/230726010-997a3a9d-6482-4245-903f-386336c5c8fb.png" width="240" height="480"/>       | Edit Profile    |  <img src="https://user-images.githubusercontent.com/73544434/230726399-681bfdc5-4f75-49a1-ad76-2a66fc50531d.png" width="240" height="480"/>       |
| Update Profile  |  <img src="https://user-images.githubusercontent.com/73544434/230726429-43c73974-2569-43a1-8987-8bc1b0c253e9.png" width="240" height="480"/>       | Update Password | <img src="https://user-images.githubusercontent.com/73544434/230726444-39305a7f-239f-4ba9-887c-f9499227c7da.png" width="240" height="480"/>        |
| Create Quiz     |  <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNGI2ZGI0MTI3MDgzYTRiOTdjNDQ3ZGZlNWQxM2Q5Y2Y3MjFlZDZlMiZjdD1n/6THt8GGIoZS7NCoI6h/giphy.gif" width="240" height="480"/>       | Update Quiz     |  <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNDE1MGVhODJkMjg5NTdlMmVmODI4ZDFkMjEwZWYwODVlYTNjMzgzMCZjdD1n/nv1dtX6hr73B9NsZkT/giphy.gif" width="240" height="480"/>       |  
| Quiz           | <img src="https://media.giphy.com/media/oWDH8WO6lGBvNxKS5v/giphy.gif" width="240" height="480"/>          | Contact Us |  <img src="https://user-images.githubusercontent.com/73544434/230726462-7d6daeab-f4b6-486f-856f-07d6a0225c10.png" width="240" height="480"/>      |
| Delete Account | <img src="https://user-images.githubusercontent.com/73544434/230726485-b308a742-a37b-4f94-a8b5-5b064bb5ee29.png" width="240" height="480"/>        |            |        |

## Backend-Frontend 📦

You can find the backend code of the application [here](https://github.com/muammer-yilmaz/QuizApp).

You can find the frontend codes written for our project's website [here](https://github.com/frknsprnl/quiz-app).


## Tech Stack 📚

* [Navigation](https://developer.android.com/jetpack/compose/navigation)

* [Retrofit](https://square.github.io/retrofit)

* [ViewModel](https://developer.android.com/jetpack/compose/libraries#viewmodel)

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

* [Accompanist](https://google.github.io/accompanist/insets/)

* [Coil](https://coil-kt.github.io/coil/image_loaders/)

* [Okhttp](https://square.github.io/okhttp/)

* [Paging](https://developer.android.com/jetpack/androidx/releases/paging)

* [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences)

* [Animations](https://developer.android.com/jetpack/compose/animation)


## Architecture 🏗
The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.


![mvvm](https://user-images.githubusercontent.com/73544434/197416569-d42a6bbe-126e-4776-9c8f-2791925f738c.png)

## Warning ⚠
The application is still under development. Therefore, some features may not work.
