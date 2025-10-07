PackPal - Travel Planning App
<img width="208" height="65" alt="packpalsignup" src="https://github.com/user-attachments/assets/adec0281-5a94-4706-8d93-78b402d902ce" />

Overview
PackPals is an Android-based travel planning application designed to simplify trip management and preparation. This app allows users to create, edit, and delete trips with customizable details such as trip name, country, start/end dates, notes, and trip types (e.g., business, leisure, family, adventure). Key features include real-time weather updates for trip destinations, dynamic packing list generation based on weather conditions and trip duration, user profile management, upcoming trip overviews, and a contact support page.

Purpose
The primary goal of PackPals is to enhance the travel planning experience by offering a centralized platform for organizing trips and preparing effectively. By integrating weather data from the OpenWeatherMap API, the app ensures users receive tailored packing suggestions based on their destination's conditions. It caters to a wide range of travelers, from casual adventurers to business professionals.

Design Considerations
User Interface (UI)

Intuitive Navigation: Utilizes a bottom navigation bar with BottomNavigationView and NavController for seamless access to main sections.
Responsive Layouts: XML layouts (e.g., activity_create_trips.xml, trip_item.xml) use ConstraintLayout for adaptability across devices.
Visual Feedback: Dynamic weather icons and notifications enhance user engagement.

Functionality

Data Management: In-memory storage via TripRepository for quick access in this prototype.
Weather Integration: Real-time data fetched with OkHttp, processed to inform packing lists.
Security: Basic profile management with password updates and account deletion using SharedPreferences.
Notifications: Implemented with NotificationCompat for achievement alerts.

Performance

Efficiency: Asynchronous HTTP requests ensure smooth UI performance.
Scalability: Modular design supports future features like persistent storage.

Accessibility

Clear labels, hints, and toast messages cater to users of varying technical familiarity.


Utilization of GitHub and GitHub Actions
GitHub Repository
The PackPals project is hosted on GitHub with multiple commits tracking iterative development (e.g., "Added weather API integration"). The repository is structured as:

app/src/main/java/ - Kotlin source files.
app/src/main/res/layout/ - XML layouts.
README.md - This documentation.

GitHub Actions
Automated testing is enabled via GitHub Actions with a workflow (e.g., .github/workflows/build.yml) that:

Triggers on push and pull requests.
Runs unit tests on an Android emulator or JVM with ./gradlew test.
Validates builds to ensure code quality.




Step
Description



Checkout Code
Fetches the latest repository code.


Setup JDK
Configures the Java environment.


Run Tests
Executes automated tests.


Build Validation
Checks for build errors.



Screenshots

  
  
  



Future Improvements

Integrate a database (e.g., Room) for persistent trip storage.
Enhance security with encrypted password storage.
Add offline mode for weather data caching.


Contributors

[Your Name] - Initial work and ongoing development.

