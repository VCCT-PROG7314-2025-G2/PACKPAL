# PackPal - Travel Planning App
<p align="center">
  <img width="208" height="65" alt="packpalsignup" src="https://github.com/user-attachments/assets/c5123b75-f2d1-4538-ad7c-691f61cfcf07" />
</p>

---

## **Overview**
PackPal is a travel planning app for Android that makes organizing and organizing trips easier. With customizable features like trip name, country, start and end dates, notes, and trip types (business, leisure, family, and adventure), users can create, modify, and delete trips using this app. A contact support page, user profile management, upcoming trip overviews, dynamic packing list generation based on weather conditions and trip duration, and real-time weather updates for trip destinations are some of the key features.

---

## **Purpose**
By providing a centralized platform for efficiently planning and organizing travel, PackPal aims to improve the travel planning experience. Through the integration of weather information from the OpenWeatherMap API, the application guarantees that users receive customized packing recommendations according to the conditions at their destination. It serves a broad spectrum of tourists, including both business professionals and leisure travelers.

---

## **Design Considerations**
### **User Interface (UI)**
- **Intuitive Navigation**: Utilizes a bottom navigation bar with `BottomNavigationView` and `NavController` for seamless access to main sections.
- **Responsive Layouts**: XML layouts (e.g., `activity_create_trips.xml`, `trip_item.xml`) use `ConstraintLayout` for adaptability across devices.
- **Visual Feedback**: Dynamic weather icons and notifications enhance user engagement.

- ### **Functionality**
- **Data Management**: In-memory storage via `TripRepository` for quick access in this prototype.
- **Weather Integration**: Real-time data fetched with OkHttp, processed to inform packing lists.
- **Security**: Basic profile management with password updates and account deletion using `SharedPreferences`.
- **Notifications**: Implemented with `NotificationCompat` for achievement alerts.

- ### **Performance**
- **Efficiency**: Asynchronous HTTP requests ensure smooth UI performance.
- **Scalability**: Modular design supports future features like persistent storage.

### **Accessibility**
- Clear labels, hints, and toast messages cater to users of varying technical familiarity.

---

## **Utilization of GitHub and GitHub Actions**

### **GitHub Repository**
The PackPals project is hosted on GitHub with multiple commits tracking iterative development (e.g., "Added weather API integration which is OpenWeather API"). The repository is structured as:
- `app/src/main/java/` - Kotlin source files.
- `app/src/main/res/layout/` - XML layouts.
- `README.md` - This documentation.

- ---

## **Screenshots**
<p align="center">
  <img width="332" height="637" alt="image" src="https://github.com/user-attachments/assets/c807ab4a-b029-4d93-b273-ccb605b82d9b" />
  <img width="328" height="657" alt="image" src="https://github.com/user-attachments/assets/eca76aaf-24a0-445b-9b10-32157953987c" />
</p>

---

## **Future Improvements**
- Integrate a database (e.g., Room) for persistent trip storage.
- Enhance security with encrypted password storage.
- Add offline mode for weather data caching.

---
## **YouTube Video**
https://youtu.be/LLNWOuGSlLU
---
## **Contributors**
- [Liyema Mangcu] - ST10143385
- [Ganeef Salie] - ST10214012
- [Onello Travis Tarjanne] - ST10178800
- [Khenende Netshivhambe] - ST10379469

---
## **References**

- The Zone (2021). Android x Kotlin Beginner Tutorial [2021] #0 - Project Introduction. [online] YouTube. Available at: https://www.youtube.com/watch?v=GP4p3d7_b2k&list=PLpZQVidZ65jPUF-o0LUvkY-XVAAkvL-Xb [Accessed 23 Apr. 2025].
- Coding with Dev (2023). android date picker dialog example | DatePickerDialog - Android Studio Tutorial | Kotlin. [online] YouTube. Available at: https://www.youtube.com/watch?v=DpL8DhCNKdE&list=PLv59B8ZxVvbv0k49hQxK10bT1fYEmWvMU&index=2 [Accessed 1 May 2025].
- Developer (2024). Date pickers. [online] Android Developers. Available at: https://developer.android.com/develop/ui/compose/components/datepickers [Accessed 1 May 2025].
- Developer (2025). Adapter | API reference | Android Developers. [online] Android Developers. Available at: https://developer.android.com/reference/kotlin/android/widget/Adapter [Accessed 1 May 2025].
- Foxandroid (2021). Custom ListView with item click using Kotlin in Android || ListView in Android || Kotlin. [online] YouTube. Available at: https://www.youtube.com/watch?v=KPvYXXERLjk&list=PLv59B8ZxVvbv0k49hQxK10bT1fYEmWvMU&index=1 [Accessed 1 May 2025].
- geeksforgeeks (2022). DatePicker in Android. [online] GeeksforGeeks. Available at: https://www.geeksforgeeks.org/datepicker-in-android/ [Accessed 1 May 2025].
- FineGap (2025). Available at: https://youtu.be/bhUuVb7g6qk?si=8i2Nw8w09nxzdwv4 [Accessed 1 May 2025].
- Android Knowledge. (2022). Easy Login Page in Android Studio using Kotlin - 5 Steps Only! - Android Knowledge. [online] Available at: https://androidknowledge.com/loginpage-in-android-kotlin/.
- CodeLabX (2025). Available at: https://youtu.be/8obgNNlj3Eo?si=szFq0AC1AIA9qwZ8 [Accessed 1 May 2025].
- The Code City (2025). Available at: https://youtu.be/WAejZCkLJAI?si=F3zcamsTl-dCJDBF [Accessed 1 May 2025].
- Android Knowledge (2023). ListView in Android Studio using Kotlin | Android Knowledge. [online] YouTube. Available at: https://www.youtube.com/watch?v=BntLlH4eSYA [Accessed 1 May 2025].
- ‌Android Developers. (2025). Display pop-up messages or requests for user input. [online] Available at: https://developer.android.com/develop/ui/compose/quick-guides/content/display-user-input [Accessed 10 Apr 2025].
- Shahi, S. (2023). How to create bottom navigation in Android using Kotlin- Beginners Guide. [online] Medium. Available at: https://medium.com/@shahisofiya575/how-to-create-bottom-navigation-in-android-using-material-design-9de844c484a [Accessed 10 Apr 2025].
- Philipp Lackner (2022). Local Notifications in Android - The Full Guide (Android Studio Tutorial). [online] YouTube. Available at: https://www.youtube.com/watch?v=LP623htmWcI [Accessed 26 Nov. 2024].
-  Andy's Tech Tutorials (2022). [Tutorial] - How to Use Weather API for Beginners | Open Weather Map API. [online] Youtu.be. Available at: https://youtu.be/MdIfZJ08g2I?si=gy4aTigN_vK77nzT [Accessed 28 Sep. 2025].
-  Flaticon. “Flaticon, the Largest Database of Free Vector Icons.” Flaticon, 2025, www.flaticon.com/.
