# Fetch Rewards Coding Exercise - Android App

This is a **native Android app (Java)** that retrieves JSON data from Fetch Rewards API and displays it in a sorted and filtered list.

## Features
- Fetches JSON from [Fetch API](https://fetch-hiring.s3.amazonaws.com/hiring.json).
- Filters out items with **blank or null names**.
- Sorts items **first by `listId`, then by `name`**.
- Displays the results using **RecyclerView**.
- Built using **Java** and supports the latest Android SDK.

---

## How to Build and Run

### Clone the Repository
```sh
git clone https://github.com/YOUR_GITHUB_USERNAME/FetchRewardsProject.git
cd FetchRewardsProject
```

### Open in Android Studio
- Open **Android Studio**.
- Select **Open an existing project**.
- Choose the `FetchRewardsProject` folder.

### Build & Run
- Click the **Run ▶️** button in Android Studio.
- Select an **emulator** or **physical device** to run the app.

---

## Code 

### `MainActivity.java`
- Fetches JSON data using **HttpURLConnection**.
- Parses and filters the data.
- Sorts the data by `listId` and `name`.
- Updates **RecyclerView** dynamically.

### `FetchItem.java`
- Represents a single item with `id`, `listId`, and `name`.

### `FetchItemAdapter.java`
- Binds each `FetchItem` to a **RecyclerView** item.
