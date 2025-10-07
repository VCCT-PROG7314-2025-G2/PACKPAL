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
