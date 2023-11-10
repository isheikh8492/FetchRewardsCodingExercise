# Fetch Rewards Coding Exercise - Android App

## Overview

This Android application is built to demonstrate fetching and rendering a list of items from a remote JSON resource, aligning with the Fetch Rewards coding exercise guidelines. The app groups items by "listId", sorts them by "listId" and "name", and excludes any items with a null or blank "name".

## Features

- **Data Fetching**: Utilizes Volley library to perform network requests and retrieve data from the specified JSON endpoint.
- **Data Presentation**: Displays data in a RecyclerView, grouped by "listId".
- **Sorting**: Sorts the data first by "listId" and then by "name".
- **Filtering**: Excludes items with null or blank "name" fields from the display.
- **Error Handling**: Shows an error layout if network connectivity is unavailable or if data fetching fails.
- **UI Polish**: Implements a ProgressBar to indicate loading states to the user.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- You have installed the latest version of [Android Studio](https://developer.android.com/studio).
- You have an Android device or emulator running Android (API Level 30 or higher).
- You have a basic understanding of Android development and Kotlin/Java.

## Installation

1. Clone the repository:
```bash
git clone https://github.com/isheikh8492/FetchRewardsCodingExercise.git
```
2. Open Android Studio and select 'Open an existing project'.
3. Navigate to the cloned repository and open it.

## Running the App

Build and run the application using Android Studio. Ensure you have a suitable Android device or emulator selected. The app will fetch data when launched and display it according to the exercise requirements.

## Approach

The application is designed with a clean architecture in mind, separating concerns into UI, data handling, and service layers.

- MainActivity: Orchestrates UI interactions and data fetching.
- ItemAdapter: Custom RecyclerView adapter for rendering sorted and filtered items.
- ItemDownloader: Handles data fetching logic using Volley and callbacks to MainActivity.
- BorderItemDecoration: Enhances RecyclerView items with styled borders.

## Contributing

If you'd like to contribute, please fork the repository and create a pull request with your changes. Please make sure to update tests as appropriate.

## Authors

Imaduddin Sheikh



## Acknowledgments

Thanks to Fetch Rewards for the opportunity to undertake this exercise. It has been a valuable experience in demonstrating modern Android development practices.


