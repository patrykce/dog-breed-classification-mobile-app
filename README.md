# dog-breed-classification-mobile-app
Mobile app that lets us recognize dog breeds.


Use with my python rest API repo : [link here](https://github.com/patrykce/dog-breed-classification-mobile-app)

This is mobile app, that recognises images from aparat or from gallery working on android.</br>
Firstly you must run host backend server that handles incoming pictures and evalueates ones.</br>
You can use my python flask REST API specified above. To configure host, go to file: `/app/src/main/assets/configuration.properties`</br>
Actually there are two configurable variables (configured to work locally with my [python REST API](https://github.com/patrykce/dog-breed-classification-mobile-app)):
- `remote.api.host` (default=10.0.2.2)
- `remote.api.port` (default=5000)
