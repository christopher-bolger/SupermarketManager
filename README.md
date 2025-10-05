# Assignment 1: Supermarket Manager

The objective of this CA exercise is to create a supermarket management system in Java that makes
heavy use of custom-built internal data structures. The system should allow the user to manage a
supermarket that is made up of multiple floor areas. Every floor area is made up of multiple aisles.
Every aisle is made up of multiple shelves. Every shelf can store multiple good items (of varying
quantities). So, all good items are stored on a shelf, a shelf must be in an aisle, and an aisle in a given
floor area.

The system should allow the user to:

- Add a new floor area to the system. The following information should be stored for each
floor area: floor area title (e.g. Household, Fruit and Veg, Dairy, Meat) and level (e.g. ground
floor, first floor, etc.)

- Add a new aisle to a floor area. Aisles can be for regular/unrefrigerated goods, refrigerated
goods, or frozen goods. The following information should be stored for each aisle: aisle
name (e.g. Cheese, Bread), aisle dimensions (length, width, etc.), and temperature (i.e.
Unrefrigerated, Refrigerated, or Frozen).

* Note that the aisle name must be kept unique in the system, so check that no other
aisle on any other floor area has the same name when creating a new aisle.

- Add a shelf to an aisle. Properties that should be stored are: shelf number.

- Add good items to a shelf (in an aisle in a floor area). Good items should have properties:
description of the good item unit, the unit size/weight (e.g. in grams, millilitres, or similar),
the unit price, the number/quantity of them being added, and storage temperature (i.e.
Unrefrigerated, Refrigerated, or Frozen), and a photo of the good item (as a URL). If a good
item with the exact same description and the exact same size/weight already exists on the
specified shelf then the quantity of the specified good item should simply be updated. For
example, adding 16 boxes of Cornflakes (720g) to a shelf which already has 7 boxes of the
same good (by description and size/weight) will result in 23 boxes on the shelf. The unit
price should also update to the new price (if different to the old price) and the photo (URL)
update to the new photo.

- View all stock in the supermarket. This would show/list all good items, on all shelves, in all
aisles, in all floor areas, in a structured fashion. This should also show a full breakdown of
costings/values and quantities for all good items, shelves, aisles, floor areas, and overall.
o Examples of these might include (your exact/given details might differ; these are just
rough illustrations):

- Tea Bags (500g): 16 @ €3.99 = €63.84

- Shelf 1 in Aisle ‘Bread’: 17 good items with a total value of €325.14

- Aisle ‘Bread’: 6 Shelves with a total goods value of €1433.23

- Floor Area ‘Baking and Breads’: 3 Aisles with a total goods value of €3844.09

- Supermarket: 4 Floor Areas with a total goods value of €16344.17

2

- Interactively “drill down” through floor areas, aisles, and shelves to view good items stored
using an appropriate GUI map for the supermarket.
o You can keep the GUI map simple (e.g. stacking aisle names under the
corresponding floor areas, etc.) or you can allow it to be more customisable to
position/orient aisles within their floor areas, which could also be given dimensions
and locations, etc. in order to approximate the actual layout of the supermarket.
This approach would require dimension, position, orientation information, etc. to
also be (user) specified when creating floor areas and aisles. Note that floor areas
can also be on different ground levels.

- Search for good items by name. The system will systematically search for all occurrences of
the given good item and report on where they are stored in the supermarket (floor area,
aisle, shelf) and their quantities, etc.

- A “smart add” facility for good items. Given the good item’s description (and quantity,
size/weight, price, storage temperature, photo, etc.), the system will identify a suitable place
to store them. It will first try to store them in the same place (floor area, aisle, shelf) as
identical good items in the first instance. If that doesn’t succeed, it will store them in a
heuristically suitable place e.g. an aisle with the most suitable storage temperature, and on a
shelf alongside similar goods (e.g. frozen “Fish fingers” to be stored in a frozen aisle with
other “fish” good items). The system completes the “smart add” by creating and adding a
good item to the heuristically chosen aisle/shelf and reporting on the storage location.
o You can have the “smart add” behave any way you think appropriate.
 Remove good items. Good items to remove should be appropriately identified by the user
(by floor area, aisle, shelf, etc.) and removal can be for either some specified quantity or all
of the good items.

- Reset facility. Clears all system data.
  
- Save and load the entire system data to support persistence between executions.
o This can be done using any suitable file format (e.g. CSV, XML, binary, etc.). There is
no need to use any database system beyond this.

- Other appropriate facilities to manage the supermarket as you see fit.
  
Notes

- This is an individual CA exercise, and students should work by themselves to complete it.
- You will have to demonstrate this CA exercise in the lab sessions (or via Zoom, at my
discretion) and you will be interviewed on various aspects of it. You are expected to be able
to answer all questions on any code you are forwarding as your own.
- This CA exercise is worth 35% of your overall module mark.
- You should implement a suitable JavaFX graphical user interface to interact with your
system.
o The GUI does not have to be particularly fancy, but should nevertheless be
functional.
- You should implement a set of JUnit classes to systematically test your code as you develop
it. Exactly what you test for is up to you, but make sure that you demonstrate a test-driven
approach to your development.

3
- It is important to note that you cannot use any existing Java collections or data structures
classes (e.g. ArrayList, LinkedList, or any other class that implements the Collection interface
or any of its children – if in doubt, ask me!). You also cannot use regular arrays directly. You
essentially have to implement the required data structures and algorithms from scratch and
from first principles (in line with the module learning outcomes). This is deliberate.
o You will have to create numerous custom ADTs such as FloorArea, Aisle, Shelf,
GoodItem, etc. (your classes/class names may differ) and use custom-built linked
lists for storing the primary system data.
o You should also avoid simply storing data using the JavaFX components themselves.
The JavaFX components can of course be used to display data/information as
required, but the main data should be stored separate from the JavaFX components.
- The indicative marking scheme below indicates where you should focus your efforts.
  
Indicative Marking Scheme
- Appropriate custom ADTs and linked lists = 10%
- Create/add facilities (floor area, aisle, shelf, good item) = 10%
- View all stock (structured listing, including quantities and costings) = 10%
- Interactive GUI map = 10%
- Search for good items = 10%
- Smart add for good items = 10%
- Remove good items (some and all) = 10%
- Reset facility = 5%
- Save/load facility = 8%
- Appropriate JavaFX user interface = 7%
- JUnit testing (minimum of 6-8 useful unit tests) = 5%
- General (commenting, style, logical approach, completeness, etc.) = 5%
