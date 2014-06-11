
Event Planner App
Description:

Planning and organizing events can be a tedious job, tracking and executing the various steps that make a good event. Events comprise various type e.g. Birthday parties, wedding parties, baby shower, other social gatherings, musical shows, technical presentations etc. Each event has a different composition and hence requires planning differently. This app lets users get party management ideas as well as keep all that information handy at all times.

User Stories
The event host opens the app and chooses an event from a list of event types such as Birthday, Wedding party, Game night, Baby shower etc. We will have limited 3-4 events types here.
After selecting the event type, next activity screen opens to compose Event Details such as description, date and time and venue.
Since event type is already selected and app has this info, suggestions for venue come up as a scrollable list (either vertically or horizontally scrollable) for user to select or take ideas from. A map view also shows up, and user can pinch and zoom on it. 
Once complete, these event details gets persisted and can later be accessed and edited via ‘My Events’ from the Action Bar menu.
Next a stream of “TODOs” comes up covering the components specific to that event type. Each component can be clicked on and takes you to that context. Lets walkthrough using ‘Birthday’ as example. The TODOs would be
Birthday Cake: user will click this to get sections - a “yelp”-type listing of bakeries nearby which he can click on and place an order by calling, a list of birthday cake images pulled in via image search, and a list of birthday cake recipes in case users wants to make it themselves. Optional story - Extending this idea to a whole menu of different items.
Decorations: User will click this to view a list of decorations by boy/girl, and also suggestions for say a nearby ‘Party city’ or listings from Amazon.com for buying. Clicking on online buying link will however take user out of this app and into the browser (not going to be tracked by this app henceforth)
Party games: User will click this to get a list of suggested games, which are links pulled in from Google/Yahoo search. Optional story – user can also compile a list of music/songs to be kept handy.
Guest list and RSVP: User goes into ‘guest list’ and enters the names and contacts of the people to invite for the event. User will also mark RSVP yes/no/maybe for the guests and get a total count of each on top. 
After the recommended TODO’s, host gets additional option of looking for more ideas or suggestions from other hosts who had organized kind of same event. He can view any existing public tips or if not, ask for some. Other app users will see such requests on top and will be able to send a response back (similar to public forum comments). This could be an optional story based on the success of the previous three.
For a saved event under ‘My Event’, host can also choose ‘Update’. This is an official update to any event details – description, date-time and venue, and will prompt user to send this update to all guests via text or email. We will plan to only incorporate email now as texting incurs charges.
Other optional advanced stories:
User profile (not sure if need one outside the local storage of the phone)
Ticketing for paid events
Integration with Facebook/Twitter etc for posting
Profile for uploading and accessible via web.
Intended learnings:
We intend to learn networking (Getting information via REST APIs and also being able to post data and receive responses to it), useful gestures (tapping, swiping & other finger gestures). Neither of us are UI experts (not yet at least) and are anticipating needing lot of time for making UI look great and up to the mark of any professional app.
