This is an Android app for fetching image result results and displaying them in a grid view. It features infinite scroll and ability to apply filters to narrow down search results. On clicking a thumbnail, it will open in full-screen view and user will be able to share the image via MMS with his friends.

A] Total #hrs to complete: 6

B]  Completed User stories:
 * [x] Required: User can enter a search query that will display a grid of image results from the Google Image API.
 * [x] Required: User can click on "settings" which allows selection of advanced search options to filter results
 * [x] Required: User can configure advanced search filters such as:
     Size (small, medium, large, extra-large)
     Color filter (black, blue, brown, gray, green, etc...)
     Type (faces, photo, clip art, line art)
     Site (espn.com)
 * [x] Required: Subsequent searches will have any filters applied to the search results
 * [x] Required: User can tap on any image in results to see the image full-screen
 * [x] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 * [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
 * [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
 * [x] Advanced: User can share an image to their friends or email it to themselves
 * [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
	
Focussed more on functionality and less on UI polishing this time. But aiming to do both next time.

C] Walkthrough of all user stories:

![Video Walkthrough](GridImageSearch_demo.gif)	

