# Movies App for Android TV with Leanback

This project is an Android TV application that allows users to browse movies from TheMovieDbApi. The app features a home browse fragment, a search function, and category rows for easy navigation to vertical grid fragments showing lists of movies.

The project is designed for Android TV with Leanback, making it an ideal learning resource for developers who want to create similar apps for this platform. The project is also useful for those who want to learn how to use the background manager and image transitions.

## Installation

To use the project, you need to create an `api_key.xml` file in the resources folder and create a string with that same name. You can generate the API key from TheMovieDb developers' site.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="api_key">YOUR_API_KEY_HERE</string>
</resources>
```

## Features

* Home browse fragment
* Details fragment
* Search function
* Category rows for easy navigation
* Vertical grid fragments for movie lists
* Background manager
* Image transitions

## Usage
Users can navigate to the home screen to browse movies or use the search function to find specific movies. From the home screen, users can navigate to category rows to view lists of movies in those categories. They can click on a movie to view its details.

## License

Copyright DevExpert SLU

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.