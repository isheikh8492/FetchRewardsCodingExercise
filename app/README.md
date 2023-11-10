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
