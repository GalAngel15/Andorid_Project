# Game Project - Dragon Ball

## Overview

This project is an Android game where the player navigates a character (goku) while avoiding obstacles and collecting bonuses that give him "shield". The game features animated characters, background music, and various game elements to enhance the player experience.

## Project Structure

The project is structured into several directories and files, each serving a specific purpose. Below is a detailed overview of the project structure and its key components:

### Directories and Files

1. **AndroidManifest.xml**
   - The manifest file that contains essential information about the app, such as its components, permissions, and metadata.

2. **ic_launcher-playstore.png**
   - The launcher icon for the app as it appears on the Play Store.

3. **Java Classes (located in `java/com/example/cargame/`)**
   - **Character.java**: Represents the player character and manages its attributes and behavior.
   - **GameManager.java**: Controls the overall game logic, including game state management and updates.
   - **MainActivity.java**: The main activity that serves as the entry point of the app.
   - **MusicManager.java**: Manages background music and sound effects.
   - **Obstacle.java**: Represents obstacles that the player must avoid.
   - **ObstacleManager.java**: Manages the creation, positioning, and movement of obstacles.
   - **UIManager.java**: Handles user interface elements and updates them according to the game state.

4. **Drawable Resources (located in `res/drawable/`)**
   - Images and XML files defining various drawable resources used in the game, such as arrows, bonuses, character images (Goku, Frieza, etc.), and backgrounds.

5. **Raw Resources (located in `res/raw/`)**
   - **background_music.mp3**: The background music file played during the game.

6. **Values Resources (located in `res/values/`)**
   - **colors.xml**: Defines color resources used in the app.
   - **strings.xml**: Contains string resources for the app.
   - **themes.xml**: Defines the app's theme.
   - **themes.xml (night)**: Defines the app's theme for night mode.

## How to Run

1. **Prerequisites**
   - Ensure you have Android Studio installed on your machine.
   - Make sure you have the necessary Android SDKs installed.

2. **Setup**
   - Clone or download the project to your local machine.
   - Open the project in Android Studio.

3. **Build and Run**
   - Connect your Android device or start an Android emulator.
   - Click on the "Run" button in Android Studio to build and deploy the app to your device/emulator.

## Features

- **Character Control**: Navigate the character using touch controls.
- **Obstacle Avoidance**: Avoid various obstacles that appear on the screen.
- **Bonuses**: Collect bonuses to gain shield.
- **Background Music**: Enjoy background music while playing the game.
- **Dynamic UI**: The user interface updates dynamically based on the game state.
- 

 # פרויקט משחק - דרגון בול

## סקירה כללית

הפרויקט הוא משחק אנדרואיד שבו השחקן שולט בדמות (גוקו) תוך הימנעות ממכשולים ואיסוף בונוסים שנותנים לו "מגן". המשחק כולל דמויות מונפשות, מוזיקת רקע ואלמנטים שונים לשיפור חוויית השחקן.

## מבנה הפרויקט

הפרויקט מחולק למספר תיקיות וקבצים, כל אחד מהם משמש מטרה מסוימת. להלן סקירה מפורטת של מבנה הפרויקט והמרכיבים העיקריים שלו:

### תיקיות וקבצים

1. **AndroidManifest.xml**
   - קובץ המניפסט שמכיל מידע חיוני על האפליקציה, כגון מרכיביה, הרשאות ומטא נתונים.

2. **ic_launcher-playstore.png**
   - אייקון ההשקה של האפליקציה כפי שהוא מופיע בחנות Google Play.

3. **קבצי Java (ממוקמים ב-`java/com/example/cargame/`)**
   - **Character.java**: מייצג את דמות השחקן ומנהל את התכונות וההתנהגות שלה.
   - **GameManager.java**: שולט בלוגיקת המשחק הכללית, כולל ניהול מצב המשחק ועדכונים.
   - **MainActivity.java**: הפעילות הראשית שמשרתת כנקודת הכניסה לאפליקציה.
   - **MusicManager.java**: מנהל מוזיקת רקע ואפקטים קוליים.
   - **Obstacle.java**: מייצג מכשולים שהשחקן צריך להימנע מהם.
   - **ObstacleManager.java**: מנהל יצירה, מיקום ותנועה של מכשולים.
   - **UIManager.java**: מטפל באלמנטים של ממשק המשתמש ומעדכן אותם בהתאם למצב המשחק.

4. **משאבי Drawable (ממוקמים ב-`res/drawable/`)**
   - תמונות וקבצי XML המגדירים משאבי Drawable שונים המשמשים במשחק, כגון חצים, בונוסים, תמונות דמויות (גוקו, פריזה וכו'), ורקעים.

5. **משאבי Raw (ממוקמים ב-`res/raw/`)**
   - **background_music.mp3**: קובץ מוזיקת הרקע שמתנגן במהלך המשחק.

6. **משאבי Values (ממוקמים ב-`res/values/`)**
   - **colors.xml**: מגדיר משאבי צבע המשמשים באפליקציה.
   - **strings.xml**: מכיל משאבי מחרוזת לאפליקציה.
   - **themes.xml**: מגדיר את הנושא של האפליקציה.
   - **themes.xml (night)**: מגדיר את הנושא של האפליקציה למצב לילה.

## כיצד להפעיל

1. **דרישות מקדימות**
   - ודא שיש לך את Android Studio מותקן במחשב שלך.
   - ודא שיש לך את ה-SDKs הנדרשים של אנדרואיד מותקנים.

2. **הגדרות**
   - שיבוט או הורדת הפרויקט למחשב המקומי שלך.
   - פתח את הפרויקט ב-Android Studio.

3. **בנה והפעל**
   - חבר את מכשיר האנדרואיד שלך או הפעל אמולטור אנדרואיד.
   - לחץ על כפתור ה-"Run" ב-Android Studio כדי לבנות ולפרוס את האפליקציה למכשיר/אמולטור שלך.

## תכונות

- **שליטת דמות**: נווט את הדמות באמצעות בקרות מגע.
- **הימנעות ממכשולים**: הימנע ממכשולים שונים שמופיעים על המסך.
- **בונוסים**: אסוף בונוסים כדי לקבל מגן.
- **מוזיקת רקע**: תהנה ממוזיקת רקע במהלך המשחק.
- **ממשק משתמש דינמי**: ממשק המשתמש מתעדכן דינמית בהתאם למצב המשחק.
