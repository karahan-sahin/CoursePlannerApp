# Boun Course Planner App

This app is the Course Planner for Bogazici University students. The app has two Activity sessions:
1. `Main Activity` : the courses are viewed on a Table and added courses are stored with deletability.
2. `Search Activity` : courses can be searched and selected by query from a Realtime Firebase Database. Also their external link to their syllabus can be found if exists

For the Web Crawler, I will be using the sample url from the University page. The script crawls through the departmental programs and extracts the course information:
- `course id`
- `course name`
- `instructor`
- `days`
- `hours`
- `credits`
- `ects`
- `external link to syllabus`

The languages that are used in this app:
- Kotlin (For the app structure)
- Python (For the web crawler)
