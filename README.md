
# **いただきます Nutritional Assistant**

Pronounced as (I-ta-da-ki-ma-su Nutritional assistant)

**Contributors: Hao Hu, Zihan Jin, Jeffrey Shen, Chuxuan Ai**

## **The What**

Our program is a Nutritional Assistant, which was designed to help users figure out and manage their recommended macronutrient and calorie levels. The program determines a recommended nutritional threshold, allows the user to search up recipes and nutritional information about the dishes they prepare, and can generate a recommended comprehensive meal plan for the entire day, based on user specification.



The nomenclature of our program's name, Romanized: (itadakimasu) comes from Japanese culture, where "いただきます" is a phrase said before eating to express gratitude for the food, the people who prepared it, and the ingredients. It conveys mindfulness and respect toward the act of eating. 

## **The Purpose**

We wanted to promote simple wellbeing and health to all demographics, regardless of allergies, personal health goals, or dietary restrictions. Making use of the EDAMAM api, we wanted to make the various processes of healthy living more accessible by combining these elements into a single program. Whether it was meal-prepping, or tracking macronutrients to maximize weight loss, these tasks can become arduous due to the different sources of information required to adhere to these tasks. Our program aims to simplify this process.


## Table of contents:

- Software features


- Installation instructions


- Usage guide


- Licensing


- User feedback


- Contributing 



## Software Feature
### Goal-Based Nutrition Tracker
This feature provides a dynamic, user-specific nutrition tracking and goal-setting system. Descriptions are as follows:
> - Calculates the user's daily calorie and macronutrient (carbs, protein, fat) goals based on their body metrics (e.g., height, weight, age, activity level) and fitness objectives (e.g., weight loss, maintenance, muscle gain).
> - Displays progress toward these goals in a visually intuitive manner, using charts and progress bars.
> - Encourages users to achieve balance in their diet by highlighting areas where they are under or over their recommended intake.

``Example Usecase``
A lightly active user with a daily calorie target of 2,241 kcal can quickly see the nutrition goal for him on the panel. Macronutrient breakdowns (carbs, protein, fat) are displayed as progress rings to ensure users stay within their targets while maintaining a balanced diet.

### Meal Planner
This feature helps users create meal plans tailored to their dietary preferences. Descriptions are as follows:
> - Users can select dietary filters such as Balanced, High-Protein, Low-Carb, Low-Fat, Vegan, and more to generate meals that align with their eating habits and preferences.
> - Automatically generates meal options and also ensures that they are nutritionally balanced and align with the user’s goals, which ensures variety and nutritious.
> - Food nutritional values are displayed below so users can have a clear view of the given options.
> - The food added can be tracked into the nutrition tracker to display the progress.

``Example Usecase``
A user with "Low-Carb" preference can select the "Low-carb" box to receive generated meal options for him to choose from. The user can directly check the carbs data directly below the generated food.

### Food Customizer
This feature allows users to create customized meal options by entering specific keywords (e.g., "chicken", "salad", or "pasta"). Descriptions are as follows:
> - It generates a list of matching meals with detailed nutritional information according to the keywords. Users can assign selected meals to a specific meal type (e.g., breakfast, lunch, dinner, or snacks) with a simple selection option.
> - Ideal for users looking to include specific foods in their diet or experiment with new recipes.
> - Food nutritional values are displayed below so users can have a clear view of the given options, which helps to choose from variety.
> - The food chosen and added can be tracked into the nutrition tracker to display the progress.
> - Provides users with the flexibility to plan their meals while maintaining control over their dietary goals, ensuring a balanced and satisfying daily menu.

``Example Usecase``
The user want to eat chicken for lunch enter the keyword "chicken" and selects Chicken Stir-Fry and assigns it to the Lunch meal type. Then the food data are tracked to add into the daily nutritional goal. 

## Installation
This software does not require installation and is designed to run directly from the source code. Follow the steps below to get started:

#### Prerequisites

Ensure you have the following installed on your system:
- Java Development Kit (JDK) version X.X or higher.
- An Integrated Development Environment (IDE) (e.g., IntelliJ IDEA, Eclipse) or a text editor with Java support (e.g., VS Code).
- [Optional] A Java runtime environment (JRE) if you only need to run compiled .class files.
Clone or Download the Repository

#### Clone the repository using the command:
``
git clone https://github.com/HARDY130/CSC207-Project-Group-18.git
``

*Alternatively, download the ZIP file of the repository and extract it.

#### Open the project folder in your preferred IDE.
- Open the Project in Your IDE
- Ensure that the IDE's project SDK is set to the appropriate Java version.

### Run the Application

- Locate the main class file (Main.java or the entry point file, e.g., FoodPlannerApp.java). 
- Right-click the file and select Run, or use your IDE's "Run" button.
- If using the command line, navigate to the folder containing the compiled ``.class`` files and run:
``
java Main
``
Replace ``Main`` with the actual name of your main class.

### Using the Application
- Once the application starts, follow the on-screen instructions or refer to the user manual for guidance.

# Usage Guide

Welcome to the Food Planner application! Follow this guide to get started and make the most out of its features.

---

## 1. Login

- Access your account to manage your profile and track your meals.

1. Open the app, and the **Login Screen** will appear.
2. Enter your **username** and **password**.
3. Click **Log in**.

🎉 *If the credentials are correct, you’ll be redirected to the dashboard.*

---

## 2. Sign Up

- Create an account to start using the app.

1. On the **Sign Up Screen**, enter:
    - A unique **username**.
    - A secure **password**, and confirm it by re-entering.
2. Click **Sign up**.

💡 *If any required fields are missing, the app will guide you to complete your profile in the next steps.*

---

## 3. Complete Your Profile

- Provide essential information for a tailored experience.

1. Fill in your:
    - **Year of Birth** (used to calculate your age).
    - **Gender** (Male/Female).
    - **Weight** (kg) and **Height** (cm).
    - **Activity Level** (e.g., Sedentary, Active).
2. Specify any **allergies** or dietary preferences (e.g., gluten-free, vegan).
3. Click **Save** to finalize your profile.

💡 *The app will calculate your recommended calorie intake based on this information.*

---

## 4. Dashboard

- Your central hub for tracking nutrition and activity.

- **What you’ll see:**
    - A visual breakdown of your calorie and macronutrient (carbs, protein, fat) progress.
    - Recent activity level information.
- **Features:**
    - **Update Profile**: Modify your personal details.
    - **Generate Meal Plan**: Get meal recommendations based on your preferences.
    - **Record Meal**: Log meals and track your nutrition.

---

## 5. Meal Planner

- Generate meal suggestions based on your dietary preferences.

1. Choose your **dietary preferences** (e.g., high-protein, vegetarian, Mediterranean).
2. Click **Generate New Meals**.
3. Browse the meal suggestions and click **Add** to include them in your plan.

---

## 6. Record a Meal

- Log your meals to track daily nutrition.

1. Enter the name of the meal or food item in the **search bar**.
2. Select the appropriate result from the list.
3. Specify the **meal type** (e.g., breakfast, lunch, dinner).
4. Click **Add to Meal**.

💡 *Nutritional details will automatically be updated in your profile.*

---
Thank you for using Food Planner! If you need further assistance, feel free to reach out.

# License

This project is licensed under the **MIT License**.

You are free to use, copy, modify, and distribute the code for any purpose, provided you include the original license.
See the [LICENSE file](./LICENSE) for more details.

MIT License

Copyright (c) 2024 Hao Hu, Zihan Jin, Jeffrey Shen, Chuxuan Ai

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.





## **Feedback**

We highly value your feedback to improve いただきます. Here’s how you can share your thoughts with us: Please use the Google Form, https://forms.gle/WFM5E3Kqt7yRt9ZDA, to send us your feedback. The following two parts are some instructions for your feedback. 

1.	Rules for valid feedback:
•	Your answer should be specific and better with examples.
•	Avoid just rushing into the survey.
•	Feedback for a proposed change should recommend a change that benefits ALL users.

2.	What to expect after submitting feedback:
•	Normally, you will not receive an announcement from us. Unless your feedback leads to an update, you will be acknowledged in our thank-you email.


## **Contributions**

We welcome contributions to enhance いただきます. Whether you're fixing bugs, adding new features, or improving documentation, we will appreciate your help! Here’s how you can contribute:

1.	Fork the repository in our link: https://github.com/HARDY130/CSC207-Project-Group-18.
2.	Clone the forked repository to your local machine.
3.	Make any reasonable changes in your IDE.
4.	Push your work back with useful commit information and create a pull request.
5.	Ensure your code has a good style, like following the clean architecture and SOLID principles.
6.	Provide a clear title and description for the merge request.
7.	All pull requests are reviewed by at least one project owner.
8.	Once we approved, your changes will be merged into the main branch.

