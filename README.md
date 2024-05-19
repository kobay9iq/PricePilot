![Static Badge](https://img.shields.io/badge/koshiex-PricePilot-PricePilot)
![GitHub top language](https://img.shields.io/github/languages/top/koshiex/PricePilot)
![GitHub](https://img.shields.io/github/license/koshiex/PricePilot)

# PricePilot
## Overview
PricePilot is an Android application designed to help users find the best prices for products across various marketplaces. By leveraging a backend service, the app retrieves an array of JSON objects representing the cheapest lots for the same product and displays them in a list. Users can also save products to a database for future consideration.

## Features
-	Retrieve the cheapest lots for products from various marketplaces.
-	Display results in a user-friendly list format.
-	Save products to a local database for future reference.

## Dependencies
- Android Room
- Kotlin Serialization
- Retrofit & OkHttp
- Glide

 ## Usage
1.	Open the PricePilot app on your device.
2.	Enter the product name to search for the product.
3.	The app will display a list of the cheapest lots for the product from various marketplaces.
4.	To save a product for future reference, click on the save icon next to the product.

## Backend Service

PricePilot relies on a backend service to fetch product data. The backend service repository can be found [there](https://github.com/koshiex/RESTScrapper)

## Demonstration screenshots
<p float="center">
  <img src="https://i.imgur.com/Gv8WBdi.jpeg" width="250">
  <img src="https://i.imgur.com/pTtbwfY.jpeg" width="250">
  <img src="https://i.imgur.com/A8nmF5q.jpeg" width="250">
</p>
