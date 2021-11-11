Assignment 6 Improvements:

1. Add a mutation method writeCell(Coord pos, data d) in WorkSheet interface. This method will allow for writing of cells at a given position, which would overwrite the old cell if necessary.

2. Add observer and subject methods to data, since data interface covers both Observer and Subject.

3. Change Value and function from interface to an abstract class, where the abstract class only has a single field: dependents, which contain the Coords of the cells that depend on it.
	-Add a getter method to get this field of dependents, called getDependents()

	-probably have to change stuff in createcell so that in creating functions also adds the cood of the function to each value in the function.

4. For references, no dependencies field is needed, since the references field is equivalent to it's dependencies.

5. Edit createCell function object so that it will also update dependencies as needed.

6. Add a "blank cell" object to hold dependencies for when an earlier-created reference references a null cell. Any new data put in that null cell will need to know it has a dependency where the reference references it.

7. Added addRow() and addColumn() methods to the model interface, so that we can implement some form of infinite scrolling.

8. Seperated the rendering of the view from the JFrame class to a custom JPanel class, as specified in the assignment.

9. Created a custom JTable used by the view, so that we could control its behavior more precisely.

Assignment 7 Design:

1. Create a Read-only interface so that views can be passed a model that can't be mutated

2. Create a testing mock  model